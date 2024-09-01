package com.ecommerce_project.Ecommerce.service;

import com.ecommerce_project.Ecommerce.DTO.CategoryDTO;
import com.ecommerce_project.Ecommerce.DTO.CategoryDTOs;
import com.ecommerce_project.Ecommerce.entities.Category;
import com.ecommerce_project.Ecommerce.exception.APIException;
import com.ecommerce_project.Ecommerce.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements CategoryServiceimpl{


    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        if(categoryRepo.findByName(categoryDTO.getName()) != null){
            throw new APIException("Category with the name '" + categoryDTO.getName() + "' already exists !!!");
        }
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryDTO.setId(category.getId());
        Category created = categoryRepo.save(category);
        return new CategoryDTO(created.getId(), created.getName());
    }

    @Override
    public CategoryDTOs getcategories() {
        List<CategoryDTO> CategoryDTOList = categoryRepo.findAll().stream().map(category ->
                new CategoryDTO(category.getId(),category.getName()))
                .collect(Collectors.toList());

        return new CategoryDTOs(CategoryDTOList);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
        Category oldCategory = categoryRepo.findById(id).orElseThrow(() -> new APIException("No Category Found With This id "));
        oldCategory.setName(categoryDTO.getName());
        Category updatecategory = categoryRepo.save(oldCategory);
        return new CategoryDTO(updatecategory.getId(), updatecategory.getName());
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepo.deleteById(id);
    }

}
