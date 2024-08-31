package com.ecommerce_project.Ecommerce.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @PreAuthorize("ADMIN")
    @GetMapping("/admin")
    public String helloAdmin(){
        return "Hello Admin";
    }
}
