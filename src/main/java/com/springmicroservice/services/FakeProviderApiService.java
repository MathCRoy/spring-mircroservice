package com.springmicroservice.services;

import com.springmicroservice.dto.ProviderProduct;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class FakeProviderApiService {
    
    String baseURL = "https://fakestoreapi.com";

    RestClient restClient = getRestClient();

    private RestClient getRestClient(){
        return RestClient.builder().
                baseUrl(baseURL)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public List<ProviderProduct> getBaseProducts(){

        List<ProviderProduct> result = restClient.get()
                .uri("/products")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ProviderProduct>>() {});

        return result;
    }
    
}
