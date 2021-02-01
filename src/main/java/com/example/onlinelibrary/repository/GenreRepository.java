package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query(value = "SELECT * FROM genre b WHERE genre_name LIKE %:keyword%", nativeQuery = true)
    List<Genre> search(@Param("keyword") String keyword);
}
