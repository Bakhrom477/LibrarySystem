package com.example.librarysystem.exception;

public class WrongInputException extends RuntimeException{
    public WrongInputException(String message) {
        super(message);
    }
}
