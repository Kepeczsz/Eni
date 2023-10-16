package com.TaskBoard.TaskBoard.taskUserRole;

import com.TaskBoard.TaskBoard.role.Role;
import com.TaskBoard.TaskBoard.task.Task;
import com.TaskBoard.TaskBoard.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userRole")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_sequence")
    @SequenceGenerator(name = "user_role_sequence", sequenceName = "user_role_sequence", allocationSize = 1)
    private Long id;

    @OneToOne
    private User user;
    @OneToOne
    private Role role;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonBackReference
    private Task task;

}
