package com.ic.catalogservice.service;


import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.dto.request.CatalogCreateRequest;

/**
 * Service interface named {@link CatalogCreateService} for creating catalogs.
 */
public interface CatalogCreateService {

    /**
     * Creates a new catalog based on the provided catalog creation request.
     *
     * @param catalogCreateRequest The request containing data to create the catalog.
     * @return The created Catalog object.
     */
    Catalog createCatalog(final CatalogCreateRequest catalogCreateRequest);

}