package com.example.be.service;

import com.example.be.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    ResponseEntity<String> loginUser(String userName, String password);
    ResponseEntity<String> registerUser(User user);
    User addUser(User user);
    void deleteUser(Long userId);
    User updateUser(User user);
    List<User> getAllUser();
}
