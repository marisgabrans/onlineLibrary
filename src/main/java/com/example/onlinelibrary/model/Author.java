package com.example.onlinelibrary.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    Long id;
    @Column(name = "first_name")
    @NotBlank(message = "First name is mandatory")
    String firstName;
    @Column(name = "last_name")
    @NotBlank(message = "Last name is mandatory")
    String lastName;

    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "author_id")
    private List<Book> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
