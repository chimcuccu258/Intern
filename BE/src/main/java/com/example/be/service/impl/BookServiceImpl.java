package com.example.be.service.impl;

import com.example.be.model.Author;
import com.example.be.model.Book;
import com.example.be.model.Category;
import com.example.be.payload.request.BookRequest;
import com.example.be.payload.response.BookResponse;
import com.example.be.payload.response.ListDataResponse;
import com.example.be.repository.AuthorRepository;
import com.example.be.repository.BookRepository;
import com.example.be.repository.CategoryRepository;
import com.example.be.service.BookService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
  @Autowired
  BookRepository bookRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  AuthorRepository authorRepository;

  @Override
  public ResponseEntity<Object> getAll() {
    try {
      List<Book> books = bookRepository.findAll();
      List<BookResponse> bookResponses = books.stream().map(book -> new BookResponse(
              book.getBookName(),
              book.getInventory(),
              book.getPrice(),
              book.getDescription(),
              book.getAuthor().getId(),
              book.getCategory().getId()))
              .collect(Collectors.toList());
      ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("OK")
              .data(bookResponses).build();
      return ResponseEntity.ok(listDataResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from the server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<Object> addBook(@NonNull BookRequest bookRequest) {
    try {
      if (bookRepository.existsByBookName(bookRequest.getBookName())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book already exists!");
      }
      if (!categoryRepository.existsById(bookRequest.getCtgId())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category Id is not exist!");
      }
      if (!authorRepository.existsById(bookRequest.getAuthorId())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Author Id is not exist!");
      }

      Book book = new Book();
      Author author = authorRepository.getById(bookRequest.getAuthorId());

      book.setBookName(bookRequest.getBookName());
      book.setInventory(bookRequest.getInventory());
      book.setPrice(bookRequest.getPrice());
      book.setDescription(bookRequest.getDescription());

      book.setAuthor(author);
      author.getBooks().add(book);

      Category category = categoryRepository.getById(bookRequest.getCtgId());

      book.setCategory(category);
      category.getBooks().add(book);

      bookRepository.save(book);
      authorRepository.save(author);
      categoryRepository.save(category);

      BookResponse bookResponse = new BookResponse();
      bookResponse.setBookName(book.getBookName());
      bookResponse.setInventory(book.getInventory());
      bookResponse.setPrice(book.getPrice());
      bookResponse.setDescription(book.getDescription());
      bookResponse.setAuthorId(author.getId());
      bookResponse.setCtgId(category.getId());

      ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("OK").data(bookResponse).build();
      return ResponseEntity.status(HttpStatus.CREATED).body(listDataResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from the server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<Object> deleteBook(Long id) {
    try {
      if (bookRepository.existsById(id)) {
        bookRepository.deleteById(id);
        ListDataResponse<Object> listDataResponse =
                ListDataResponse.builder().message("Book " + id + " deleted " + "successfully").build();
        return ResponseEntity.ok(listDataResponse);
      } else {
        ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("Book not found").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listDataResponse);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<Object> editBook(Long id, BookRequest bookRequest) {
    try {
      Optional<Book> bookOptional = bookRepository.findById(id);
      if (bookOptional.isPresent()) {
        Book book = bookOptional.get();
        if (bookRepository.existsByBookName(bookRequest.getBookName()) && !bookRequest.getBookName().equals(book.getBookName())) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book already exists!");
        }
        if (!categoryRepository.existsById(bookRequest.getCtgId())) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category Id is not exist!");
        }
        if (!authorRepository.existsById(bookRequest.getAuthorId())) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Author Id is not exist!");
        }

        Author author = authorRepository.getById(bookRequest.getAuthorId());

        book.setBookName(bookRequest.getBookName());
        book.setInventory(bookRequest.getInventory());
        book.setPrice(bookRequest.getPrice());
        book.setDescription(bookRequest.getDescription());

        book.setAuthor(author);
        author.getBooks().add(book);

        Category category = categoryRepository.getById(bookRequest.getCtgId());

        book.setCategory(category);
        category.getBooks().add(book);

        bookRepository.save(book);
        authorRepository.save(author);
        categoryRepository.save(category);

        BookResponse bookResponse = new BookResponse();
        bookResponse.setBookName(book.getBookName());
        bookResponse.setInventory(book.getInventory());
        bookResponse.setPrice(book.getPrice());
        bookResponse.setDescription(book.getDescription());
        bookResponse.setAuthorId(author.getId());
        bookResponse.setCtgId(category.getId());

        ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("OK").data(bookResponse).build();
        return ResponseEntity.status(HttpStatus.OK).body(listDataResponse);
      } else {
        ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("Book not found!").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listDataResponse);
      }

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<Object> getBookById(Long id) {
    try {
      Optional<Book> bookOptional = bookRepository.findById(id);
      if (bookOptional.isPresent()) {
        Book book = bookOptional.get();

        BookResponse bookResponse = new BookResponse();
        bookResponse.setBookName(book.getBookName());
        bookResponse.setInventory(book.getInventory());
        bookResponse.setPrice(book.getPrice());
        bookResponse.setDescription(book.getDescription());
        bookResponse.setAuthorId(book.getAuthor().getId());
        bookResponse.setCtgId(book.getCategory().getId());

        ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("OK").data(bookResponse).build();
        return ResponseEntity.ok(listDataResponse);
      } else {
        ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("Book not found!").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listDataResponse);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<Object> searchBooks(String keyword) {
    try {
      List<Book> books = bookRepository.findAllByBookNameContaining(keyword);

      if (!books.isEmpty()) {
        List<BookResponse> bookResponses = books.stream().map(book -> new BookResponse(
                        book.getBookName(),
                        book.getInventory(),
                        book.getPrice(),
                        book.getDescription(),
                        book.getAuthor().getId(),
                        book.getCategory().getId()))
                .collect(Collectors.toList());

        ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("OK")
                .data(bookResponses).build();
        return ResponseEntity.ok(listDataResponse);
      } else {
        ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("No books found!").build();
        return ResponseEntity.ok(listDataResponse);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from the server with exception = " + e);
    }
  }


}
