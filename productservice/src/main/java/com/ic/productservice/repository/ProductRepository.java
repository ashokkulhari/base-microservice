package com.ic.productservice.repository;

import com.ic.productservice.model.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface named {@link ProductRepository} for managing ProductEntity objects in the database.
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    /**
     * Checks if a product entity with the given name exists in the database.
     *
     * @param name the name of the product to check for existence
     * @return true if a product entity with the given name exists, false otherwise
     */
    boolean existsProductEntityByName(final String name);

}
