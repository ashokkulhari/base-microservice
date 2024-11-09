package com.ic.catalogservice.repository;

import com.ic.catalogservice.model.catalog.entity.CatalogDocument;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogRepository extends MongoRepository<CatalogDocument, String> {

    // You can define custom query methods here, for example:
//    Optional<CatalogDocument> findByProductId(Long productId);

    boolean existsCatalogEntityByCatalogName(final String name);
}
