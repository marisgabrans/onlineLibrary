package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Genre;
import com.example.onlinelibrary.service.AuthorService;
import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class BookController {
    @Autowired BookService bookService;
    @Autowired GenreService genreService;
    @Autowired AuthorService authorService;



    @GetMapping("/books")
    public String findAll(Model model, @Param("keyword") String keyword) {
        List<Genre> genres = genreService.findAll();
        List<Author> authors = authorService.findAll();
        List<Book> books = bookService.search(keyword);
        model.addAttribute("books", books);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        model.addAttribute("keyword", keyword);
        return "books-list";
    }

    @GetMapping("/book-create")
    public String createBookForm(Model model) {
        List<Genre> genres = genreService.findAll();
        List<Author> authors = authorService.findAll();
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        model.addAttribute("book", new Book());
        return "book-create";
    }

    @PostMapping("/book-create")
    public String createBook(@ModelAttribute @Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Genre> genres = genreService.findAll();
            List<Author> authors = authorService.findAll();
            model.addAttribute("genres", genres);
            model.addAttribute("authors", authors);
            return "book-create";
        }
        bookService.saveBook(book);
        model.addAttribute("book", book);
        return "redirect:/books";
    }

    @GetMapping("/book-page")
    public String bookPage(Model model, @RequestParam(name = "book_id", required = false) Long book_id) {
        Book book = null;
        try {
            book = bookService.findById(book_id);
        } catch (Exception e) {
            return "books-list";
        }
        List<Book> books = bookService.findAll();
        model.addAttribute("book", book);
        return "book-page";
    }

    @GetMapping("/book-update")
    public String showUpdateForm(@RequestParam(name = "book_id", required = true) long id, Model model) {
        List<Genre> genres = genreService.findAll();
        List<Author> authors = authorService.findAll();
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return "book-update";
    }

    @PostMapping("/book-update")
    public String updateBook(@RequestParam(name = "book_id", required = true) Long id,
                             @ModelAttribute @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Genre> genres = genreService.findAll();
            List<Author> authors = authorService.findAll();
            model.addAttribute("genres", genres);
            model.addAttribute("authors", authors);
            return "book-update";
        }
        book.setCover(bookService.findById(id).getCover());
        bookService.updateBook(book, id);
        model.addAttribute("books", bookService.findAll());
        return "redirect:/book-page?book_id=" + id;
    }

    @GetMapping("/book-delete")
    public String deleteBook(@RequestParam(name = "book_id", required = true) long id, Model model) {
        try {
            Book book = bookService.findById(id);
            bookService.deleteBook(book);
        } catch (Exception e) {
            return "redirect:/books";
        }
        model.addAttribute("books", bookService.findAll());
        return "redirect:/books";
    }

    @GetMapping("/book-reservation")
    public String bookReservation(Model model, @RequestParam(name = "book_id", required = true) Long book_id) {
        List<Book> books = bookService.findAll();
        Book book = bookService.findById(book_id);
        boolean check = bookService.reservation(book);
        if (check) {
            return "success-reservation";
        } else {
            return "error-reservation";
        }
    }

    @RequestMapping(value = "/imageDisplay", method = RequestMethod.GET)
    public void showImage(@RequestParam("id") Long id, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {
        Book book = bookService.findById(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(book.getCover());
        response.getOutputStream().close();
    }
}
