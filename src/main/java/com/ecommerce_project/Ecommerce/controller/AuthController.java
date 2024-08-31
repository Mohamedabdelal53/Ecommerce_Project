package com.ecommerce_project.Ecommerce.controller;

import com.ecommerce_project.Ecommerce.DTO.LoginDTO;
import com.ecommerce_project.Ecommerce.DTO.RegisterationDTO;
import com.ecommerce_project.Ecommerce.repository.RoleRepo;
import com.ecommerce_project.Ecommerce.repository.UserRepo;
import com.ecommerce_project.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterationDTO registerationDTO){
        if(userRepo.findByUsername(registerationDTO.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Username already exists");
        }else{
            return ResponseEntity.ok(userService.addUser(registerationDTO));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(userService.login(loginDTO));
    }
}

