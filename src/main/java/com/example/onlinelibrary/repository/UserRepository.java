package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findUserById(Long id);

    @Query("SELECT u FROM User u " +
            "WHERE u.firstName LIKE %?1% " +
            "OR u.lastName LIKE %?1% " +
            "OR u.email LIKE %?1%")
    List<User> search(String keyword);

}
