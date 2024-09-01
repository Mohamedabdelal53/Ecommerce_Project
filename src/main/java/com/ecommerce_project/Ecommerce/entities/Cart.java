package com.ecommerce_project.Ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    // Getters and Setters
}
