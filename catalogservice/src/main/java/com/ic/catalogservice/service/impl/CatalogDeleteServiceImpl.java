package com.ic.catalogservice.service.impl;

import com.ic.catalogservice.exception.CatalogNotFoundException;
import com.ic.catalogservice.model.catalog.entity.CatalogDocument;
import com.ic.catalogservice.repository.CatalogRepository;
import com.ic.catalogservice.service.CatalogDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link CatalogDeleteServiceImpl} for deleting catalogs.
 */
@Service
@RequiredArgsConstructor
public class CatalogDeleteServiceImpl implements CatalogDeleteService {

    private final CatalogRepository catalogRepository;

    /**
     * Deletes a catalog identified by its unique ID.
     *
     * @param catalogId The ID of the catalog to delete.
     * @throws CatalogNotFoundException If no catalog with the given ID exists.
     */
    @Override
    public void deleteCatalogById(String catalogId) {

        final CatalogDocument catalogDocumentToBeDelete = catalogRepository
                .findById(catalogId)
                .orElseThrow(() -> new CatalogNotFoundException("With given catalogID = " + catalogId));

        catalogRepository.delete(catalogDocumentToBeDelete);

    }

}

