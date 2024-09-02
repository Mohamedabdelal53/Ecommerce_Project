package com.ecommerce_project.Ecommerce.repository;

import com.ecommerce_project.Ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    boolean existsByNameAndDescription(String name, String description);
}
