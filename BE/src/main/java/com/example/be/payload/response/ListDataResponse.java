package com.example.be.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListDataResponse <T>{
  private String message;
  private T data;
}
