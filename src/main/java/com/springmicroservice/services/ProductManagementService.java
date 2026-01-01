package com.springmicroservice.services;

import com.springmicroservice.entities.Product;
import com.springmicroservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductManagementService {
    
    private final FakeProviderApiService providerApiService;
    private final ProductRepository productRepository;
    private final String fakeStoreProvider = "FakeStoreApi";

    // TODO Learn more about :actionlist
    public ProductManagementService(FakeProviderApiService providerApiService, ProductRepository productRepository) {
        this.providerApiService = providerApiService;
        this.productRepository = productRepository;
    }

    public void importProviderProducts(){
        
        Map<Integer, Product> providerProducts = getProviderProducts(fakeStoreProvider);
        Map<Integer, Product> dbProducts = getDbProducts(fakeStoreProvider);

        List<Product> toUpdate = getProductsToUpdate(providerProducts, dbProducts);
        List<Product> toDeactivate = getProductsToDeactivate(providerProducts, dbProducts);
        List<Product> toInsert = getProductsToInsert(providerProducts, dbProducts);
        
        List<Product> toSave = new ArrayList<>();
        toSave.addAll(toUpdate);
        toSave.addAll(toDeactivate);
        toSave.addAll(toInsert);
        
        productRepository.saveAll(toSave);
        
    }
    
    private Map<Integer, Product> getProviderProducts(String provider){
        return providerApiService.getBaseProducts().stream()
                .map(providerProduct -> {
                    Product product = providerProduct.toEntity();
                    product.setProvider(provider);
                    product.setActive(true);
                    return product;
                })
                .collect(Collectors.toMap(Product::getProviderId, Function.identity()));
    }

    private Map<Integer, Product> getDbProducts(String provider){
       return productRepository.findByProvider(provider)
                .stream()
                .collect(Collectors.toMap(Product::getProviderId, Function.identity()));
    }

    private List<Product> getProductsToUpdate(Map<Integer, Product> providerProducts, Map<Integer, Product> dbProducts) {
        return dbProducts.entrySet().stream()
                .filter(dbProduct -> providerProducts.containsKey(dbProduct.getKey()))
                .map(dbProduct ->{
                    int providerId = dbProduct.getKey();
                    Product product = providerProducts.get(providerId);
                    product.setId(dbProduct.getValue().getId());
                    return product;
                })
                .toList();
    }

    private List<Product> getProductsToDeactivate(Map<Integer, Product> providerProducts, Map<Integer, Product> dbProducts) {
        return dbProducts.entrySet().stream()
                .filter(entry -> !providerProducts.containsKey(entry.getKey()))
                .map(entry ->{
                    Product product = entry.getValue();
                    product.setActive(false);
                    return product;
                })
                .toList();
    }
    
    private List<Product> getProductsToInsert(Map<Integer, Product> providerProducts, Map<Integer, Product> dbProducts) {
        return providerProducts.entrySet().stream()
                .filter(providerProduct -> !dbProducts.containsKey(providerProduct.getKey()))
                .map(Map.Entry::getValue)
                .toList();
    }

}