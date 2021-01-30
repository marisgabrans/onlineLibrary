package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookById(Long id);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%"
            + " OR b.description LIKE %?1%")
//            + " OR b.genre LIKE %?1%"
//            + " OR CONCAT(p.price, '') LIKE %?1%")
    List<Book> search(String keyword);


//    @Query("SELECT p FROM Product p WHERE CONCAT(p.name, p.brand, p.madein, p.price) LIKE %?1%")
//    public List<Product> search(String keyword);

}
