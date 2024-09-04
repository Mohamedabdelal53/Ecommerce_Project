package com.ecommerce_project.Ecommerce.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce_project.Ecommerce.DTO.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long cartId;
    private BigDecimal totalAmount;
    private List<ProductDTO> products = new ArrayList<>();

}
