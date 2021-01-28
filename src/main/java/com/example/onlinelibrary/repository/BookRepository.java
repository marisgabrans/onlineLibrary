package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookById(Long id);

}
