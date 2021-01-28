package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Genre;
import com.example.onlinelibrary.repository.AuthorRepository;
import com.example.onlinelibrary.repository.GenreRepository;
import com.example.onlinelibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookController(BookService bookService, GenreRepository genreRepository, AuthorRepository authorRepository) {
        this.bookService = bookService;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/books")
    public String findAll(Model model) {
        List<Book> books = bookService.findAll();
        List<Genre> genres = genreRepository.findAll();
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return "/books-list";
    }

    @GetMapping("/book-create")
    public String createBookForm(Model model) {
        List<Genre> genres = genreRepository.findAll();
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        model.addAttribute("book", new Book());
        return "/book-create";
    }

    @PostMapping("/book-create")
    public String createBook(Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/book-page")
    public String bookPage(Model model, @RequestParam(name = "book_id", required = true) Long book_id) {
        List<Book> books = bookService.findAll();
        Book book = bookService.findById(book_id);
        model.addAttribute("book", book);
        return "/book-page";
    }
}
