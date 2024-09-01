package com.ecommerce_project.Ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime paymentDate;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String paymentMethod;


}