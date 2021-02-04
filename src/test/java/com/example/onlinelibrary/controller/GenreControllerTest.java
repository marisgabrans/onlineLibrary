package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.model.Genre;
import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.service.GenreService;
import com.example.onlinelibrary.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class GenreControllerTest {

    public static final String URLFindAll = "/genres";
    public static final String URLCreateGenreForm = "/genre-create";
    public static final String URLGenrePage = "/genre-page";
    public static  final String URLShowUpdateForm = "/genre-update";
    public static  final String URLUpdateGenre = "/genre-update";
    public static  final String URLDeleteGenre = "/genre-delete";

    @InjectMocks
    GenreController controller;

    @Mock
    GenreService genreService;

    private MockMvc mvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void testFindAll() throws Exception {
        String keyword = "Data";
        when(genreService.search(anyString())).thenReturn(getGenres());
        ResultActions resultActions = this.mvc.perform(get(URLFindAll).param("keyword", keyword));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attributeExists("keyword"))
                .andExpect(view().name("genres/genres-list"));
    }

    @Test
    public void testCreateGenreForm() throws Exception {
        ResultActions resultActions = this.mvc.perform(get(URLCreateGenreForm));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("genre"))
                .andExpect(view().name("genres/genre-create"));
    }

    @Test
    public void testCreateGenre_hasError() throws Exception {
        ResultActions resultActions = this.mvc.perform(post(URLCreateGenreForm).flashAttr("genre",getGenre(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("genres/genre-create"));
    }

    @Test
    public void testCreateGenre_saveGenre() throws Exception {
        when(genreService.saveGenre(any())).thenReturn(getGenre(1L));
        ResultActions resultActions = this.mvc.perform(post(URLCreateGenreForm).flashAttr("genre",getGenreForValidation(1L)));
        resultActions.andExpect(status().isFound())
                .andExpect(model().attributeExists("genre"))
                .andExpect(view().name("redirect:/genres"));
    }

    @Test
    public void testGenrePage() throws Exception {
        String genre_id = "1";
        when(genreService.findAll()).thenReturn(getGenres());
        when(genreService.findById(anyLong())).thenReturn(getGenre(1L));
        ResultActions resultActions = this.mvc.perform(get(URLGenrePage).param("genre_id", genre_id));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("genre"))
                .andExpect(view().name("genres/genre-page"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        String genre_id = "1";
        when(genreService.findById(anyLong())).thenReturn(getGenre(1L));
        ResultActions resultActions = this.mvc.perform(get(URLShowUpdateForm).param("genre_id", genre_id));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("genre"))
                .andExpect(view().name("genres/genre-update"));
    }

    @Test
    public void testUpdateGenre_hasError() throws Exception {
        String genre_id = "1";
        ResultActions resultActions = this.mvc.perform(post(URLUpdateGenre).param("genre_id", genre_id).flashAttr("genre",getGenre(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("genres/genre-update"));
    }

    @Test
    public void testUpdateGenre_findGenre() throws Exception {
        String genre_id = "1";
        when(genreService.findAll()).thenReturn(getGenres());
        genreService.updateGenre(getGenre(1L), 1L);
        verify(genreService).updateGenre(any(), anyLong());
        ResultActions resultActions = this.mvc.perform(post(URLUpdateGenre).param("genre_id", genre_id).flashAttr("genre",getGenreForValidation(1L)));
        resultActions.andExpect(status().isFound())
                .andExpect(model().attributeExists("genres"))
                .andExpect(view().name("redirect:/genre-page?genre_id=1" ));
    }

    @Test
    public void testDeleteGenre() throws Exception {
        String genre_id = "1";
        when(genreService.findById(anyLong())).thenReturn(getGenre(1L));
        genreService.deleteById(anyLong());
        verify(genreService).deleteById(any());
        ResultActions resultActions = this.mvc.perform(get(URLDeleteGenre).param("genre_id", genre_id));
        resultActions.andExpect(status().isFound())
                .andExpect(model().attributeExists("genre"))
                .andExpect(view().name("redirect:/genres"));
    }

    private List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<Genre>();
        Genre genre = new Genre();
        genre.setId(1L);
        Genre genre1 = new Genre();
        genre1.setId(2L);
        genres.add(genre);
        genres.add(genre1);
        return genres;
    }

    private Genre getGenre(Long id) {
        Genre genre = new Genre();
        genre.setId(id);
        return genre;
    }

    private Genre getGenreForValidation(Long id) {
        Genre genre = new Genre();
        genre.setId(id);
        genre.setGenre_name("Test");
        return genre;
    }

    private ViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }
}
