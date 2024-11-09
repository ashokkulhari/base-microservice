package com.ic.catalogservice.service.impl;


import com.ic.catalogservice.exception.CatalogAlreadyExistException;
import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.dto.request.CatalogCreateRequest;
import com.ic.catalogservice.model.catalog.entity.CatalogDocument;
import com.ic.catalogservice.model.catalog.mapper.CatalogCreateRequestToCatalogEntityMapper;
import com.ic.catalogservice.model.catalog.mapper.CatalogEntityToCatalogMapper;
import com.ic.catalogservice.repository.CatalogRepository;
import com.ic.catalogservice.service.CatalogCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation named {@link CatalogCreateServiceImpl} for creating catalogs.
 */
@Service
@RequiredArgsConstructor
public class CatalogCreateServiceImpl implements CatalogCreateService {

    private final CatalogRepository catalogRepository;

    private final CatalogCreateRequestToCatalogEntityMapper catalogCreateRequestToCatalogEntityMapper =
            CatalogCreateRequestToCatalogEntityMapper.initialize();

    private final CatalogEntityToCatalogMapper catalogEntityToCatalogMapper = CatalogEntityToCatalogMapper.initialize();

    /**
     * Creates a new catalog based on the provided catalog creation request.
     *
     * @param catalogCreateRequest The request containing data to create the catalog.
     * @return The created Catalog object.
     * @throws CatalogAlreadyExistException If a catalog with the same name already exists.
     */
    @Override
    public Catalog createCatalog(CatalogCreateRequest catalogCreateRequest) {

        checkUniquenessCatalogName(catalogCreateRequest.getCatalogName());
        System.out.println("---createCatalog---before save ");

        final CatalogDocument catalogDocumentToBeSave = catalogCreateRequestToCatalogEntityMapper.mapForSaving(catalogCreateRequest);
        System.out.println("---catalogDocumentToBeSave---before save "+catalogDocumentToBeSave);
        CatalogDocument savedCatalogDocument = catalogRepository.save(catalogDocumentToBeSave);

        return catalogEntityToCatalogMapper.map(savedCatalogDocument);

    }

    /**
     * Checks if a catalog with the given name already exists in the repository.
     *
     * @param catalogName The name of the catalog to check.
     * @throws CatalogAlreadyExistException If a catalog with the same name already exists.
     */
    private void checkUniquenessCatalogName(final String catalogName) {
        if (catalogRepository.existsCatalogEntityByCatalogName(catalogName)) {
            throw new CatalogAlreadyExistException("There is another catalog with given name: " + catalogName);
        }
    }

}
