package com.ic.productservice.service.impl;

import com.ic.productservice.exception.ProductNotFoundException;
import com.ic.productservice.model.product.entity.ProductEntity;
import com.ic.productservice.repository.ProductRepository;
import com.ic.productservice.service.ProductDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link ProductDeleteServiceImpl} for deleting products.
 */
@Service
@RequiredArgsConstructor
public class ProductDeleteServiceImpl implements ProductDeleteService {

    private final ProductRepository productRepository;

    /**
     * Deletes a product identified by its unique ID.
     *
     * @param productId The ID of the product to delete.
     * @throws ProductNotFoundException If no product with the given ID exists.
     */
    @Override
    public void deleteProductById(Long productId) {

        final ProductEntity productEntityToBeDelete = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("With given productID = " + productId));

        productRepository.delete(productEntityToBeDelete);

    }

}

