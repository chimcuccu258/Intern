package com.example.be.notfound;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
