package com.TaskBoard.TaskBoard.task;

import com.TaskBoard.TaskBoard.taskUserRole.UserRoleAssigment;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    private Long id;

    private String title;
    private String description;
    private LocalDate dueTo;
    private TaskPriority priority;
    private TaskStatus taskStatus;

    @OneToMany
    List<UserRoleAssigment> userList;
}
