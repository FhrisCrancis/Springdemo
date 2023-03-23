package com.learning.demo.entities; 

//Book Data Transfer Object
public class BookDto { 

    private String title; 
    private String author; 
    
    public BookDto() {} 
    
    public BookDto(String title, String author) { 
        super(); 
        this.title = title; 
        this.author = author; 
    } 
    
    public BookDto(Book book) { 
        this(book.getTitle(), book.getAuthor()); 
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

    