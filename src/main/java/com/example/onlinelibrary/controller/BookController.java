package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String findAll(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "/books-list";
    }

    /**
     * Commented out because of conflict in MainController class - duplicate mapping path "/"
     */
//    @GetMapping("/")
//    public String homePage(Model model) {
//        return "/home";
//    }

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
