package com.ic.catalogservice.service;

import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.dto.request.CatalogUpdateRequest;

/**
 * Service interface named {@link CatalogUpdateService} for updating catalogs.
 */
public interface CatalogUpdateService {

    /**
     * Updates a catalog identified by its unique ID using the provided update request.
     *
     * @param catalogId           The ID of the catalog to update.
     * @param catalogUpdateRequest The request containing updated data for the catalog.
     * @return The updated Catalog object.
     */
    Catalog updateCatalogById(final String catalogId, final CatalogUpdateRequest catalogUpdateRequest);

}
