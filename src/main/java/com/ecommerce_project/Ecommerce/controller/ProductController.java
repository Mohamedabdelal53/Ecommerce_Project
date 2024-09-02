package com.ecommerce_project.Ecommerce.controller;

import com.ecommerce_project.Ecommerce.DTO.ProductDTO;
import com.ecommerce_project.Ecommerce.entities.Product;
import com.ecommerce_project.Ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(
            @PathVariable Long categoryId,
            @RequestParam("product") String productJson,
            @RequestParam("image") MultipartFile image) throws IOException {

        // Convert JSON string to Product object
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productJson, Product.class);

        // Save the product with the image
        ProductDTO savedProduct = productService.addProduct(categoryId, product, image);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/admin/categories/product/{productID}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody Product product, @PathVariable Long productID){
        ProductDTO savedProduct = productService.updateProduct(productID, product);
        return new ResponseEntity<ProductDTO>(savedProduct, HttpStatus.FOUND);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        return new ResponseEntity<>(productService.getProducts(),HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> getproduct(@PathVariable Long productId){
        return new ResponseEntity<>(productService.getProduct(productId),HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{id}")
    public String deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }
    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile image) {
        try {
            ProductDTO updatedProduct = productService.updateProductImage(productId, image);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
