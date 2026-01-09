package com.springmicroservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    
}
