package com.learning.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.demo.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    
}