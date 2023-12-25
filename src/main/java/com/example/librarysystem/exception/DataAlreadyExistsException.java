package com.example.librarysystem.exception;

public class DataAlreadyExistsException extends RuntimeException{
    public DataAlreadyExistsException(String formatted) {
        super(formatted);
    }
}