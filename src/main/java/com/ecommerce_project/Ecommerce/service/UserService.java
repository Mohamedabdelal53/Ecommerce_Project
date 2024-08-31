package com.ecommerce_project.Ecommerce.service;

import com.ecommerce_project.Ecommerce.DTO.LoginDTO;
import com.ecommerce_project.Ecommerce.entities.Address;
import com.ecommerce_project.Ecommerce.entities.Role;
import com.ecommerce_project.Ecommerce.entities.Users;
import com.ecommerce_project.Ecommerce.DTO.RegisterationDTO;
import com.ecommerce_project.Ecommerce.repository.AddressRepo;
import com.ecommerce_project.Ecommerce.repository.RoleRepo;
import com.ecommerce_project.Ecommerce.repository.UserRepo;
import com.ecommerce_project.Ecommerce.security.JWT.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTGenerator jwtGenerator;

    public String addUser(RegisterationDTO registerationDTO) {
        // Create and save Address entity
        Address address = new Address();
        address.setStreet(registerationDTO.getStreet());
        address.setCity(registerationDTO.getCity());
        address.setState(registerationDTO.getState());
        address.setPostalCode(registerationDTO.getPostalCode());
        address.setCountry(registerationDTO.getCountry());
        Address savedAddress = addressRepo.save(address); // Save address first

        // Create User entity
        Users user = new Users();
        user.setUsername(registerationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerationDTO.getPassword()));
        user.setEmail(registerationDTO.getEmail());

        // Find and set Role
        Optional<Role> role = roleRepo.findByName("ROLE_USER");
        user.setRole(role.get());
        user.setAddress(savedAddress); // Set the saved address to user

        // Save User entity
        userRepo.save(user);

        return "User Registration Success";
    }

    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtGenerator.generateToken(loginDTO.getUsername());
        }
        return "Login Fail";
    }
}
