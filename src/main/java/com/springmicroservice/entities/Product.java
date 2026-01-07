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

    @OneToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    private int providerProductId;
    
    private boolean active;

}
