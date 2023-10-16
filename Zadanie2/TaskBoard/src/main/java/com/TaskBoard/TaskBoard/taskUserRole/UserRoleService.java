package com.TaskBoard.TaskBoard.taskUserRole;

import com.TaskBoard.TaskBoard.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole addUserToTask(User user) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        return this.userRoleRepository.save(userRole);
    }

    public void updateUserRole(UserRole user){
        UserRole userRole = userRoleRepository.getReferenceById(user.getId());
        userRole.setRole(user.getRole());
        userRole.setUser(user.getUser());
        userRoleRepository.save(userRole);
    }
}
