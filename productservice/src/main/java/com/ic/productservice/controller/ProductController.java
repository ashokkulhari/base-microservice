package com.ic.productservice.controller;

import com.ic.productservice.model.common.CustomPage;
import com.ic.productservice.model.common.dto.response.CustomPagingResponse;
import com.ic.productservice.model.common.dto.response.CustomResponse;
import com.ic.productservice.model.product.Product;
import com.ic.productservice.model.product.dto.request.ProductCreateRequest;
import com.ic.productservice.model.product.dto.request.ProductPagingRequest;
import com.ic.productservice.model.product.dto.request.ProductUpdateRequest;
import com.ic.productservice.model.product.dto.response.ProductResponse;
import com.ic.productservice.model.product.mapper.CustomPageToCustomPagingResponseMapper;
import com.ic.productservice.model.product.mapper.ProductToProductResponseMapper;
import com.ic.productservice.service.ProductCreateService;
import com.ic.productservice.service.ProductDeleteService;
import com.ic.productservice.service.ProductReadService;
import com.ic.productservice.service.ProductUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller named {@link ProductController} for managing products.
 * Provides endpoints to create, read, update, and delete products.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductCreateService productCreateService;
    private final ProductReadService productReadService;
    private final ProductUpdateService productUpdateService;
    private final ProductDeleteService productDeleteService;

    private final ProductToProductResponseMapper productToProductResponseMapper = ProductToProductResponseMapper.initialize();

    private final CustomPageToCustomPagingResponseMapper customPageToCustomPagingResponseMapper =
            CustomPageToCustomPagingResponseMapper.initialize();

    /**
     * Creates a new product.
     *
     * @param productCreateRequest the request payload containing product details
     * @return a {@link CustomResponse} containing the ID of the created product
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<Long> createProduct(@RequestBody @Valid final ProductCreateRequest productCreateRequest) {
        System.out.println("--creating---"+productCreateRequest);
        final Product createdProduct = productCreateService
                .createProduct(productCreateRequest);
        System.out.println("--createdProduct---"+createdProduct);
        return CustomResponse.successOf(createdProduct.getId());
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId the ID of the product to retrieve
     * @return a {@link CustomResponse} containing the product details
     */
    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<ProductResponse> getProductById(@PathVariable final Long productId) {

        final Product product = productReadService.getProductById(productId);

        final ProductResponse productResponse = productToProductResponseMapper.map(product);

        return CustomResponse.successOf(productResponse);

    }

    /**
     * Retrieves a paginated list of products based on the paging request.
     *
     * @param productPagingRequest the request payload containing paging information
     * @return a {@link CustomResponse} containing the paginated list of products
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<CustomPagingResponse<ProductResponse>> getProducts(
            @RequestBody @Valid final ProductPagingRequest productPagingRequest) {

        final CustomPage<Product> productPage = productReadService.getProducts(productPagingRequest);

        final CustomPagingResponse<ProductResponse> productPagingResponse =
                customPageToCustomPagingResponseMapper.toPagingResponse(productPage);

        return CustomResponse.successOf(productPagingResponse);

    }

    /**
     * Updates an existing product by its ID.
     *
     * @param productUpdateRequest the request payload containing updated product details
     * @param productId the ID of the product to update
     * @return a {@link CustomResponse} containing the updated product details
     */
    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<ProductResponse> updatedProductById(
            @RequestBody @Valid final ProductUpdateRequest productUpdateRequest,
            @PathVariable  final Long productId) {

        final Product updatedProduct = productUpdateService.updateProductById(productId, productUpdateRequest);

        final ProductResponse productResponse = productToProductResponseMapper.map(updatedProduct);

        return CustomResponse.successOf(productResponse);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId the ID of the product to delete
     * @return a {@link CustomResponse} indicating successful deletion
     */
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<Void> deleteProductById(@PathVariable final Long productId) {

        productDeleteService.deleteProductById(productId);
        return CustomResponse.SUCCESS;
    }

}
