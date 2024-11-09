package com.ic.catalogservice.service;

/**
 * Service interface named {@link CatalogDeleteService} for deleting catalogs.
 */
public interface CatalogDeleteService {

    /**
     * Deletes a catalog identified by its unique ID.
     *
     * @param catalogId The ID of the catalog to delete.
     */
    void deleteCatalogById(final String catalogId);

}
