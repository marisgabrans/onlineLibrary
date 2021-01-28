package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Genre;
import com.example.onlinelibrary.repository.GenreRepository;
import com.example.onlinelibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;
    private final GenreRepository genreRepository;

    @Autowired
    public BookController(BookService bookService, GenreRepository genreRepository) {
        this.bookService = bookService;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/books")
    public String findAll(Model model) {
        List<Book> books = bookService.findAll();
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("genres", genres);
        return "/books-list";
    }

    @GetMapping("/book-create")
    public String createBookForm(Book book) {
        return "/book-create";
    }

    @PostMapping("/book-create")
    public String createBook(Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }
}
