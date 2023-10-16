package com.TaskBoard.TaskBoard.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Boolean existsById(Long id){
        return userRepository.existsById(id);
    }

    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    public void updateUser(Long id, User userDetails) {
        User user = userRepository.getReferenceById(id);
        user.setName(userDetails.getName());
        user.setSurname(userDetails.getSurname());
        user.setEmail(userDetails.getEmail());
        userRepository.save(user);
    }

}
