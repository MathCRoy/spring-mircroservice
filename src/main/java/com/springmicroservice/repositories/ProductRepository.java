package com.springmicroservice.repositories;

import com.springmicroservice.entities.Product;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@JaversSpringDataAuditable
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProviderName (String providerName);
}
