package com.TaskBoard.TaskBoard.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private Long id;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'assigned'")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
