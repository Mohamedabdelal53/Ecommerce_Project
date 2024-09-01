package com.ecommerce_project.Ecommerce.controller;

import com.ecommerce_project.Ecommerce.DTO.CategoryDTO;
import com.ecommerce_project.Ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping("/public/category")
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        List<CategoryDTO> savedCategory = categoryService.getcategories();
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }

    @PutMapping("/admin/category/{id}/update")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id){
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO,id);
        return new ResponseEntity<>(updatedCategory, HttpStatus.FOUND);
    }

    @DeleteMapping("/admin/category/{id}/delete")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.CREATED);
    }
}
