package com.example.onlinelibrary.model;

import javax.persistence.*;

@Table(name = "books")
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
