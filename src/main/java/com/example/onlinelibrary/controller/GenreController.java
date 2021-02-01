package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.service.GenreService;
import org.springframework.stereotype.Controller;

@Controller
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }
}
