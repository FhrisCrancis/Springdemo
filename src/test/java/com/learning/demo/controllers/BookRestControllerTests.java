package com.learning.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.learning.demo.entities.Book;
import com.learning.demo.entities.BookXML;

import jakarta.xml.bind.JAXB;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookRestControllerTests {
    
    @Autowired
    private TestRestTemplate template;


    @Test
    public void bookWithoutParameters() {
        //get response as json and verify it
        ResponseEntity<Book> entity = template.getForEntity("/rest", Book.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());
        //make the book from json then verify
        Book response = entity.getBody();
        assertEquals(0, response.getBookId());
        assertEquals("Book", response.getTitle());
        assertEquals("Anonymous", response.getAuthor());
    }

    @Test
    public void bookWithParameters() {
        //get response as object and verify it
        Book response = template.getForObject("/rest?bookId=1&author=Stephen%20King&title=Kujo", Book.class);
        assertEquals(1, response.getBookId());
        assertEquals("Kujo", response.getTitle());
        assertEquals("Stephen%20King", response.getAuthor());
    }

    @Test
    public void bookXMLWithoutParameters(){
        //set XML content type explicitly to force response in XML
        //(if not, spring gets response in default JSON)
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        HttpEntity<String> httpEntity = new HttpEntity<>("parameters", headers);

        ResponseEntity<BookXML> entity = template.getRestTemplate().exchange("/restXML", 
                HttpMethod.GET, httpEntity, BookXML.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        //below is same as assertEquals("MediaType.APPLICATION_XML_VALUE", entity.getHeaders().getContentType());
        assertEquals("application/xml", 
            entity.getHeaders().getContentType().toString());
        
        //convert XML string to object (unmarshal takes XML toString and converts to a Book object)
        BookXML response = entity.getBody();
        Book book = JAXB.unmarshal(new StringReader(response.toString()), Book.class);
        assertEquals(0, book.getBookId());
        assertEquals("Book", book.getTitle());
        assertEquals("Anonymous", book.getAuthor());
    }
    
    @Test
    public void bookXMLWithParameters(){
        //set XML content type explicitly to force response in XML
        //(if not, spring gets response in default JSON)
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        HttpEntity<String> httpEntity = new HttpEntity<>("parameters", headers);

        ResponseEntity<BookXML> entity = template.getRestTemplate().exchange(
            "/restXML?bookId=1&author=Stephen%20King&title=Kujo", 
            HttpMethod.GET, httpEntity, BookXML.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("application/xml", 
            entity.getHeaders().getContentType().toString());
        
        //convert xml string to object
        BookXML response = entity.getBody();
        Book book = JAXB.unmarshal(new StringReader(response.toString()), Book.class);
        assertEquals(1, book.getBookId());
        assertEquals("Kujo", book.getTitle());
        assertEquals("Stephen%20King", book.getAuthor());
    }
}
