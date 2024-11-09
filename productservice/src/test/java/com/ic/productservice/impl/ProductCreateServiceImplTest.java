package com.ic.productservice.impl;

import com.ic.productservice.base.AbstractBaseServiceTest;
import com.ic.productservice.exception.ProductAlreadyExistException;
import com.ic.productservice.model.product.Product;
import com.ic.productservice.model.product.dto.request.ProductCreateRequest;
import com.ic.productservice.model.product.entity.ProductEntity;
import com.ic.productservice.model.product.mapper.ProductCreateRequestToProductEntityMapper;
import com.ic.productservice.model.product.mapper.ProductEntityToProductMapper;
import com.ic.productservice.repository.ProductRepository;
import com.ic.productservice.service.impl.ProductCreateServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductCreateServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private ProductCreateServiceImpl productCreateService;

    @Mock
    private ProductRepository productRepository;

    private final ProductCreateRequestToProductEntityMapper productCreateRequestToProductEntityMapper =
            ProductCreateRequestToProductEntityMapper.initialize();

    private final ProductEntityToProductMapper productEntityToProductMapper = ProductEntityToProductMapper.initialize();

    @Test
    void givenProductCreateRequest_whenProductCreated_thenReturnProduct() {

        // Given
        String productName = "Test Product";
        ProductCreateRequest productCreateRequest = ProductCreateRequest.builder()
                .name(productName)
                .unitPrice(BigDecimal.valueOf(12))
                .amount(BigDecimal.valueOf(5))
                .build();

        ProductEntity productEntity = productCreateRequestToProductEntityMapper.mapForSaving(productCreateRequest);

        Product expected = productEntityToProductMapper.map(productEntity);

        // When
        when(productRepository.existsProductEntityByName(productName)).thenReturn(false);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        // Then
        Product createdProduct = productCreateService.createProduct(productCreateRequest);

        assertNotNull(createdProduct);
        assertEquals(expected.getName(), createdProduct.getName());
        assertEquals(expected.getAmount(), createdProduct.getAmount());
        assertEquals(expected.getUnitPrice(), createdProduct.getUnitPrice());

        // Verify
        verify(productRepository, times(1)).existsProductEntityByName(productName);
        verify(productRepository, times(1)).save(any(ProductEntity.class));

    }

    @Test
    void givenProductCreateRequest_whenProductAlreadyExists_ThenReturnProductAlreadyExistException() {

        // Given
        String productName = "Existing Product";
        ProductCreateRequest productCreateRequest = new ProductCreateRequest();
        productCreateRequest.setName(productName);

        // When
        when(productRepository.existsProductEntityByName(productName)).thenReturn(true);

        // Then
        ProductAlreadyExistException productAlreadyExistException =
                assertThrows(ProductAlreadyExistException.class, () -> productCreateService.createProduct(productCreateRequest));

        assertEquals("Product already exist!\n There is another product with given name: " + productName,
                productAlreadyExistException.getMessage());

        // Verify
        verify(productRepository, times(1)).existsProductEntityByName(productName);
        verify(productRepository, never()).save(any(ProductEntity.class));

    }

}