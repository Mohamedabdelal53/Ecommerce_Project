package com.ecommerce_project.Ecommerce.service;

import com.ecommerce_project.Ecommerce.DTO.LoginDTO;
import com.ecommerce_project.Ecommerce.entities.Address;
import com.ecommerce_project.Ecommerce.entities.Role;
import com.ecommerce_project.Ecommerce.entities.Users;
import com.ecommerce_project.Ecommerce.DTO.UserDTO;
import com.ecommerce_project.Ecommerce.exception.APIException;
import com.ecommerce_project.Ecommerce.repository.AddressRepo;
import com.ecommerce_project.Ecommerce.repository.RoleRepo;
import com.ecommerce_project.Ecommerce.repository.UserRepo;
import com.ecommerce_project.Ecommerce.security.JWT.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceImpl{
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

    @Override
    public String addUser(UserDTO userDTO) {
        // Create and save Address entity
        Address address = new Address();
        address.setStreet(userDTO.getStreet());
        address.setCity(userDTO.getCity());
        address.setState(userDTO.getState());
        address.setPostalCode(userDTO.getPostalCode());
        address.setCountry(userDTO.getCountry());
        Address savedAddress = addressRepo.save(address); // Save address first

        // Create User entity
        Users user = new Users();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());

        // Find and set Role
        Optional<Role> role = roleRepo.findByName("ROLE_USER");
        user.setRole(role.get());
        user.setAddress(savedAddress); // Set the saved address to user

        // Save User entity
        userRepo.save(user);

        return "User Registration Success";
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtGenerator.generateToken(loginDTO.getUsername());
        }
        return "Login Fail";
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = userRepo.findAll().stream().map(user ->{
                Address address = user.getAddress();
                return new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        null,
                        user.getEmail(),
                        address.getStreet(),
                        address.getCity(),
                        address.getState(),
                        address.getPostalCode(),
                        address.getCountry());
                })
                .collect(Collectors.toList());
        return users;
    }

    @Override
    public String deleteUser(Long id){
        Users user = userRepo.findById(id).orElseThrow(()->new APIException("User Not Found"));
        String name = user.getUsername();
        userRepo.deleteById(id);
        return name+" Account Is Deleted";
    }

    @Override
    public UserDTO getMyUser(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();

        Users user = userRepo.findById(id).orElseThrow(()->new APIException("User Not Found"));

        // Check if the authenticated user is trying to show their own profile
        if (!user.getUsername().equals(authenticatedUsername)) {
            throw new APIException("You are not authorized to update this user's profile");
        }

        Address userAddress = user.getAddress();
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(),user.getEmail() ,
                userAddress.getStreet(), userAddress.getCity(), userAddress.getState(),
                userAddress.getPostalCode(), userAddress.getCountry());
    }

    @Override
    public String updateMyUser(Long id, UserDTO userDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();

        // Fetch the user to be updated
        Users user = userRepo.findById(id).orElseThrow(() -> new APIException("User Not Found"));

        // Check if the authenticated user is trying to update their own profile
        if (!user.getUsername().equals(authenticatedUsername)) {
            throw new APIException("You are not authorized to update this user's profile");
        }

        Address address = user.getAddress();
        address.setStreet(userDTO.getStreet());
        address.setCity(userDTO.getCity());
        address.setState(userDTO.getState());
        address.setPostalCode(userDTO.getPostalCode());
        address.setCountry(userDTO.getCountry());
        Address savedAddress = addressRepo.save(address);

        // Save address first
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());

        // Find and set Role
        if(user.getRole().getName() != "ROLE_ADMIN"){
            Optional<Role> role = roleRepo.findByName("ROLE_USER");
            user.setRole(role.get());
        }
        user.setAddress(savedAddress); // Set the saved address to user

        // Save User entity
        userRepo.save(user);

        return "User Updated Success";
    }
}
