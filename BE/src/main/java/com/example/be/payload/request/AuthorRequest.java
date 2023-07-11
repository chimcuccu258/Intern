package com.example.be.payload.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthorRequest {

  @Size(max = 255, message = "Too long!")
  private String authorName;

}
