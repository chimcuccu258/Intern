package com.example.be.Service;

import com.example.be.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User registerUser(User user);
    ResponseEntity<String> loginUser(String userName, String password);
}
