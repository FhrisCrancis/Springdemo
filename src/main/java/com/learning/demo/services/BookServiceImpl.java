package com.learning.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.demo.entities.Book;
import com.learning.demo.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{
    
    @Autowired
    private BookRepository repository;

    @Override 
    public List<Book> findAll() { 
        return repository.findAll(); 
    }
    
    @Override 
    public Book save(Book book) { 
        return repository.save(book); 
    }
    
    @Override 
    public Optional<Book> findById(Integer id) { 
        return repository.findById(id); 
    }

    @Override 
    public void delete(Book book) {
        repository.delete(book); 
    }
   
}




    
         
             
            