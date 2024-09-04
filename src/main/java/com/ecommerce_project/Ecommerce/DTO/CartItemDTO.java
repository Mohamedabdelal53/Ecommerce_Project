package com.ecommerce_project.Ecommerce.DTO;

import com.ecommerce_project.Ecommerce.DTO.CartDTO;
import com.ecommerce_project.Ecommerce.DTO.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long cartItemId;
    private CartDTO cart;
    private ProductDTO product;
    private Integer quantity;
    private double discount;
    private double productPrice;
}
