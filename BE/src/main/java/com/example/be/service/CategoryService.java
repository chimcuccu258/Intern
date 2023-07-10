package com.example.be.service;

import com.example.be.payload.request.CategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

  ResponseEntity<Object> getAll();

  ResponseEntity<Object> addCtg(CategoryRequest categoryRequest);

  ResponseEntity<Object> deleteCtg(Long id);

  ResponseEntity<Object> editCtg(Long id, CategoryRequest categoryRequest);
}
