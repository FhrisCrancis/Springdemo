package com.learning.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Book {
    
    @Id
    @GeneratedValue
    private Integer bookId;
    
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    public Book() {}

    public Book(String title, String author) { 
        super(); 
        this.title = title; 
        this.author = author; 
    }

    public Book(Integer bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    
}


