package com.ecommerce_project.Ecommerce.service;

import com.ecommerce_project.Ecommerce.DTO.CategoryDTO;
import com.ecommerce_project.Ecommerce.DTO.CategoryDTOs;

public interface CategoryServiceimpl {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTOs getcategories();
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id);
    void deleteCategory(Long id);
}
