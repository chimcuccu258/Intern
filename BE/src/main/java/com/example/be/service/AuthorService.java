package com.example.be.service;

import com.example.be.payload.request.AuthorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {

  ResponseEntity<Object> getAll();

  ResponseEntity<Object> addAuth(AuthorRequest authorRequest);

  ResponseEntity<Object> deleteAuth(Long id);

  ResponseEntity<Object> editAuth(Long id, AuthorRequest authorRequest);
}
