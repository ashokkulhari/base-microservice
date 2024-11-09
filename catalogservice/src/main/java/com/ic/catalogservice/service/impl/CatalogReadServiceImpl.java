package com.ic.catalogservice.service.impl;

import com.ic.catalogservice.client.ProductServiceClient;
import com.ic.catalogservice.exception.CatalogNotFoundException;
import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.dto.request.CatalogPagingRequest;
import com.ic.catalogservice.model.catalog.entity.CatalogDocument;
import com.ic.catalogservice.model.catalog.mapper.CatalogEntityToCatalogMapper;
import com.ic.catalogservice.model.catalog.mapper.ListCatalogEntityToListCatalogMapper;
import com.ic.catalogservice.model.common.CustomPage;
import com.ic.catalogservice.repository.CatalogRepository;
import com.ic.catalogservice.service.CatalogReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation named {@link CatalogReadServiceImpl} for reading catalogs.
 */
@Service
@RequiredArgsConstructor
public class CatalogReadServiceImpl implements CatalogReadService {

    private final CatalogRepository catalogRepository;
    private final ProductServiceClient productServiceClient;

    private final CatalogEntityToCatalogMapper catalogEntityToCatalogMapper = CatalogEntityToCatalogMapper.initialize();

    private final ListCatalogEntityToListCatalogMapper listCatalogEntityToListCatalogMapper =
            ListCatalogEntityToListCatalogMapper.initialize();

    /**
     * Retrieves a catalog by its unique ID.
     *
     * @param catalogId The ID of the catalog to retrieve.
     * @return The Catalog object corresponding to the given ID.
     * @throws CatalogNotFoundException If no catalog with the given ID exists.
     */
    @Override
    public Catalog getCatalogById(String catalogId) {
        System.out.println("--getCatalogById-"+catalogId);

        final CatalogDocument catalogDocumentFromDB = catalogRepository
                .findById(catalogId)
                .orElseThrow(() -> new CatalogNotFoundException("With given catalogID = " + catalogId));

        return catalogEntityToCatalogMapper.map(catalogDocumentFromDB);
    }

    /**
     * Retrieves a page of catalogs based on the paging request criteria.
     *
     * @param catalogPagingRequest The paging request criteria.
     * @return A CustomPage containing the list of catalogs that match the paging criteria.
     * @throws CatalogNotFoundException If no catalogs are found based on the paging criteria.
     */
    @Override
    public CustomPage<Catalog> getCatalogs(CatalogPagingRequest catalogPagingRequest) {

        final Page<CatalogDocument> catalogEntityPage = catalogRepository.findAll(catalogPagingRequest.toPageable());

        if (catalogEntityPage.getContent().isEmpty()) {
            throw new CatalogNotFoundException("Couldn't find any Catalog");
        }

        final List<Catalog> catalogDomainModels = listCatalogEntityToListCatalogMapper
                .toCatalogList(catalogEntityPage.getContent());

        return CustomPage.of(catalogDomainModels, catalogEntityPage);

    }

}
