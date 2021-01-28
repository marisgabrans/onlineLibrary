package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Genre;
import com.example.onlinelibrary.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre findById(Long id) {
        return genreRepository.getOne(id);
    }

    public List<Genre> findAll( ) {
        return genreRepository.findAll();
    }

    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }
}