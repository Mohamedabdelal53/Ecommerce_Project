package com.ecommerce_project.Ecommerce.controller;

import com.ecommerce_project.Ecommerce.DTO.LoginDTO;
import com.ecommerce_project.Ecommerce.DTO.RegisterationDTO;
import com.ecommerce_project.Ecommerce.repository.RoleRepo;
import com.ecommerce_project.Ecommerce.repository.UserRepo;
import com.ecommerce_project.Ecommerce.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Login Success", HttpStatus.OK);
    }


    @PreAuthorize("has('ADMIN')")
    @PostMapping("/admin/register")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterationDTO registerationDTO){
        if(userRepo.findByUsername(registerationDTO.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Username already exists");
        }else{
            return ResponseEntity.ok(registerService.addAdmin(registerationDTO));
        }
    }
}
