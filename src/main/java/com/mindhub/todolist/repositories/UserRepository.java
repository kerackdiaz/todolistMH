package com.mindhub.todolist.repositories;

import com.mindhub.todolist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String password);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);


}
