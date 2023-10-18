package com.TaskBoard.TaskBoard.task;

import com.TaskBoard.TaskBoard.queries.Filter;
import com.TaskBoard.TaskBoard.taskUserRole.UserRole;
import com.TaskBoard.TaskBoard.taskUserRole.UserRoleRepository;
import com.TaskBoard.TaskBoard.user.User;
import com.TaskBoard.TaskBoard.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
   private final UserRoleRepository userRoleRepository;
   private final UserRepository userRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository, UserRoleRepository userRoleRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    public Task addTask(Task task){
        return this.taskRepository.save(task);
    }
    public boolean deleteTask(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return true;
        }
        return false;
    }
    public Optional<Task> getTask(Long taskId){
        Optional<Task> task =  this.taskRepository.findById(taskId);
        if (task.isPresent()) {
            return task;
        }
        throw new NoSuchElementException("The task is not present");
    }

    public List<Task> getTasks() {return this.taskRepository.findAll();}
    public void updateTask(Long id, Task taskDetails){
        Task task = this.taskRepository.getReferenceById(id);
        task.setTaskStatus(taskDetails.getTaskStatus());
        task.setDescription(taskDetails.getDescription());
        task.setTitle(taskDetails.getTitle());
        task.setDueTo(taskDetails.getDueTo());
        task.setPriority(taskDetails.getPriority());
        this.taskRepository.save(task);
    }

    @Transactional
    public boolean assignUserToTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        UserRole userRole = userRoleRepository.findByUserAndTask(user, task);

        if (userRole != null) {
            return false;
        } else {
            userRole = new UserRole();
            userRole.setUser(user);
            userRole.setTask(task);
            userRoleRepository.save(userRole);

            List<UserRole> userRoles = task.getUserList();
            userRoles.add(userRole);

            taskRepository.save(task);

            return true;
        }
    }

    public boolean deleteUserFromTask(Long taskId, Long userId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        UserRole userRole = userRoleRepository.findByUserAndTask(user, task);

        if (userRole == null) {
            return false;
        }
        task.userList.remove(userRole);
        userRoleRepository.delete(userRole);
        return true;
    }

    public List<Task> getFilteredTasks(Filter taskFilter) {
        return this.taskRepository.findAll().stream()
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

    }
}
