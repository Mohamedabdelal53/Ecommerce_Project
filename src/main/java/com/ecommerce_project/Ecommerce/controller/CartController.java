package com.ecommerce_project.Ecommerce.controller;


import com.ecommerce_project.Ecommerce.DTO.CartDTO;
import com.ecommerce_project.Ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public CartDTO addToCart(@RequestParam Long productId, @RequestParam Integer quantity){
        return cartService.addToCart(productId,quantity);
    }

    @PutMapping("/update")
    public CartDTO updateCart(@RequestParam Long productId, @RequestParam Integer quantity) {
        return cartService.updateCart(productId, quantity);
    }

    @DeleteMapping("/delete")
    public String deleteFromCart(@RequestParam Long productId){
        return cartService.deleteFromCart(productId);
    }

    @GetMapping("/admin/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts(){
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<CartDTO> getMyCarts(){
        return new ResponseEntity<>(cartService.getMyCart(), HttpStatus.OK);
    }

}
