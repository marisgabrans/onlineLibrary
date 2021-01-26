package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    }


