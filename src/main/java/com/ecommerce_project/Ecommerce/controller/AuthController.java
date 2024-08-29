package com.ecommerce_project.Ecommerce.controller;

import com.ecommerce_project.Ecommerce.model.RegisterationDTO;
import com.ecommerce_project.Ecommerce.repository.RoleRepo;
import com.ecommerce_project.Ecommerce.repository.UserRepo;
import com.ecommerce_project.Ecommerce.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;
    private RegisterService registerService;


    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepo userRepo,
                          RoleRepo roleRepo,
                          PasswordEncoder passwordEncoder,
                          RegisterService registerService) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.registerService = registerService;

    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterationDTO registerationDTO){
        if(userRepo.findByUsername(registerationDTO.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Username already exists");
        }else{
            return ResponseEntity.ok(registerService.addUser(registerationDTO));
        }
    }
}
