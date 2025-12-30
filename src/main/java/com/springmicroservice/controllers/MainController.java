package com.springmicroservice.controllers;

import com.springmicroservice.services.ProductManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    public final ProductManagementService managementService;

    public MainController(ProductManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping("/")
    public ResponseEntity home() {
        return ResponseEntity.ok("Rest API Running!");
    }

    @RequestMapping("/get-products/")
    public ResponseEntity getProducts() {
        managementService.importProviderProducts();
        return ResponseEntity.ok("Provider's product successfully imported");
    }
}
