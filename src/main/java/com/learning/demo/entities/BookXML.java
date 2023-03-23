package com.learning.demo.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="book")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookXML {
    
    @XmlElement 
    private Integer bookId;

    @XmlElement 
    private String title;

    @XmlElement 
    private String author; 
    
    public BookXML() {} 
    
    public BookXML(Integer bookId, String title, String author) { 
        super(); 
        this.bookId = bookId; 
        this.title = title; 
        this.author = author; 
    }

    //needed for jUnit testing  
    @Override 
    public String toString() { 
        return  "<book><bookId>"+bookId+"</bookId>"+ 
                "<title>"+title+"</title>"+ 
                "<author>"+author+"</author></book>"; 
    }

    //NO GETTERS AND SETTERS SINCE WE'RE USING XML
}
