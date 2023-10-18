package com.TaskBoard.TaskBoard.taskUserRole;

import com.TaskBoard.TaskBoard.role.Role;
import com.TaskBoard.TaskBoard.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository, RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }


    public ResponseEntity<String> updateUserRole(Long taskId,Long userId, Long roleId){
        UserRole userRole = userRoleRepository.findByTaskIdAndUserId(taskId, userId);
        if(userRole == null)
            return ResponseEntity.badRequest().body("user has not been found in this task");

        if(!roleRepository.existsById(roleId))
            return ResponseEntity.badRequest().body("Role does not exist, you need to create one");

        Role role = roleRepository.getReferenceById(roleId);
        userRole.setRole(role);
        userRoleRepository.save(userRole);

        return ResponseEntity.ok("Role has been updated");
    }
}
