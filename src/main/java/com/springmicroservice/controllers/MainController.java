package com.springmicroservice.controllers;

import com.springmicroservice.services.ProductManagementService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    public final ProductManagementService managementService;

    public MainController(ProductManagementService managementService) {
        this.managementService = managementService;
    }

    @RequestMapping("/")
    public String home() {
        return "API running";
    }

    @RequestMapping("/get-products/")
    public Object getProducts() {
        // TODO Too much logic in controller...
        try {
            managementService.importProviderProducts();
            return "Provider products imported";
        } catch (Exception e) {
            return e;
        }
    }

}
