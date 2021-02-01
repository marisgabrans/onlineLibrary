package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.service.AuthorService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String findAll(Model model, @Param("keyword") String keyword) {
        List<Author> authors = authorService.search(keyword);
        model.addAttribute("authors", authors);
        model.addAttribute("keyword", keyword);
        return "/authors/authors-list";
    }

    @GetMapping("/author-create")
    public String createAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "/authors/author-create";
    }

    @PostMapping("/author-create")
    public String createAuthor(@ModelAttribute @Valid Author author, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "/authors/author-create";
        }
        authorService.saveAuthor(author);
        model.addAttribute("author", author);
        return "redirect:/authors";
    }

    @GetMapping("/author-page")
    public String authorPage(Model model, @RequestParam(name = "author_id", required = false) Long author_id) {
        Author author =  authorService.findById(author_id);

        List<Author> authors = authorService.findAll();
        model.addAttribute("author", author);
        return "/authors/author-page";
    }

    @GetMapping("/author-update")
    public String showUpdateForm(@RequestParam(name = "author_id", required = true) long id, Model model) {
        Author author = authorService.findById(id);
        model.addAttribute("author", author);
        return "/authors/author-update";
    }

    @PostMapping("/author-update")
    public String updateAuthor(@RequestParam(name = "author_id", required = true) Long id,
                               @ModelAttribute @Valid Author author, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/authors/author-update";
        }
        authorService.updateAuthor(author, id);
        model.addAttribute("authors", authorService.findAll());
        return "redirect:/author-page?author_id=" + id;
    }

    @GetMapping("/author-delete")
    public String deleteAuthor(@RequestParam(name = "author_id", required = true) long id, Model model) {
        try {
            Author author = authorService.findById(id);
            authorService.deleteById(id);
        } catch (Exception e) {
            return "redirect:/authors";
        }
        model.addAttribute("author", authorService.findAll());
        return "redirect:/authors";
    }
}
