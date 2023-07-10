package com.example.be.controller;

import com.example.be.payload.request.BookRequest;
import com.example.be.repository.BookRepository;
import com.example.be.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backend/book")
public class BookController {

  @Autowired
  BookService bookService;

  @Autowired
  BookRepository bookRepository;

  @GetMapping("/get-all")
  public ResponseEntity<Object> getAllBooks() {
    return bookService.getAll();
  }

  @PostMapping("/add")
  public ResponseEntity<Object> addBook(
          @RequestBody BookRequest bookRequest) {
    return bookService.addBook(bookRequest);
  }

  @GetMapping("/getBook/{id}")
  public ResponseEntity<Object> getBookById(@PathVariable("id") Long id) {
    return bookService.getBookById(id);
  }


  @PostMapping("/edit/{id}")
  public ResponseEntity<Object> editBook(
          @PathVariable("id") Long id,
          @RequestBody BookRequest bookRequest) {
    return bookService.editBook(id,bookRequest);
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Object> deleteBook(@PathVariable("id") long id) {
    return bookService.deleteBook(id);
  }

  @GetMapping("/search/{keyword}")
  public ResponseEntity<Object> searchBooks(@PathVariable String keyword) {
    return bookService.searchBooks(keyword);
  }
}
