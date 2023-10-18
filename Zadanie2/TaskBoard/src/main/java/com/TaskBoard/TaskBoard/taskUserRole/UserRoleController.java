package com.TaskBoard.TaskBoard.taskUserRole;

import com.TaskBoard.TaskBoard.role.Role;
import com.TaskBoard.TaskBoard.role.RoleType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userRoles")
@CrossOrigin
public class UserRoleController {
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PutMapping("/{taskId}/{userId}/{roleId}")
    public ResponseEntity<String> updateUserRoleInTask(@PathVariable Long taskId, @PathVariable Long userId, @PathVariable Long roleId) {
        return this.userRoleService.updateUserRole(taskId,userId,roleId);
    }
}
