package com.ecommerce_project.Ecommerce.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;
    private Long userId;
    private Long shippingAddressId;
    private List<OrderItemDTO> orderItems;

    // Getters and Setters
}
