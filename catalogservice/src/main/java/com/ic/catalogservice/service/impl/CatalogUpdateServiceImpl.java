package com.ic.catalogservice.service.impl;

import com.ic.catalogservice.exception.CatalogAlreadyExistException;
import com.ic.catalogservice.exception.CatalogNotFoundException;
import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.dto.request.CatalogUpdateRequest;
import com.ic.catalogservice.model.catalog.entity.CatalogDocument;
import com.ic.catalogservice.model.catalog.mapper.CatalogEntityToCatalogMapper;
import com.ic.catalogservice.model.catalog.mapper.CatalogUpdateRequestToCatalogEntityMapper;
import com.ic.catalogservice.repository.CatalogRepository;
import com.ic.catalogservice.service.CatalogUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link CatalogUpdateServiceImpl} for updating catalogs.
 */
@Service
@RequiredArgsConstructor
public class CatalogUpdateServiceImpl implements CatalogUpdateService {

    private final CatalogRepository catalogRepository;

    private final CatalogUpdateRequestToCatalogEntityMapper catalogUpdateRequestToCatalogEntityMapper =
            CatalogUpdateRequestToCatalogEntityMapper.initialize();

    private final CatalogEntityToCatalogMapper catalogEntityToCatalogMapper =
            CatalogEntityToCatalogMapper.initialize();

    /**
     * Updates a catalog identified by its unique ID using the provided update request.
     *
     * @param catalogId           The ID of the catalog to update.
     * @param catalogUpdateRequest The request containing updated data for the catalog.
     * @return The updated Catalog object.
     * @throws CatalogNotFoundException If no catalog with the given ID exists.
     * @throws CatalogAlreadyExistException If another catalog with the updated name already exists.
     */
    @Override
    public Catalog updateCatalogById(String catalogId, CatalogUpdateRequest catalogUpdateRequest) {

        checkCatalogNameUniqueness(catalogUpdateRequest.getCatalogName());

        final CatalogDocument catalogDocumentToBeUpdate = catalogRepository
                .findById(catalogId)
                .orElseThrow(() -> new CatalogNotFoundException("With given catalogID = " + catalogId));

        catalogUpdateRequestToCatalogEntityMapper.mapForUpdating(catalogDocumentToBeUpdate, catalogUpdateRequest);

        CatalogDocument updatedCatalogDocument = catalogRepository.save(catalogDocumentToBeUpdate);

        return catalogEntityToCatalogMapper.map(updatedCatalogDocument);

    }

    /**
     * Checks if a catalog with the updated name already exists in the repository.
     *
     * @param catalogName The updated name of the catalog to check.
     * @throws CatalogAlreadyExistException If another catalog with the updated name already exists.
     */
    private void checkCatalogNameUniqueness(final String catalogName) {
        if (catalogRepository.existsCatalogEntityByCatalogName(catalogName)) {
            throw new CatalogAlreadyExistException("With given catalog name = " + catalogName);
        }
    }

}
