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
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String userName;

  @Column
  private String password;

  @Column
  private Boolean gender;

  @Column
  private String phoneNumber;

  @Column
  private String email;

  @JsonManagedReference
  @OneToMany(
          mappedBy = "user",
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  private List<UserRole> userRole = new ArrayList<>();

  @JsonManagedReference
  @OneToMany(
          mappedBy = "user",
          cascade = CascadeType.ALL,
          orphanRemoval = true)
  private List<Bill> bills = new ArrayList<>();

}
