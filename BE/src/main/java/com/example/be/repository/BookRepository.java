package com.example.be.repository;

import com.example.be.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
  boolean existsByBookName(String bookName);

  List<Book> findAllByBookNameContaining(String keyword);

  List<Book> findByBookName(String keyword);
}
