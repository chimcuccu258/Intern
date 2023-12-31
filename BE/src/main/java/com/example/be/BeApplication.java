package com.example.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.be")
public class BeApplication {

  public static void main(String[] args) {
    SpringApplication.run(BeApplication.class, args);
  }

}
