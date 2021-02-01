package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository  extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a " +
            "WHERE a.firstName LIKE %?1% " +
            "OR a.lastName LIKE %?1%")
    List<Author> search(String keyword);

}
