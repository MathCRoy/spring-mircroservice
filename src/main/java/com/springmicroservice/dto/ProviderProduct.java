package com.springmicroservice.dto;

import lombok.Data;

@Data
public class ProviderProduct {
    
    private int id;
    
    private String title;
    
    private float price;
    
    private String description;
    
    private String category;
    
}
