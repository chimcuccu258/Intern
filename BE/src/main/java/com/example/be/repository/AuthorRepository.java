package com.example.be.repository;

import com.example.be.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
  boolean existsByAuthorName(String authorName);
}
