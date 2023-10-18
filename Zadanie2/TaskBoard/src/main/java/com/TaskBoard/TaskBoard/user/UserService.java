package com.TaskBoard.TaskBoard.user;

import com.TaskBoard.TaskBoard.queries.Filter;
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
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public UserService(UserRepository userRepository, UserValidator userValidator)
    {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }
    public ResponseEntity<String> addUser(User userDetails, BindingResult bindingResult){
        List<String> getErrors = getResponseEntity(userDetails, bindingResult);

        if(getErrors != null) return ResponseEntity.badRequest().body(String.join("\n",getErrors));

        userRepository.save(userDetails);
        return ResponseEntity.ok("User has been added");
    }

    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }

    public ResponseEntity<String> deleteUserById(Long id) {
        if(!existsById(id))
            return ResponseEntity.badRequest().body("User with id " + id + " does not exist");
        this.userRepository.deleteById(id);
        return ResponseEntity.ok("User has been deleted");
    }

    public ResponseEntity<User> getUser(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
    public ResponseEntity<String> updateUser(Long id, User userDetails, BindingResult bindingResult) {
        if(!existsById(id))
            return ResponseEntity.badRequest().body("User with id " + id + " does not exist");

        User user = userRepository.getReferenceById(id);

        user.setName(userDetails.getName());
        user.setSurname(userDetails.getSurname());
        user.setEmail(userDetails.getEmail());
        userRepository.save(user);

        return ResponseEntity.ok().body("user has been updated");
    }

    public ResponseEntity<List<User>> getUsers() {
       return ResponseEntity.ok(this.userRepository.findAll());
    }

    public ResponseEntity<List<User>> getFilteredUsers(Filter userFilter) {
        List<User> userList =  this.userRepository.findAll().stream()
                .filter(user -> {
                    try {
                        Field field = User.class.getDeclaredField(userFilter.getFieldName());
                        field.setAccessible(true);
                        Object foundUser = field.get(user);
                        if (foundUser != null && foundUser.toString().contains(userFilter.getSearch())) {
                            return true;
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException("Field '" + userFilter.getFieldName() + "' does not exist in the User class.");

                    }
                    return false;
                })
                .collect(Collectors.toList());
        if(userList.isEmpty())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(userList);
    }

    private List<String> getResponseEntity(User userDetails, BindingResult bindingResult) {
        userValidator.validate(userDetails,bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMessages.add(fieldError.getDefaultMessage());
            }
            return errorMessages;
        }
        return null;
    }
}
