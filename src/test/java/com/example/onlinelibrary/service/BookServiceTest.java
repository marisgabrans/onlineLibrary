package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class BookServiceTest {

    @MockBean
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;


    @Test
    void findById() {

        Long id = 456L;
        Book book = new Book();
        book.setId(id);

        Mockito.when(bookRepository.findBookById(id)).thenReturn(book);
        Book result = bookRepository.findBookById(id);
        Assertions.assertNotNull(result);
    }

    @Test
    void findAll() {

        List<Book> list = new ArrayList<>();
        Book book1 = new Book();
        Book book2 = new Book();
        Book book3 = new Book();
        list.add(book1);
        list.add(book2);
        list.add(book3);

        Mockito.doReturn(list).when(bookRepository).findAll();
        List<Book> result = bookService.findAll();
        Assertions.assertEquals(3, result.size());
        Mockito.verify(bookRepository, Mockito.times(1)).findAll();

    }

    @Test
    void saveBook() {

        Book book = new Book();

        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Book result = bookService.saveBook(book);
        Assertions.assertNotNull(result);

    }

    @Test
    void deleteById() {

        Long id = 456L;
        Book book = new Book();
        book.setId(id);

        bookService.deleteById(book.getId());
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(book.getId());

    }

    @Test
    void deleteBook() {

        Book book = new Book();

        bookService.deleteBook(book);
        Mockito.verify(bookRepository, Mockito.times(1)).delete(book);

    }

//    @Test
//    void updateBook() {
//    }
//
//    @Test
//    void reservation() {
//    }
//
//    @Test
//    void search() {
//    }
}