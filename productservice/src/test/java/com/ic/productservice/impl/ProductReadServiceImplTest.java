package com.ic.productservice.impl;

import com.ic.productservice.base.AbstractBaseServiceTest;
import com.ic.productservice.exception.ProductNotFoundException;
import com.ic.productservice.model.common.CustomPage;
import com.ic.productservice.model.common.CustomPaging;
import com.ic.productservice.model.product.Product;
import com.ic.productservice.model.product.dto.request.ProductPagingRequest;
import com.ic.productservice.model.product.entity.ProductEntity;
import com.ic.productservice.model.product.mapper.ListProductEntityToListProductMapper;
import com.ic.productservice.model.product.mapper.ProductEntityToProductMapper;
import com.ic.productservice.repository.ProductRepository;
import com.ic.productservice.service.impl.ProductReadServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductReadServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private ProductReadServiceImpl productReadService;

    @Mock
    private ProductRepository productRepository;

    private final ProductEntityToProductMapper productEntityToProductMapper = ProductEntityToProductMapper.initialize();

    private final ListProductEntityToListProductMapper listProductEntityToListProductMapper =
            ListProductEntityToListProductMapper.initialize();

    @Test
    void givenProductEntity_whenFindProductById_thenReturnProduct() {

        // Given
        Long productId = 1L;
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);

        Product expected = productEntityToProductMapper.map(productEntity);

        // When
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        // Then
        Product result = productReadService.getProductById(productId);

        assertNotNull(result);
        assertEquals(expected.getId(), result.getId());

        // Verify
        verify(productRepository, times(1)).findById(productId);

    }

    @Test
    void givenProductEntity_whenProductNotFound_thenThrowProductNotFoundException() {

        // Given
        Long productId = 1L;

        // When
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Then
        assertThrows(ProductNotFoundException.class, () -> productReadService.getProductById(productId));

        // Verify
        verify(productRepository, times(1)).findById(productId);

    }

    @Test
    void givenProductPagingRequest_WhenProductPageList_ThenReturnCustomPageProductList() {

        // Given
        ProductPagingRequest pagingRequest = ProductPagingRequest.builder()
                .pagination(
                        CustomPaging.builder()
                                .pageSize(1)
                                .pageNumber(1)
                                .build()
                ).build();

        Page<ProductEntity> productEntityPage = new PageImpl<>(Collections.singletonList(new ProductEntity()));

        List<Product> products = listProductEntityToListProductMapper.toProductList(productEntityPage.getContent());

        CustomPage<Product> expected = CustomPage.of(products, productEntityPage);

        // When
        when(productRepository.findAll(any(Pageable.class))).thenReturn(productEntityPage);

        // Then
        CustomPage<Product> result = productReadService.getProducts(pagingRequest);

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
        assertEquals(expected.getPageNumber(), result.getPageNumber());
        assertEquals(expected.getContent().get(0).getId(), result.getContent().get(0).getId());
        assertEquals(expected.getTotalPageCount(), result.getTotalPageCount());
        assertEquals(expected.getTotalElementCount(), result.getTotalElementCount());

        // Verify
        verify(productRepository, times(1)).findAll(any(Pageable.class));

    }

    @Test
    void givenProductPagingRequest_WhenNoProductPageList_ThenThrowProductNotFoundException() {

        // Given
        ProductPagingRequest pagingRequest = ProductPagingRequest.builder()
                .pagination(
                        CustomPaging.builder()
                                .pageSize(1)
                                .pageNumber(1)
                                .build()
                ).build();

        Page<ProductEntity> productEntityPage = new PageImpl<>(Collections.emptyList());

        // When
        when(productRepository.findAll(any(Pageable.class))).thenReturn(productEntityPage);

        // Then
        assertThrows(ProductNotFoundException.class, () -> productReadService.getProducts(pagingRequest));

        // Verify
        verify(productRepository, times(1)).findAll(any(Pageable.class));

    }

}