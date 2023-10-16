package com.TaskBoard.TaskBoard.task;

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
    private final TaskValidator taskValidator;
    @Autowired
    public TaskController(TaskService taskService, TaskValidator taskValidator){
        this.taskService = taskService;
        this.taskValidator = taskValidator;
    }

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task task, BindingResult bindingResult){
        ResponseEntity<?> errorMessages = getResponseEntity(task, bindingResult);
         if(errorMessages != null) return errorMessages;


        return ResponseEntity.ok(this.taskService.addTask(task));
    }
    @GetMapping
    public ResponseEntity<?> getTasks() {
        return ResponseEntity.ok(this.taskService.getTasks());
    }
    @PutMapping("/{id}/{userId}")
    public void assignUserToTask(@PathVariable  Long id, @PathVariable Long userId ){
        this.taskService.assignUserToTask(id, userId);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id){
        return ResponseEntity.ok(this.taskService.getTask(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        boolean isDeleted = this.taskService.deleteTask(id);
        if (isDeleted) {
            return ResponseEntity.ok("Task with ID " + id + " has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with ID " + id + " was not found or could not be deleted.");
        }
    }

    private ResponseEntity<?> getResponseEntity(@RequestBody Task taskDetails, BindingResult bindingResult) {
        taskValidator.validate(taskDetails,bindingResult);
        if (bindingResult.hasErrors()) {
           List<String> errorMessages = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMessages.add(fieldError.getDefaultMessage());
            }
           return ResponseEntity.badRequest().body(errorMessages);
        }
        return null;
    }
}
