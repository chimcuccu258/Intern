package com.example.be.service.impl;

import com.example.be.model.Category;
import com.example.be.payload.request.CategoryRequest;
import com.example.be.payload.response.CategoryResponse;
import com.example.be.payload.response.ListDataResponse;
import com.example.be.repository.CategoryRepository;
import com.example.be.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  CategoryRepository categoryRepository;

  @Override
  public ResponseEntity<Object> addCtg(CategoryRequest categoryRequest) {
    try {
      if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category already exists!");
      }
      if (!validateCtg(categoryRequest.getCategoryName())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid category name!");
      }

      Category category = new Category();
      category.setCategoryName(categoryRequest.getCategoryName());
      categoryRepository.save(category);

      CategoryResponse categoryResponse = new CategoryResponse();
      categoryResponse.setCategoryName(category.getCategoryName());

      ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("OK").data(categoryResponse).build();
      return ResponseEntity.status(HttpStatus.CREATED).body(listDataResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<Object> getAll() {
    try {
      List<Category> categories = categoryRepository.findAll();
      ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("OK").data(categories).build();
      return ResponseEntity.ok(listDataResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<Object> deleteCtg(Long id) {
    try {
      if (categoryRepository.existsById(id)) {
        categoryRepository.deleteById(id);
        ListDataResponse<Object> listDataResponse = ListDataResponse.builder()
                .message("Category " + id + " deleted " + "successfully").build();
        return ResponseEntity.ok(listDataResponse);
      } else {
        ListDataResponse<Object> listDataResponse = ListDataResponse.builder()
                .message("Category not found").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listDataResponse);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from server with exception = " + e);
    }
  }

  @Override
  public ResponseEntity<Object> editCtg(Long id, CategoryRequest categoryRequest) {
    try {
      Optional<Category> categoryOptional = categoryRepository.findById(id);
      if (categoryOptional.isPresent()) {
        Category category = categoryOptional.get();
        if (categoryRequest.getCategoryName().isBlank()) {
          return ResponseEntity.badRequest().body("Category name is required!");
        }
        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName()) && !categoryRequest.getCategoryName().equals(category.getCategoryName())) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category already exists!");
        }
        category.setCategoryName(categoryRequest.getCategoryName());
        categoryRepository.save(category);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryName(category.getCategoryName());

        ListDataResponse<Object> listDataResponse = ListDataResponse.builder()
                .message("Author name updated successfully!").data(categoryResponse).build();
        return ResponseEntity.status(HttpStatus.OK).body(listDataResponse);
      } else {
        ListDataResponse<Object> listDataResponse = ListDataResponse.builder().message("Category not found!").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listDataResponse);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An exception occurred from the server with exception = " + e);
    }
  }

  public boolean validateCtg(String ctgName) {
    String regex = "^[a-zA-Z]+$";
    if (ctgName.matches(regex)) {
      System.out.println("Category name is valid!");
      return true;
    } else {
      System.out.println("Category name is invalid!");
      return false;
    }
  }
}
