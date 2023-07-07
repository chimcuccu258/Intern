package com.example.be.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
  private String bookName;
  private Integer inventory;
  private Long price;
  private String description;
  private Long authorId;
  private Long ctgId;
}
