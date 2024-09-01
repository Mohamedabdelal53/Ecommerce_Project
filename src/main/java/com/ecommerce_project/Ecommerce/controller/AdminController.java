package com.ecommerce_project.Ecommerce.controller;

import com.ecommerce_project.Ecommerce.DTO.RegisterationDTO;
import com.ecommerce_project.Ecommerce.repository.UserRepo;
import com.ecommerce_project.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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


    @GetMapping("/admin")
    public String helloAdmin(){
        return "Hello Admin";
    }
    @PostMapping("/admin/register")
    public ResponseEntity<String> addminregister(@RequestBody RegisterationDTO registerationDTO){
        if(userRepo.findByUsername(registerationDTO.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Username already exists");
        }else{
            return ResponseEntity.ok(userService.addAdmin(registerationDTO));
        }
    }

}
