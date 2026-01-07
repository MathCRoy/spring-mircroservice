package com.springmicroservice.controllers;

import com.springmicroservice.services.ProductManagementService;
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
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Rest API Running");
    }

    @RequestMapping("/import-provider-products/")
    public ResponseEntity<String> getProducts() {
        managementService.importProviderProducts();
        return ResponseEntity.ok("Provider's product successfully imported");
    }
}
