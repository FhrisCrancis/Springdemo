package com.learning.demo.services;

import java.util.List;
import java.util.Optional;

import com.learning.demo.entities.Book;

public interface BookService {
    List<Book> findAll();
    Book save(Book book);
    Optional<Book> findById(Integer id);
    void delete(Book book);
}