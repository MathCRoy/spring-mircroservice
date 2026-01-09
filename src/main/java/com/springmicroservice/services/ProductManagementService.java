package com.springmicroservice.services;

import com.springmicroservice.entities.Product;
import com.springmicroservice.entities.Provider;
import com.springmicroservice.repositories.ProductRepository;
import com.springmicroservice.repositories.ProviderRepository;
import org.javers.core.Javers;
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
    private final ProviderRepository providerRepository;
    private final String fakeStoreProvider = "Fake Provider";

    private final Javers javers;
    
    public ProductManagementService(FakeProviderApiService providerApiService, ProductRepository productRepository, ProviderRepository providerRepository, Javers javers) {
        this.providerApiService = providerApiService;
        this.productRepository = productRepository;
        this.providerRepository = providerRepository;
        this.javers = javers;
    }

    public void importProviderProducts(){
        
        Map<Integer, Product> providerProducts = getProviderProducts();
        Map<Integer, Product> dbProducts = getDbProducts();

        List<Product> toUpdate = getProductsToUpdate(providerProducts, dbProducts);
        List<Product> toDeactivate = getProductsToDeactivate(providerProducts, dbProducts);
        List<Product> toInsert = getProductsToInsert(providerProducts, dbProducts);
        
        List<Product> toSave = new ArrayList<>();
        toSave.addAll(toUpdate);
        toSave.addAll(toDeactivate);
        toSave.addAll(toInsert);
        
        productRepository.saveAll(toSave);
        toSave.forEach(product -> javers.commit("Import-Provider-Products-Job",product));
        
    }
    
    private Map<Integer, Product> getProviderProducts(){
        Provider fakeProvider = providerRepository.findByName(fakeStoreProvider);
        return providerApiService.getBaseProducts().stream()
                .map(providerProduct -> {
                    Product product = providerProduct.toEntity();
                    product.setProvider(fakeProvider);
                    product.setActive(true);
                    return product;
                })
                .collect(Collectors.toMap(Product::getProviderProductId, Function.identity()));
    }

    private Map<Integer, Product> getDbProducts(){
        return productRepository.findByProviderName(fakeStoreProvider)
                .stream()
                .collect(Collectors.toMap(Product::getProviderProductId, Function.identity()));
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