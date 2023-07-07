package com.example.be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/backend/")
public class DemoController {
  @GetMapping("/demo")
  public String demo() {
    return "Hello";
  }
}
