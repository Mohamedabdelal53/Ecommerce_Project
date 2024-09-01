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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    public AdminController(UserRepo userRepo, UserService userService)
    {
        this.userRepo = userRepo;
        this.userService = userService;
    }


    @GetMapping("/")
    public String helloAdmin(){
        return "Hello Admin";
    }
    @PostMapping("/register")
    public ResponseEntity<String> addminregister(@RequestBody UserDTO userDTO){
        if(userRepo.findByUsername(userDTO.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Username already exists");
        }else{
            return ResponseEntity.ok(userService.addAdmin(userDTO));
        }
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> addminregister(){
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> addminregister(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
