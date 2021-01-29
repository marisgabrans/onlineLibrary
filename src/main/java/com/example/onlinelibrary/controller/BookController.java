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
import org.springframework.web.bind.annotation.*;

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
    public String createBook(@ModelAttribute @Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "/book-create";
        }

        bookService.saveBook(book);
        model.addAttribute("book", book);
        return "redirect:/books";
    }

    @GetMapping("/book-page")
    public String bookPage(Model model, @RequestParam(name = "book_id", required = true) Long book_id) {
        List<Book> books = bookService.findAll();
        Book book = bookService.findById(book_id);
        model.addAttribute("book", book);
        return "/book-page";
    }

    @GetMapping("/book-update")
    public String showUpdateForm(@RequestParam(name = "book_id", required = true)  long id, Model model) {
        List<Genre> genres = genreService.findAll();
        List<Author> authors = authorService.findAll();
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return "/book-update";
    }

    @PostMapping("/book-update")
    public String updateBook(@RequestParam(name = "book_id", required = true) Long id, Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/book-update";
        }

        bookService.updateBook(book, id);
        model.addAttribute("books", bookService.findAll());
        return "redirect:/books";
    }

    @GetMapping("/book-delete")
    public String deleteBook(@RequestParam(name = "book_id", required = true) long id, Model model) {
        bookService.deleteById(id);
        model.addAttribute("books", bookService.findAll());
        return "/books-list";
    }

    @GetMapping("/book-reservation")
    public String bookReservation(Model model, @RequestParam(name = "book_id", required = true) Long book_id) {
        List<Book> books = bookService.findAll();
        Book book = bookService.findById(book_id);
        boolean check = bookService.reservation(book);
            if (check) {
                return "/success-reservation";
            } else {
                return "/error-reservation";
            }
    }
}
