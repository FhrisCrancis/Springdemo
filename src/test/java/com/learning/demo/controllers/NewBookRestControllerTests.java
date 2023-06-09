package com.learning.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.learning.demo.entities.Book;
import com.learning.demo.entities.BookDto;
import com.learning.demo.services.BookServiceImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

@Transactional //needed for testing with a db
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewBookRestControllerTests {

    @Autowired
    private NewBookRestController controller;

    @Autowired
    private BookServiceImpl service;

    @Test
    public void testGetAllBooks(){
        service.save(new Book("Kujo", "Stephen King"));
        service.save(new Book("Funny Book", "Joe King"));

        List<Book> result = new ArrayList<>();
        controller.getAllBooks().forEach(result::add);

        assertEquals(2, result.size());
        
        //stream of results to get book titles, collect that stream of strings into the items list
        List<String> items = result.stream().map(Book::getTitle)
                    .collect(Collectors.toList());
        assertThat(items, containsInAnyOrder("Kujo", "Funny Book"));
    }
    
    @Test
    public void testPost(){
        BookDto bookDto = new BookDto("It", "Stephen King");
        controller.createBook(bookDto);
        Book book = controller.verifyBook(1);
        assertEquals(1, book.getBookId());
        assertEquals("It", book.getTitle());
        assertEquals("Stephen King", book.getAuthor());
    }

    @Test
    public void testUpdateWithPut(){
        service.save(new Book("Kujo", "Stephen King"));

        List<Book> books = new ArrayList<>();
        controller.getAllBooks().forEach(books::add);
        assertEquals(1, books.size());
        Book book = books.get(0);
        int id = book.getBookId();

        BookDto bookDto = new BookDto(book);
        bookDto.setTitle("Jotaro");
        bookDto.setAuthor("Stephen King");
        controller.updateWithPut(id, bookDto);

        Book result = controller.verifyBook(id);
        assertEquals("Jotaro", result.getTitle());
        assertEquals("Stephen King", result.getAuthor());
    }

    @Test
    public void testUpdateWithPatch(){
        service.save(new Book("Kujo", "Stephen King"));

        List<Book> books = new ArrayList<>();
        controller.getAllBooks().forEach(books::add);
        assertEquals(1, books.size());
        Book book = books.get(0);
        int id = book.getBookId();

        BookDto bookDto = new BookDto(book);
        bookDto.setTitle("Jotaro");
        bookDto.setAuthor(null);
        controller.updateWithPatch(id, bookDto);

        Book result = controller.verifyBook(id);
        assertEquals("Jotaro", result.getTitle());
        assertEquals("Stephen King", result.getAuthor());
    }

    @Test
    public void testDelete(){
        service.save(new Book("Kujo", "Stephen King"));
        List<Book> books = new ArrayList<>();
        controller.getAllBooks().forEach(books::add);
        assertEquals(1, books.size());
        Book book = books.get(0);
        int id = book.getBookId();

        controller.deleteBook(id);

        int count = controller.getAllBooks().size();
        assertEquals(0, count);
    }
}
