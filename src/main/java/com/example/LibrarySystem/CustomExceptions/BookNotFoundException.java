package com.example.LibrarySystem.CustomExceptions;

public class BookNotFoundException extends Exception{
    public BookNotFoundException(String message) {
        super(message);
    }

}
