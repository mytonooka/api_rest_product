package com.apiproduct.controller;

import com.apiproduct.dto.RecoveryProductDto;
import com.apiproduct.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<RecoveryProductDto>> getProducts() {

        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<RecoveryProductDto> getProductById(@PathVariable Long productId) {

        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

}
