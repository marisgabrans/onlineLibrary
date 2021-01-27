package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository  extends JpaRepository<Author, Long> {
}
