package com.ecommerce_project.Ecommerce.service;

import com.ecommerce_project.Ecommerce.DTO.ProductDTO;
import com.ecommerce_project.Ecommerce.entities.Category;
import com.ecommerce_project.Ecommerce.entities.Product;
import com.ecommerce_project.Ecommerce.exception.APIException;
import com.ecommerce_project.Ecommerce.impl.ProductServiceImpl;
import com.ecommerce_project.Ecommerce.repository.CategoryRepo;
import com.ecommerce_project.Ecommerce.repository.ProductRepo;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements ProductServiceImpl {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductDTO> getProducts() {
        return productRepo.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO addProduct(@NotNull Long categoryID, @NotNull Product product, MultipartFile image) throws IOException {
        Category category = categoryRepo.findById(categoryID)
                .orElseThrow(() -> new APIException("This Category Does Not Exist"));

        if (productRepo.existsByNameAndDescription(product.getName(), product.getDescription())) {
            throw new APIException("Product already exists!");
        }

        if (image != null && !image.isEmpty()) {
            String imageUrl = saveImage(image);
            product.setImageUrl(imageUrl);
        } else {
            product.setImageUrl("default.png");
        }

        product.setCategory(category);
        Product savedProduct = productRepo.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(@NotNull Long productId,@NotNull Product product) {
        Product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new APIException("Product not found"));


        product.setId(productId);
        product.setCategory(existingProduct.getCategory());
        product.setImageUrl(existingProduct.getImageUrl());

        Product updatedProduct = productRepo.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    public ProductDTO updateProductImage(@NotNull Long productId, MultipartFile image) throws IOException {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new APIException("Product not found"));

        if (image == null || image.isEmpty()) {
            throw new APIException("Invalid image file");
        }

        // Save the new image and update the product
        String imageUrl = saveImage(image);
        product.setImageUrl(imageUrl);

        Product savedProduct = productRepo.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    private String saveImage(MultipartFile image) throws IOException {
        String uploadDir = "uploaded-images/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath); // Create the directory if it doesn't exist
        }

        String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path imagePath = uploadPath.resolve(imageName);
        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        return "/images/" + imageName; // Return the path or URL where the image is stored
    }

    @Override
    public String deleteProduct(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new APIException("Product not found"));
        String prodName = product.getName();
        productRepo.deleteById(productId);
        return prodName + " Deleted";
    }


    @Override
    public ProductDTO getProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()->new APIException("Product Not Found"));
        return modelMapper.map(product, ProductDTO.class);
    }
}
