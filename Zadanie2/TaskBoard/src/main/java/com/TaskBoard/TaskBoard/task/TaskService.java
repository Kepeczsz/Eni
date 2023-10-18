package com.TaskBoard.TaskBoard.task;

import com.TaskBoard.TaskBoard.queries.Filter;
import com.TaskBoard.TaskBoard.taskUserRole.UserRole;
import com.TaskBoard.TaskBoard.taskUserRole.UserRoleRepository;
import com.TaskBoard.TaskBoard.user.User;
import com.TaskBoard.TaskBoard.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
   private final UserRoleRepository userRoleRepository;
   private final UserRepository userRepository;

   private final TaskValidator taskValidator;

    public TaskService(TaskRepository taskRepository, UserRoleRepository userRoleRepository, UserRepository userRepository, TaskValidator taskValidator) {
        this.taskRepository = taskRepository;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.taskValidator = taskValidator;
    }

    public ResponseEntity<String> addTask(Task task, BindingResult bindingResult){
        List<String> getErrors = getResponseEntity(task, bindingResult);

        if(getErrors != null) return ResponseEntity.badRequest().body(String.join("\n",getErrors));
        this.taskRepository.save(task);

        return ResponseEntity.ok("Task has been added");
    }
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.ok(this.taskRepository.findAll());
    }
    public ResponseEntity<String> deleteTask(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return ResponseEntity.ok().body("Task has been deleted");
        }
        return ResponseEntity.badRequest().body("Task has not been found");
    }
    public ResponseEntity<Task> getTask(Long taskId){
        Optional<Task> task =  this.taskRepository.findById(taskId);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    public ResponseEntity<String> updateTask(Long id, Task taskDetails, BindingResult bindingResult){
        Task task = this.taskRepository.getReferenceById(id);
        task.setTaskStatus(taskDetails.getTaskStatus());
        task.setDescription(taskDetails.getDescription());
        task.setTitle(taskDetails.getTitle());
        task.setDueTo(taskDetails.getDueTo());
        task.setPriority(taskDetails.getPriority());


        List<String> getErrors = getResponseEntity(task, bindingResult);
        if(getErrors != null) return ResponseEntity.badRequest().body(String.join("\n",getErrors));

        return ResponseEntity.ok().body("Task has been updated");
    }

    @Transactional
    public ResponseEntity<String> assignUserToTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        UserRole userRole = userRoleRepository.findByUserAndTask(user, task);

        if (userRole != null) {
            return ResponseEntity.badRequest().body("User has been assigned to another task");
        } else {
            userRole = new UserRole();
            userRole.setUser(user);
            userRole.setTask(task);
            userRoleRepository.save(userRole);

            List<UserRole> userRoles = task.getUserList();
            userRoles.add(userRole);

            taskRepository.save(task);

            return ResponseEntity.ok().body("User assigned to task");
        }
    }

    public ResponseEntity<String> removeUserFromTask(Long taskId, Long userId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        UserRole userRole = userRoleRepository.findByUserAndTask(user, task);

        if (userRole == null) {
            return ResponseEntity.badRequest().body("user was not assigned to task");
        }
        task.userList.remove(userRole);
        userRoleRepository.delete(userRole);
        return ResponseEntity.ok("User has been removed from task");
    }

    public ResponseEntity<List<Task>> getFilteredTasks(Filter taskFilter) {
        List<Task> taskList =  this.taskRepository.findAll().stream()
                .filter(task -> {
                    try {
                        Field field = Task.class.getDeclaredField(taskFilter.getFieldName());
                        field.setAccessible(true);
                        Object foundTask = field.get(task);
                        if (foundTask != null && foundTask.toString().contains(taskFilter.getSearch())) {
                            return true;
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException("Field '" + taskFilter.getFieldName() + "' does not exist in the User class.");

                    }
                    return false;
                })
                .collect(Collectors.toList());
         if(taskList.isEmpty())
             return ResponseEntity.badRequest().build();

         return ResponseEntity.ok(taskList);
    }

    private List<String> getResponseEntity(Task taskDetails, BindingResult bindingResult) {
        taskValidator.validate(taskDetails,bindingResult);

        if (bindingResult.hasErrors()) {
            return bindingResult
                    .getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
