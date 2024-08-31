package com.ecommerce_project.Ecommerce.controller;

import com.ecommerce_project.Ecommerce.DTO.AuthResponseDTO;
import com.ecommerce_project.Ecommerce.DTO.LoginDTO;
import com.ecommerce_project.Ecommerce.DTO.RegisterationDTO;
import com.ecommerce_project.Ecommerce.repository.RoleRepo;
import com.ecommerce_project.Ecommerce.repository.UserRepo;
import com.ecommerce_project.Ecommerce.security.JWT.JWTGenerator;
import com.ecommerce_project.Ecommerce.security.JWT.JwtBlacklistService;
import com.ecommerce_project.Ecommerce.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    private UserService registerService;
    private JWTGenerator jwtGenerator;


    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepo userRepo,
                          RoleRepo roleRepo,
                          PasswordEncoder passwordEncoder,
                          UserService registerService,
                          JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.registerService = registerService;
        this.jwtGenerator = jwtGenerator;

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
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication.getName());
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @Autowired
    private JwtBlacklistService jwtBlacklistService; // Service for handling token blacklist

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            jwtBlacklistService.blacklistToken(token);  // Add token to blacklist
            return ResponseEntity.ok("Logged out successfully");
        }

        return ResponseEntity.badRequest().body("Invalid token");
    }
}
