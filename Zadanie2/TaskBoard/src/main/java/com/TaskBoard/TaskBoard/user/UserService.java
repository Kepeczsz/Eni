package com.TaskBoard.TaskBoard.user;

import com.TaskBoard.TaskBoard.queries.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    public User addUser(User user){
        return userRepository.save(user);
    }

    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }

    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    public Optional<User> getUser(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent())
            return user;
        throw new NoSuchElementException("User is not present");
    }
    public void updateUser(Long id, User userDetails) {
        User user = userRepository.getReferenceById(id);
        user.setName(userDetails.getName());
        user.setSurname(userDetails.getSurname());
        user.setEmail(userDetails.getEmail());
        userRepository.save(user);
    }

    public List<User> getUsers() {
       return this.userRepository.findAll();
    }

    public List<User> getFilteredUsers(Filter userFilter) {
        return this.userRepository.findAll().stream()
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

    }
}
