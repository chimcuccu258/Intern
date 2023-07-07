package com.example.be.controller;

import com.example.be.payload.request.AuthorRequest;
import com.example.be.repository.AuthorRepository;
import com.example.be.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backend/author")
public class AuthorController {

  @Autowired
  AuthorService authorService;

  @Autowired
  AuthorRepository authorRepository;

  @GetMapping("/get-all")
  public ResponseEntity<Object> getAllAuthors() {
    return authorService.getAll();
  }

  @PostMapping("/add")
  public ResponseEntity<Object> addAuthor(
          @RequestBody AuthorRequest authorRequest) {
    return authorService.addAuth(authorRequest);
  }

  @PostMapping("/edit/{id}")
  public ResponseEntity<Object> editAuthor(
          @PathVariable("id") Long id,
          @RequestBody AuthorRequest authorRequest) {
    return authorService.editAuth(id,authorRequest);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> deleteAuthor(@PathVariable("id") long id) {
    return authorService.deleteAuth(id);
  }
}