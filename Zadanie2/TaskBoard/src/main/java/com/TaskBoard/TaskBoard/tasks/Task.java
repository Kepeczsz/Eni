package com.TaskBoard.TaskBoard.tasks;

import com.TaskBoard.TaskBoard.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Task {
    @Id
    private Long id;

    private String title;
    private String description;
    private LocalDate dueTo;

    @OneToMany
    List<User> userList;
}
