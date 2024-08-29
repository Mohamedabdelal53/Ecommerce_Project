package com.ecommerce_project.Ecommerce.repository;

import com.ecommerce_project.Ecommerce.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
}
