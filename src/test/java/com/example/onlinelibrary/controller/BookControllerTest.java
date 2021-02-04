package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Genre;
import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.service.AuthorService;
import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.service.GenreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    public static final String URLFindAll = "/books";
    public static final String URLCreateBookForm = "/book-create";
    public static final String URLCreateBook = "/book-create";
    public static final String URLBookPage = "/book-page";
    public static final String URLShowUpdateForm = "/book-update";
    public static final String URLUpdateBook = "/book-update";
    public static final String URLDeleteBook = "/book-delete";
    public static final String URLBookReservation = "/book-reservation";

    @InjectMocks
    BookController controller;

    @Mock
    GenreService genreService;
    @Mock
    AuthorService authorService;
    @Mock
    BookService bookService;

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
        when(genreService.findAll()).thenReturn(getGenres());
        when(authorService.findAll()).thenReturn(getAuthors());
        when(bookService.search(keyword)).thenReturn(getBooks());
        ResultActions resultActions = this.mvc.perform(get(URLFindAll).param("keyword", keyword));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("keyword"))
                .andExpect(view().name("books-list"));
    }

    @Test
    public void testCreateBookForm() throws Exception {
        when(genreService.findAll()).thenReturn(getGenres());
        when(authorService.findAll()).thenReturn(getAuthors());
        ResultActions resultActions = this.mvc.perform(get(URLCreateBookForm));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("book-create"));
    }

    @Test
    public void testCreateBook_hasError() throws Exception {
        when(genreService.findAll()).thenReturn(getGenres());
        when(authorService.findAll()).thenReturn(getAuthors());
        ResultActions resultActions = this.mvc.perform(post(URLCreateBook).flashAttr("book",getBook(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(view().name("book-create"));
    }

    @Test
    public void testCreateBook_saveBook() throws Exception {
        when(bookService.saveBook(any())).thenReturn(getBook(1L));
        ResultActions resultActions = this.mvc.perform(post(URLCreateBook).flashAttr("book",getBookForValidation(1L)));
        resultActions.andExpect(status().isFound())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("redirect:/books"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        String book_id = "2";
        when(genreService.findAll()).thenReturn(getGenres());
        when(authorService.findAll()).thenReturn(getAuthors());
        when(bookService.findById(anyLong())).thenReturn(getBook(2L));
        ResultActions resultActions = this.mvc.perform(get(URLShowUpdateForm).param("book_id", book_id));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(view().name("book-update"));
    }

    @Test
    public void testUpdateBook_hasError() throws Exception {
        String book_id = "1";
        when(genreService.findAll()).thenReturn(getGenres());
        when(authorService.findAll()).thenReturn(getAuthors());
        ResultActions resultActions = this.mvc.perform(post(URLUpdateBook).param("book_id", book_id).flashAttr("book",getBook(1L)));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(view().name("book-update"));
    }

    @Test
    public void testUpdateBook_findBook() throws Exception {
        String book_id = "1";
        when(bookService.findAll()).thenReturn(getBooks());
        when(bookService.findById(anyLong())).thenReturn(getBook(1L));
        bookService.updateBook(getBook(1L), 1L);
        verify(bookService).updateBook(any(), anyLong());
        ResultActions resultActions = this.mvc.perform(post(URLUpdateBook).param("book_id", book_id).flashAttr("book",getBookForValidation(1L)));
        resultActions.andExpect(status().isFound())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("redirect:/book-page?book_id=1" ));
    }

    @Test
    public void testDeleteBook() throws Exception {
        String book_id = "1";
        when(bookService.findById(anyLong())).thenReturn(getBook(1L));
        bookService.deleteBook(getBook(1L));
        verify(bookService).deleteBook(any());
        ResultActions resultActions = this.mvc.perform(get(URLDeleteBook).param("book_id", book_id));
        resultActions.andExpect(status().isFound())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("redirect:/books"));
    }

    @Test
    public void testBookPage() throws Exception {
        String book_id = "1";
        when(bookService.findById(anyLong())).thenThrow(NullPointerException.class);
        ResultActions resultActions = this.mvc.perform(get(URLBookPage).param("book_id", book_id));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("books-list"));
    }

    @Test
    public void testBookPage_findAll() throws Exception {
        String book_id = "1";
        when(bookService.findById(anyLong())).thenReturn(getBook(1L));
        when(bookService.findAll()).thenReturn(getBooks());
        ResultActions resultActions = this.mvc.perform(get(URLBookPage).param("book_id", book_id));
        resultActions.andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("book-page"));
    }

    @Test
    public void testSuccessBookReservation() throws Exception {
        String book_id = "1";
        when(bookService.findAll()).thenReturn(getBooks());
        when(bookService.findById(anyLong())).thenReturn(getBook(1L));
        when(bookService.reservation(any())).thenReturn(true);
        ResultActions resultActions = this.mvc.perform(get(URLBookReservation).param("book_id", book_id));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("success-reservation"));
    }


    @Test
    public void testErrorBookReservation() throws Exception {
        String book_id = "1";
        when(bookService.findAll()).thenReturn(getBooks());
        when(bookService.findById(anyLong())).thenReturn(getBook(1L));
        when(bookService.reservation(any())).thenReturn(false);
        ResultActions resultActions = this.mvc.perform(get(URLBookReservation).param("book_id", book_id));
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("error-reservation"));
    }

    private Book getBook(Long id) {
        Book book = new Book();
        book.setId(id);
        return book;
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

    private List<Author> getAuthors() {
        List<Author> authors = new ArrayList<Author>();
        Author author = new Author();
        author.setId(3L);
        Author author1 = new Author();
        author1.setId(4L);
        authors.add(author);
        authors.add(author1);
        return authors;
    }

    private List<Book> getBooks() {
        List<Book> books = new ArrayList<Book>();
        Book book = new Book();
        book.setId(2L);
        Book book1 = new Book();
        book1.setId(3L);
        books.add(book);
        books.add(book1);
        return books;
    }

    private Book getBookForValidation(Long id) {
        Book book = new Book();
        book.setId(id);
        book.setTitle("Test1");
        book.setPages(100);
        book.setDescription("Be or not to be");
        book.setQuantity(20);
        return book;
    }


    private ViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }
}
