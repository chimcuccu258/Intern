package com.example.be.payload.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BookRequest {
  @NotBlank
  private String bookName;
  @Min(value = 1, message = "Invalid inventory value!")
  private Integer inventory;
  @Min(value = 0, message = "Invalid price value!")
  private Long price;
  private String description;
  private Long authorId;
  private String authorName;
  private Long ctgId;
}
