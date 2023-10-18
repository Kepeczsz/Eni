package com.TaskBoard.TaskBoard.user;

import com.TaskBoard.TaskBoard.queries.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;
    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user, BindingResult bindingResult) {
        ResponseEntity<?> errorMessages = getResponseEntity(user, bindingResult);
        if (errorMessages != null) return errorMessages;

        return ResponseEntity.ok(this.userService.addUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        if(userService.existsById(id)) {
            this.userService.deleteUserById(id);
            return ResponseEntity.ok().body("User has been deleted");
        }
        return ResponseEntity.badRequest().body("User with that id does not exist");
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails, BindingResult bindingResult) {
        if(userService.existsById(id)) {
            ResponseEntity<?> errorMessages = getResponseEntity(userDetails, bindingResult);
            if (errorMessages != null) return errorMessages;

            this.userService.updateUser(id, userDetails);
            return ResponseEntity.ok().body("User has been changed");
        }
        return ResponseEntity.badRequest().body("User with that id does not exist or requested body is invalid");
    }

    @GetMapping
    public ResponseEntity<?> getUsers()
    {
        return ResponseEntity.ok(this.userService.getUsers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.userService.getUser(id));
    }

    private ResponseEntity<?> getResponseEntity(@RequestBody User userDetails, BindingResult bindingResult) {
        userValidator.validate(userDetails,bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMessages.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return null;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<?> getFilteredUsers(@RequestBody Filter userFilter){
        List<User> userList = this.userService.getFilteredUsers(userFilter);
        if( userList != null)
            return ResponseEntity.ok(userList);
        return ResponseEntity.badRequest().body("something went wrong");
    }

}
