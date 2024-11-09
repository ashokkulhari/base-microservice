package com.ic.catalogservice.client;

import com.ic.catalogservice.config.FeignClientConfig;
import com.ic.catalogservice.model.catalog.dto.response.ProductResponse;
import com.ic.catalogservice.model.common.dto.response.CustomResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client interface named {@link ProductServiceClient} for interacting with the User Service.
 * Provides methods to validate tokens and retrieve authentication information.
 */
@FeignClient(name = "productservice", path = "/api/v1/products", configuration = FeignClientConfig.class)
public interface ProductServiceClient {


    @GetMapping("/{productId}")
    CustomResponse<ProductResponse> getProductById(@PathVariable  Long productId);



}
