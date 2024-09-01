package com.ecommerce_project.Ecommerce.DTO;

import com.ecommerce_project.Ecommerce.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private Category category;
    private String imageUrl;

}
