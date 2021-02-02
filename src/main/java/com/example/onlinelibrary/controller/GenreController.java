package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.model.Genre;
import com.example.onlinelibrary.service.GenreService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String findAll(Model model, @Param("keyword") String keyword) {
        List<Genre> genres = genreService.search(keyword);
        model.addAttribute("genres", genres);
        model.addAttribute("keyword", keyword);
        return "genres/genres-list";
    }

    @GetMapping("/genre-create")
    public String createGenreForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "genres/genre-create";
    }

    @PostMapping("/genre-create")
    public String createGenre(@ModelAttribute @Valid Genre genre, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "genres/genre-create";
        }
        genreService.saveGenre(genre);
        model.addAttribute("genre", genre);
        return "redirect:/genres";
    }

    @GetMapping("/genre-page")
    public String genrePage(Model model, @RequestParam(name = "genre_id", required = false) Long genre_id) {
        Genre genre = genreService.findById(genre_id);
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genre", genre);
        return "genres/genre-page";
    }

    @GetMapping("/genre-update")
    public String showUpdateForm(@RequestParam(name = "genre_id", required = true) long id, Model model) {
        Genre genre = genreService.findById(id);
        model.addAttribute("genre", genre);
        return "genres/genre-update";
    }

    @PostMapping("/genre-update")
    public String updateGenre(@RequestParam(name = "genre_id", required = true) Long id,
                              @ModelAttribute @Valid Genre genre, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "genres/genre-update";
        }
        genreService.updateGenre(genre, id);
        model.addAttribute("genres", genreService.findAll());
        return "redirect:/genre-page?genre_id=" + id;
    }

    @GetMapping("/genre-delete")
    public String deleteGenre(@RequestParam(name = "genre_id", required = true) long id, Model model) {
        try {
            Genre genre = genreService.findById(id);
            genreService.deleteById(id);
        } catch (Exception e) {
            return "redirect:/genres";
        }
        model.addAttribute("genre", genreService.findAll());
        return "redirect:/genres";
    }

}
