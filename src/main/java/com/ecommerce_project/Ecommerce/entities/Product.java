package com.ecommerce_project.Ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @Lob
    private String description;

    private BigDecimal price;

    private int stock;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @Column(length = 255)
    private String imageUrl;  // Field for image URL

    @OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    private List<CartItem> cartItem = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<OrderItem> orderItems = new ArrayList<>();

}
