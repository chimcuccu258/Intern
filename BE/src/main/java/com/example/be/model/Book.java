package com.example.be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String bookName;

  @Column
  private Integer inventory;

  @Column
  private Long price;

  @Column
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private Author author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private Category category;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BillDetails> billDetails = new ArrayList<>();
}
