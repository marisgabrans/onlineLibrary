package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Genre;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorControllerTest {

    public static final String URLFindAll = "/authors";
    public static final String URLCreateAuthorForm = "/author-create";
    public static final String URLCreateAuthor = "/author-create";
    public static final String URLAuthorPage = "/author-page";
    public static final String URLShowUpdateForm = "/author-update";
    public static final String URLUpdateAuthor = "/author-update";
    public static final String URLDeleteAuthor = "/author-delete";

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
    public void testCreateAuthor_hasError() throws Exception {
        ResultActions resultActions = this.mvc.perform(post(URLCreateAuthor).flashAttr("author",getAuthor(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("authors/author-create"));
    }

    @Test
    public void testCreateAuthor_saveAuthor() throws Exception {
        when(authorService.saveAuthor(any())).thenReturn(getAuthor(1L));
        ResultActions resultActions = this.mvc.perform(post(URLCreateAuthor).flashAttr("author",getAuthorForValidation(1L)));
        resultActions.andExpect(status().isFound())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("redirect:/authors"));
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

    @Test
    public void testUpdateAuthor_hasError() throws Exception {
        String author_id = "1";
        ResultActions resultActions = this.mvc.perform(post(URLUpdateAuthor).param("author_id", author_id).flashAttr("author",getAuthor(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("/authors/author-update"));
    }

    @Test
    public void testUpdateAuthor_findAuthor() throws Exception {
        String author_id = "1";
        when(authorService.findAll()).thenReturn(getAuthors());
        authorService.updateAuthor(getAuthor(1L), 1L);
        verify(authorService).updateAuthor(any(), anyLong());
        ResultActions resultActions = this.mvc.perform(post(URLUpdateAuthor).param("author_id", author_id).flashAttr("author",getAuthorForValidation(1L)));
        resultActions.andExpect(status().isFound())
                .andExpect(model().attributeExists("authors"))
                .andExpect(view().name("redirect:/author-page?author_id=1" ));
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        String author_id = "1";
        when(authorService.findById(anyLong())).thenReturn(getAuthor(1L));
        authorService.deleteById(anyLong());
        verify(authorService).deleteById(any());
        ResultActions resultActions = this.mvc.perform(get(URLDeleteAuthor).param("author_id", author_id));
        resultActions.andExpect(status().isFound())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("redirect:/authors"));
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

    private Author getAuthorForValidation(Long id) {
        Author author = new Author();
        author.setId(1L);
        author.setFirstName("Test");
        author.setLastName("Test1");
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
