package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Genre;
import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Blob;
import java.util.List;

    @Service
    public class BookService {
        private final BookRepository bookRepository;

        @Autowired
        public BookService(BookRepository bookRepository) {
            this.bookRepository = bookRepository;
        }

        public Book findById(Long id) {
            return bookRepository.getOne(id);
        }

        public List<Book> findAll( ) {
            return bookRepository.findAll();
        }

        public Book saveBook(Book book) {
            return bookRepository.save(book);
        }

        public void deleteById(Long id) {
            bookRepository.deleteById(id);
        }

        public Book updateBook(Book bookDetails, Long id) {
            Book book = bookRepository.findBookById(id);
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setTitle(bookDetails.getTitle());
            book.setGenre(bookDetails.getGenre());
            book.setPages(bookDetails.getPages());
            book.setDescription(bookDetails.getDescription());
            book.setQuantity(bookDetails.getQuantity());
            book.setCover(bookDetails.getCover());
            return bookRepository.save(book);
        }

    }