package com.ecommerce_project.Ecommerce.service;

import com.ecommerce_project.Ecommerce.entities.Address;
import com.ecommerce_project.Ecommerce.entities.Role;
import com.ecommerce_project.Ecommerce.entities.Users;
import com.ecommerce_project.Ecommerce.model.RegisterationDTO;
import com.ecommerce_project.Ecommerce.repository.AddressRepo;
import com.ecommerce_project.Ecommerce.repository.RoleRepo;
import com.ecommerce_project.Ecommerce.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepo addressRepo;

    @Autowired
    public RegisterService(UserRepo userRepo,
                           RoleRepo roleRepo,
                           PasswordEncoder passwordEncoder,
                           AddressRepo addressRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.addressRepo = addressRepo;
    }

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
        Optional<Role> role = roleRepo.findByName("USER");
        if (role.isEmpty()) {
            throw new RuntimeException("Role USER not found");
        }
        user.setRole(role.get());
        user.setAddress(savedAddress); // Set the saved address to user

        // Save User entity
        userRepo.save(user);

        return "User Registration Success";
    }
}
