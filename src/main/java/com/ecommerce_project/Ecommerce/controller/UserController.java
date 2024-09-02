package com.ecommerce_project.Ecommerce.controller;

import com.ecommerce_project.Ecommerce.DTO.UserDTO;
import com.ecommerce_project.Ecommerce.repository.UserRepo;
import com.ecommerce_project.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    public UserController(UserRepo userRepo, UserService userService)
    {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getMyUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getMyUser(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> getMyUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateMyUser(id,userDTO));
    }

    // ADMIN

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<String> deleteuser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }


    @GetMapping("/admin/users/{id}")
    public ResponseEntity<UserDTO> getuserbyadmin(@PathVariable Long id){
        return ResponseEntity.ok(userService.getuserbyadmin(id));
    }

}
