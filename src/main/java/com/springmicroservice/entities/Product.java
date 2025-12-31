package com.springmicroservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String title;
    
    private float price;
    
    private String description;
    
    private String category;

    private String provider;

    private int providerId;
    
    private boolean active;

}
