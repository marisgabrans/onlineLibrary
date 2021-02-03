package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.service.AuthorService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorControllerTest {

    public static final String URLFindAll = "/authors";
    public static final String URLCreateAuthorForm = "/author-create";
    public static final String URLAuthorPage = "/author-page";
    public static  final String URLShowUpdateForm = "/author-update";

    @InjectMocks
    AuthorController controller;

    @Mock
    AuthorService authorService;

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
        when(authorService.search(keyword)).thenReturn(getAuthors());
        ResultActions resultActions = this.mvc.perform(get(URLFindAll).param("keyword", keyword));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("keyword"))
                .andExpect(view().name("authors/authors-list"));
    }

    @Test
    public void testCreateAuthorForm() throws Exception {
        ResultActions resultActions = this.mvc.perform(get(URLCreateAuthorForm));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("authors/author-create"));
    }

    @Test
    public void testAuthorPage() throws Exception {
        String author_id = "1";
        when(authorService.findAll()).thenReturn(getAuthors());
        when(authorService.findById(anyLong())).thenReturn(getAuthor(1L));
        ResultActions resultActions = this.mvc.perform(get(URLAuthorPage).param("author_id", author_id));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("authors/author-page"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        String author_id = "1";
        when(authorService.findById(anyLong())).thenReturn(getAuthor(1L));
        ResultActions resultActions = this.mvc.perform(get(URLShowUpdateForm).param("author_id", author_id));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("authors/author-update"));
    }

    private List<Author> getAuthors() {
        List<Author> authors = new ArrayList<Author>();
        Author author = new Author();
        author.setId(2L);
        Author author1 = new Author();
        author1.setId(3L);
        authors.add(author);
        authors.add(author1);
        return authors;
    }

    private Author getAuthor(Long id) {
        Author author = new Author();
        author.setId(id);
        return author;
    }



    private ViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }
}
