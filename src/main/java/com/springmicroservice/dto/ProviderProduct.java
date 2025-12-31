package com.springmicroservice.dto;

import com.springmicroservice.entities.Product;
import lombok.Data;

@Data
public class ProviderProduct {
    
    private int id;
    
    private String title;
    
    private float price;
    
    private String description;
    
    private String category;
    
    public Product toEntity(){
        Product entity = new Product();
        
        entity.setPrice((float) (this.price * 1.2));
        entity.setTitle(this.getTitle());
        entity.setDescription(this.getDescription());
        entity.setCategory(this.category);
        entity.setProviderId(this.id);
        
        return entity; 
    }
}
