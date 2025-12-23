package com.springmicroservice.controllers;

import com.springmicroservice.dto.ProviderProduct;
import com.springmicroservice.services.FakeProviderApiService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
    
    FakeProviderApiService providerApiService = new FakeProviderApiService();
    
    @RequestMapping("/")
    public String home(){
        return "Hello World! Spring microservice running!";
    }

    @RequestMapping("/get-products/")
    public List<ProviderProduct> getProducts(){
        return providerApiService.getBaseProducts();
    }
    
}
