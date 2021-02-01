package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository  extends JpaRepository<Author, Long> {
    @Query(value = "SELECT * FROM authors b " +
            "WHERE CONCAT(first_name, ' ', last_name) " +
            "LIKE %:keyword%", nativeQuery = true)
    List<Author> search(@Param("keyword") String keyword);
}
