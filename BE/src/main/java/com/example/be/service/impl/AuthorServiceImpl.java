package com.example.be.service.impl;

import com.example.be.model.Author;
import com.example.be.payload.request.AuthorRequest;
import com.example.be.payload.response.AuthorResponse;
import com.example.be.payload.response.CategoryResponse;
import com.example.be.payload.response.ListDataResponse;
import com.example.be.repository.AuthorRepository;
import com.example.be.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
  @Autowired
  AuthorRepository authorRepository;

  @Override
  public ResponseEntity<Object> getAll() {
    try {
      List<Author> authors = authorRepository.findAll();
      ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("OK").data(authors).build();
      return ResponseEntity.ok(listDataResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<Object> addAuth(AuthorRequest authorRequest) {
    try {
      if (authorRepository.existsByAuthorName(authorRequest.getAuthorName())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Author already exists!");
      }
      Author author = new Author();
      author.setAuthorName(authorRequest.getAuthorName());
      authorRepository.save(author);

      CategoryResponse categoryResponse = new CategoryResponse();
      categoryResponse.setCategoryName(author.getAuthorName());

      ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("OK").data(categoryResponse).build();
      return ResponseEntity.status(HttpStatus.CREATED).body(listDataResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<Object> deleteAuth(Long id) {
    try {
      if (authorRepository.existsById(id)) {
        authorRepository.deleteById(id);
        ListDataResponse<Object> listDataResponse = ListDataResponse.builder()
                .message("Author " + id + " deleted " + "successfully").build();
        return ResponseEntity.ok(listDataResponse);
      } else {
        ListDataResponse<Object> listDataResponse = ListDataResponse.builder()
                .message("Category not found").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listDataResponse);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<Object> editAuth(Long id, AuthorRequest authorRequest) {
    try {
      Optional<Author> authorOptional = authorRepository.findById(id);
      if (authorOptional.isPresent()) {
        Author author = authorOptional.get();
        if (authorRequest.getAuthorName().isBlank()) {
          return ResponseEntity.badRequest().body("Author name is required!");
        }
        if (authorRepository.existsByAuthorName(authorRequest.getAuthorName()) && !authorRequest.getAuthorName().equals(author.getAuthorName())) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Author already exists!");
        }
        author.setAuthorName(authorRequest.getAuthorName());
        authorRepository.save(author);

        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setAuthorName(author.getAuthorName());

        ListDataResponse<Object> listDataResponse = ListDataResponse.builder()
                .message("Author name updated successfully!").data(authorResponse).build();
        return ResponseEntity.status(HttpStatus.OK).body(listDataResponse);
      } else {
        ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("Author not found!").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listDataResponse);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from the server with exception = " + e);
    }
  }
}


