package com.springmicroservice.services;

import com.springmicroservice.dto.ProviderProduct;
import com.springmicroservice.entities.Product;
import com.springmicroservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductManagementService {
    
    private final FakeProviderApiService providerApiService;
    private final ProductRepository productRepository;

    public ProductManagementService(FakeProviderApiService providerApiService, ProductRepository productRepository) {
        this.providerApiService = providerApiService;
        this.productRepository = productRepository;
    }

    public void importProviderProducts(){
        
        List<ProviderProduct> providerProducts = providerApiService.getBaseProducts();
        
        List<Product> products = providerProducts.stream()
                .map(ProviderProduct::toEntity)
                .peek(product -> {
                    product.setProvider("FakeStoreApi");
                    product.setActive(true);
                })
                .toList();
        
        productRepository.saveAll(products);
        
    }
    
}