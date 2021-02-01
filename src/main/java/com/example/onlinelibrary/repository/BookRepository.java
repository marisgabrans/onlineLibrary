package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookById(Long id);

    @Query(value = "SELECT * FROM books b " +
            "JOIN authors a " +
            "ON b.author_id = a.author_id " +
            "JOIN genre g " +
            "ON b.genre_id = g.genre_id " +
            "WHERE CONCAT(book_id, ' ', title, ' ', first_name, ' ', last_name, ' ', genre_name, ' ', description) " +
            "LIKE %:keyword%", nativeQuery = true)
    List<Book> search(@Param("keyword") String keyword);

}
