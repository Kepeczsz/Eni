package com.TaskBoard.TaskBoard.taskUserRole;

import com.TaskBoard.TaskBoard.role.Role;
import com.TaskBoard.TaskBoard.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;


@Entity
public class UserRoleAssigment {

    @Id
    private Long id;

    @OneToOne
    private User user;
    @OneToOne
    private Role role;
}
