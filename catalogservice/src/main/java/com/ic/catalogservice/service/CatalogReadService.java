package com.ic.catalogservice.service;


import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.dto.request.CatalogPagingRequest;
import com.ic.catalogservice.model.common.CustomPage;

/**
 * Service interface named {@link CatalogReadService} for reading catalogs.
 */
public interface CatalogReadService {

    /**
     * Retrieves a catalog by its unique ID.
     *
     * @param catalogId The ID of the catalog to retrieve.
     * @return The Catalog object corresponding to the given ID.
     */
    Catalog getCatalogById(final String catalogId);

    /**
     * Retrieves a page of catalogs based on the paging request criteria.
     *
     * @param catalogPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of catalogs that match the paging criteria.
     */
    CustomPage<Catalog> getCatalogs(final CatalogPagingRequest catalogPagingRequest);

}
