package com.springmicroservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.math.BigDecimal;

@Data
@Entity
@Audited
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String title;
    
    private BigDecimal price;
    
    private String description;
    
    private String category;

    @OneToOne
    @JoinColumn(name = "provider_id")
    @NotAudited
    private Provider provider;

    private int providerProductId;
    
    private boolean active;

}
