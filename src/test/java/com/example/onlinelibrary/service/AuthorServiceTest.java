package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.repository.AuthorRepository;
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
class AuthorServiceTest {

    @MockBean
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void findById() {

        Long id = 456L;
        Author author = new Author();
        author.setId(id);

        authorService.findById(author.getId());
        Mockito.verify(authorRepository, Mockito.times(1)).getOne(author.getId());

    }

    @Test
    void findAll() {

        List<Author> list = new ArrayList<>();
        Author author1 = new Author();
        Author author2 = new Author();
        Author author3 = new Author();
        list.add(author1);
        list.add(author2);
        list.add(author3);

        Mockito.doReturn(list).when(authorRepository).findAll();
        List<Author> result = authorService.findAll();
        Assertions.assertEquals(3, result.size());
        Mockito.verify(authorRepository, Mockito.times(1)).findAll();

    }

    @Test
    void saveAuthor() {

        Author author = new Author();

        Mockito.when(authorRepository.save(author)).thenReturn(author);
        Author result = authorService.saveAuthor(author);
        Assertions.assertNotNull(result);

    }

    @Test
    void deleteById() {

        Long id = 456L;
        Author author = new Author();
        author.setId(id);

        authorService.deleteById(author.getId());
        Mockito.verify(authorRepository, Mockito.times(1)).deleteById(author.getId());

    }

//    @Test
//    void updateAuthor() {
//
//    }
//
//    @Test
//    void search() {
//    }

}