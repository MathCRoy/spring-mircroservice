package com.springmicroservice.dto;

import com.springmicroservice.entities.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class ProviderProduct {
    
    private int id;
    
    private String title;
    
    private float price;
    
    private String description;
    
    private String category;
    
    public Product toEntity(){
        Product entity = new Product();
        
        entity.setPrice(BigDecimal.valueOf(this.price * 1.2)
                .setScale(2, RoundingMode.HALF_UP));
        entity.setTitle(this.getTitle());
        entity.setDescription(this.getDescription());
        entity.setCategory(this.category);
        entity.setProviderProductId(this.id);
        
        return entity; 
    }
}
