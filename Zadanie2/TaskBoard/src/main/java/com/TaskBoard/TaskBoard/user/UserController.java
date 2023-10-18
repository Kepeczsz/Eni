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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user, BindingResult bindingResult) {
        return this.userService.addUser(user, bindingResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return this.userService.deleteUserById(id);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User userDetails, BindingResult bindingResult) {
        return this.userService.updateUser(id, userDetails, bindingResult);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getUsers()
    {
        return this.userService.getUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id)
    {
        return this.userService.getUser(id);
    }

    @GetMapping("/getFilteredUsers")
    public ResponseEntity<List<User>> getFilteredUsers(@RequestBody Filter userFilter){
        return this.userService.getFilteredUsers(userFilter);
    }

}
