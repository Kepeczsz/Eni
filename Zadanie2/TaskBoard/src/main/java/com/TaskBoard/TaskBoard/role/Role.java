package com.TaskBoard.TaskBoard.role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Role {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
