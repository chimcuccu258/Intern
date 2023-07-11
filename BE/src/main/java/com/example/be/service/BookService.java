package com.example.be.service;

import com.example.be.payload.request.BookRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface BookService {

  ResponseEntity<Object> getAll();

  ResponseEntity<Object> addBook(BookRequest bookRequest);

  ResponseEntity<Object> deleteBook(Long id);

  ResponseEntity<Object> editBook(Long id, BookRequest bookRequest);

  ResponseEntity<Object> getBookById(Long id);

  ResponseEntity<Object> searchBooks(String keyword);
}
