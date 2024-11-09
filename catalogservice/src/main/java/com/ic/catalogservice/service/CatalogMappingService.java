package com.ic.catalogservice.service;

import com.ic.catalogservice.client.ProductServiceClient;
import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.dto.response.CatalogResponse;
import com.ic.catalogservice.model.catalog.dto.response.ProductResponse;
import com.ic.catalogservice.model.catalog.mapper.CatalogToCatalogResponseMapper;
import com.ic.catalogservice.model.common.dto.response.CustomResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CatalogMappingService {

    private final ProductServiceClient productServiceClient;
    private final CatalogToCatalogResponseMapper catalogToCatalogResponseMapper = CatalogToCatalogResponseMapper.initialize();
    public CatalogResponse mapToCatalog(Catalog catalog) {
//        List<Mono<ProductResponse>> productResponseMonos = catalog.getProductIds() == null
//                ? Collections.emptyList()
//                : catalog.getProductIds().stream()
//                .map(id -> fetchProductById(id, productServiceClient))
//                .collect(Collectors.toList());


//        List<ProductResponse> productResponses = Mono.zip(productResponseMonos, results ->
//                Arrays.stream(results)
//                        .map(ProductResponse.class::cast)
//                        .collect(Collectors.toList())
//        ).block();
        List<ProductResponse> productResponses = catalog.getProductIds() == null
                ? Collections.emptyList()
                : catalog.getProductIds().stream()
                .map(id -> fetchProductById(id, productServiceClient))
                .collect(Collectors.toList());

        CatalogResponse catalogResponse = catalogToCatalogResponseMapper.map(catalog);
        catalogResponse.setProducts(productResponses);
        return catalogResponse;
    }

//    private Mono<ProductResponse> fetchProductById(Long productId, ProductServiceClient productServiceClient) {
//        System.out.println("--fetchProductById--");
//        return Mono.fromCallable(() -> productServiceClient.getProductById(productId))
//                .subscribeOn(Schedulers.boundedElastic())
//                .map(CustomResponse::getResponse);
//    }
    public ProductResponse fetchProductById(Long id, ProductServiceClient productServiceClient) {
        System.out.println("--fetchProductById--");
        CustomResponse<ProductResponse> customResponse= productServiceClient.getProductById(id); // Ensure this method is synchronous
        try{
            return customResponse.getResponse();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

}

