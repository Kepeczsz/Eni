package com.TaskBoard.TaskBoard.task;

import com.TaskBoard.TaskBoard.queries.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<String> addTask(@RequestBody Task task, BindingResult bindingResult){
        return this.taskService.addTask(task, bindingResult);
    }
    @GetMapping("/getTasks")
    public ResponseEntity<List<Task>> getTasks() {
        return this.taskService.getTasks();
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id){
        return this.taskService.getTask(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        return this.taskService.deleteTask(id);
    }

    @PutMapping("/{id}/{userId}")
    public ResponseEntity<String> assignUserToTask(@PathVariable  Long id, @PathVariable Long userId ){
        return this.taskService.assignUserToTask(id,userId);
    }

    @DeleteMapping("/{taskId}/{userId}")
    public ResponseEntity<String> removeUserFromTask(@PathVariable Long taskId, @PathVariable Long userId)
    {
        return this.taskService.removeUserFromTask(taskId,userId);
    }

    @GetMapping("/getFilteredTasks")
    public ResponseEntity<List<Task>> getFilteredTasks(@RequestBody Filter taskFilter){
        return this.taskService.getFilteredTasks(taskFilter);
    }

    @PutMapping("/editTaskDeitals/{taskId}")
    public ResponseEntity<String> updateTaskDetails(@PathVariable Long taskId, @RequestBody Task task, BindingResult bindingResult){
        return this.taskService.updateTask(taskId, task, bindingResult);
    }
}
