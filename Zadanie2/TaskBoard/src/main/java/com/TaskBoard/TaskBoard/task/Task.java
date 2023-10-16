package com.TaskBoard.TaskBoard.task;

import com.TaskBoard.TaskBoard.taskUserRole.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
    @SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", allocationSize = 1)
    private Long id;

    private String title;
    private String description;
    private LocalDate dueTo;
    private TaskPriority priority;
    private TaskStatus taskStatus;

    @OneToMany(mappedBy = "task")
    @JsonManagedReference
    List<UserRole> userList = new ArrayList<>();

    public Task(String title, String description){
        this.description = description;
        this.title = title;
    }
}
