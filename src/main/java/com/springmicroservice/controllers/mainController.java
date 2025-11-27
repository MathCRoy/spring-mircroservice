package com.springmicroservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mainController {
    
    @RequestMapping("/")
    public String home(){
        return "Hello World! Spring microservice running!";
    }
    
}
