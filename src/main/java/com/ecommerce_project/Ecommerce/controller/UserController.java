package com.ecommerce_project.Ecommerce.controller;

import com.ecommerce_project.Ecommerce.DTO.UserDTO;
import com.ecommerce_project.Ecommerce.entities.Users;
import com.ecommerce_project.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/admin/promote/{userId}")
    public ResponseEntity<?> promoteToAdmin(@PathVariable Long userId) {
        Users updatedUser = userService.promoteToAdmin(userId);
        return ResponseEntity.ok("User " + updatedUser.getUsername() + " has been promoted to admin");
    }


    @GetMapping("/user/my-profile")
    public ResponseEntity<?> getUserById() {
        try {
            UserDTO userDTO = userService.getMyUser();
            if (userDTO != null) {
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
