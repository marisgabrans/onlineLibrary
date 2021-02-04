package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Genre;
import com.example.onlinelibrary.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class GenreServiceTest {

    @MockBean
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @Test
    void findById() {

        Long id = 456L;
        Genre genre = new Genre();
        genre.setId(id);

        genreService.findById(genre.getId());
        Mockito.verify(genreRepository, Mockito.times(1)).getOne(genre.getId());

    }


    @Test
    void findAll() {

        List<Genre> list = new ArrayList<>();
        Genre genre1 = new Genre();
        Genre genre2 = new Genre();
        Genre genre3 = new Genre();

        list.add(genre1);
        list.add(genre2);
        list.add(genre3);

        Mockito.doReturn(list).when(genreRepository).findAll();
        List<Genre> result = genreService.findAll();
        Assertions.assertEquals(3, result.size());
        Mockito.verify(genreRepository, Mockito.times(1)).findAll();

    }

    @Test
    void saveGenre() {

        Genre genre = new Genre();

        Mockito.when(genreRepository.save(genre)).thenReturn(genre);
        Genre result = genreService.saveGenre(genre);
        Assertions.assertNotNull(result);
    }

    @Test
    void deleteById() {

        Long id = 456L;
        Genre genre = new Genre();
        genre.setId(id);

        genreService.deleteById(genre.getId());
        Mockito.verify(genreRepository, Mockito.times(1)).deleteById(genre.getId());

    }

//    @Test
//    void updateGenre() {
//
//
//    }
//
//    @Test
//    void search() {
//    }
}