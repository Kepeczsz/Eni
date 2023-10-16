package com.TaskBoard.TaskBoard.taskUserRole;

import com.TaskBoard.TaskBoard.task.Task;
import com.TaskBoard.TaskBoard.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByUserAndTask(User user, Task task);
}
