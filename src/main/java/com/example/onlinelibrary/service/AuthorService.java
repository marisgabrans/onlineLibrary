package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findById(Long id) {
        return authorRepository.getOne(id);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    public Author updateAuthor(Author authorForUpdate, Long id) {
        Author authorToBeUpdated = authorRepository.getOne(id);
        authorToBeUpdated.setFirstName(authorForUpdate.getFirstName());
        authorToBeUpdated.setLastName(authorForUpdate.getLastName());
        return authorRepository.save(authorToBeUpdated);
    }

    public List<Author> search(String keyword) {
        if (keyword != null) {
            return authorRepository.search(keyword);
        }
        return authorRepository.findAll();
    }
}
