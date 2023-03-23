package com.learning.demo.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learning.demo.entities.Book;
import com.learning.demo.entities.BookDto;
import com.learning.demo.services.BookService;

@RestController
@RequestMapping(path="/bookcontrol/")
public class NewBookRestController {
    
    @Autowired
    private BookService service;

    
    @GetMapping("/testall")
    public List<Book> getAllBooks(){
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@RequestBody @Validated BookDto bookDto){
        service.save(new Book(bookDto.getTitle(), bookDto.getAuthor()));
    }

    public Book verifyBook(int bookId) {
        Optional<Book> optional = service.findById(bookId);
        if(optional.isEmpty()){
            throw(new NoSuchElementException("Book for " + bookId));
        } else {
            return optional.get();
        }
    }

    //localhost:8080/bookcontrol/:id
    @PutMapping("{id}")
    public BookDto updateWithPut(@PathVariable(value = "id") int id,
            @RequestBody @Validated BookDto bookDto){
        Book book = verifyBook(id);
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        return new BookDto(service.save(book));
    }

    @PatchMapping("{id}")
    public BookDto updateWithPatch(@PathVariable(value = "id") int id,
            @RequestBody @Validated BookDto bookDto){
        Book book = verifyBook(id);
        if(bookDto.getAuthor() != null){
            book.setAuthor(bookDto.getAuthor());
        }
        if(bookDto.getTitle() != null){
            book.setTitle(bookDto.getTitle());
        }
        return new BookDto(service.save(book));
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable(value = "id") int id){
        Book book = verifyBook(id);
        service.delete(book);
    }
}
