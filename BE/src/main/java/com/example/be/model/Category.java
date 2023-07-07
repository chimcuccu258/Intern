package com.example.be.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String categoryName;

  @JsonManagedReference
  @OneToMany(
          mappedBy = "category",
          cascade = CascadeType.ALL,
          fetch = FetchType.LAZY)
  private List<Book> books = new ArrayList<>();
}