package com.learning.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.demo.entities.Book;
import com.learning.demo.entities.BookXML;

@RestController
public class BookRestController {

    @GetMapping(value="/restXML", 
        produces = { MediaType.APPLICATION_XML_VALUE})
    public BookXML getBookXML(
            @RequestParam(required = false, defaultValue = "0") String bookId,
            @RequestParam(required = false, defaultValue = "Book") String title, 
            @RequestParam(required = false, defaultValue = "Anonymous") String author
        ){
        int id = Integer.parseInt(bookId);
        return new BookXML(id, title, author);
    }


    @GetMapping("/rest")
    public Book getBook(
            @RequestParam(required = false, defaultValue = "0") String bookId,
            @RequestParam(required = false, defaultValue = "Book") String title, 
            @RequestParam(required = false, defaultValue = "Anonymous") String author
            ){
        int id = Integer.parseInt(bookId);
        return new Book(id, title, author);
    }

}
