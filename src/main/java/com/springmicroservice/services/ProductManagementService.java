package com.springmicroservice.services;

import com.springmicroservice.dto.ProviderProduct;
import com.springmicroservice.entities.Product;
import com.springmicroservice.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductManagementService {
    
    private final FakeProviderApiService providerApiService;
    private final ProductRepository productRepository;

    // TODO Learn more about :actionlist
    public ProductManagementService(FakeProviderApiService providerApiService, ProductRepository productRepository) {
        this.providerApiService = providerApiService;
        this.productRepository = productRepository;
    }

    public void importProviderProducts(){
        
        Map<Integer, Product> providerProducts = providerApiService.getBaseProducts().stream()
                .map(ProviderProduct::toEntity)
                .peek(product -> {
                    product.setProvider("FakeStoreApi");
                    product.setActive(true);
                })
                .collect(Collectors.toMap(Product::getProviderId, Function.identity()));

        Map<Integer, Product> dbProducts = productRepository.findByProvider("FakeStoreApi")
                .stream()
                .collect(Collectors.toMap(Product::getProviderId, Function.identity()));
        
        dbProducts.entrySet().stream()
                .forEach(productEntry ->{
                    int productKey = productEntry.getKey();
                    if (providerProducts.containsKey(productKey)) {
                        int id = productEntry.getValue().getId();
                        productEntry.setValue(providerProducts.get(productKey));
                        productEntry.getValue().setId(id);
                        providerProducts.remove(productKey);
                    } else {
                        productEntry.getValue().setActive(false);
                    }
                });

        dbProducts.putAll(providerProducts);
        
        productRepository.saveAll(dbProducts.values());
        
    }
}