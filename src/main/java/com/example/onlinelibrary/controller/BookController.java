package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Genre;
import com.example.onlinelibrary.service.AuthorService;
import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @GetMapping("/books")
    public String findAll(Model model) {
        List<Book> books = bookService.findAll();
        List<Genre> genres = genreService.findAll();
        List<Author> authors = authorService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return "/books-list";
    }

    @GetMapping("/book-create")
    public String createBookForm(Model model) {
        List<Genre> genres = genreService.findAll();
        List<Author> authors = authorService.findAll();
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

    @GetMapping("update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "book-update";
    }

    @PostMapping("update/{id}")
    public String updateBook(@PathVariable("id") long id, @Valid Book book, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            return "book-update";
        }

        bookService.saveBook(book);
        model.addAttribute("students", bookService.findAll());
        return "index";
    }
}
