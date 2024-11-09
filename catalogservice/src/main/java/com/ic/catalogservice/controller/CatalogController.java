package com.ic.catalogservice.controller;


import com.ic.catalogservice.client.ProductServiceClient;
import com.ic.catalogservice.model.common.CustomPage;
import com.ic.catalogservice.model.common.dto.response.CustomPagingResponse;
import com.ic.catalogservice.model.common.dto.response.CustomResponse;
import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.dto.request.CatalogCreateRequest;
import com.ic.catalogservice.model.catalog.dto.request.CatalogPagingRequest;
import com.ic.catalogservice.model.catalog.dto.request.CatalogUpdateRequest;
import com.ic.catalogservice.model.catalog.dto.response.CatalogResponse;
import com.ic.catalogservice.model.catalog.mapper.CustomPageToCustomPagingResponseMapper;
import com.ic.catalogservice.model.catalog.mapper.CatalogToCatalogResponseMapper;
import com.ic.catalogservice.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller named {@link CatalogController} for managing catalogs.
 * Provides endpoints to create, read, update, and delete catalogs.
 */
@RestController
@RequestMapping("/api/v1/catalogs")
@RequiredArgsConstructor
@Validated
public class CatalogController {

    private final CatalogCreateService catalogCreateService;
    private final CatalogReadService catalogReadService;
    private final CatalogUpdateService catalogUpdateService;
    private final CatalogDeleteService catalogDeleteService;

    private final CatalogMappingService catalogMappingService;


    private final CustomPageToCustomPagingResponseMapper customPageToCustomPagingResponseMapper =
            CustomPageToCustomPagingResponseMapper.initialize();

    /**
     * Creates a new catalog.
     *
     * @param catalogCreateRequest the request payload containing catalog details
     * @return a {@link CustomResponse} containing the ID of the created catalog
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<String> createCatalog(@RequestBody @Valid final CatalogCreateRequest catalogCreateRequest) {

        final Catalog createdCatalog = catalogCreateService
                .createCatalog(catalogCreateRequest);

        return CustomResponse.successOf(createdCatalog.getId());
    }

    /**
     * Retrieves a catalog by its ID.
     *
     * @param catalogId the ID of the catalog to retrieve
     * @return a {@link CustomResponse} containing the catalog details
     */
    @GetMapping("/{catalogId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<CatalogResponse> getCatalogById(@PathVariable  final String catalogId) {

        final Catalog catalog = catalogReadService.getCatalogById(catalogId);

        final CatalogResponse catalogResponse = catalogMappingService.mapToCatalog(catalog);

        return CustomResponse.successOf(catalogResponse);

    }

    /**
     * Retrieves a paginated list of catalogs based on the paging request.
     *
     * @param catalogPagingRequest the request payload containing paging information
     * @return a {@link CustomResponse} containing the paginated list of catalogs
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomResponse<CustomPagingResponse<CatalogResponse>> getCatalogs(
            @RequestBody @Valid final CatalogPagingRequest catalogPagingRequest) {

        final CustomPage<Catalog> catalogPage = catalogReadService.getCatalogs(catalogPagingRequest);

        final CustomPagingResponse<CatalogResponse> catalogPagingResponse =
                customPageToCustomPagingResponseMapper.toPagingResponse(catalogPage);

        return CustomResponse.successOf(catalogPagingResponse);

    }

    /**
     * Updates an existing catalog by its ID.
     *
     * @param catalogUpdateRequest the request payload containing updated catalog details
     * @param catalogId the ID of the catalog to update
     * @return a {@link CustomResponse} containing the updated catalog details
     */
    @PutMapping("/{catalogId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<CatalogResponse> updatedCatalogById(
            @RequestBody @Valid final CatalogUpdateRequest catalogUpdateRequest,
            @PathVariable @UUID final String catalogId) {

        final Catalog updatedCatalog = catalogUpdateService.updateCatalogById(catalogId, catalogUpdateRequest);

        final CatalogResponse catalogResponse = catalogMappingService.mapToCatalog(updatedCatalog);

        return CustomResponse.successOf(catalogResponse);
    }

    /**
     * Deletes a catalog by its ID.
     *
     * @param catalogId the ID of the catalog to delete
     * @return a {@link CustomResponse} indicating successful deletion
     */
    @DeleteMapping("/{catalogId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<Void> deleteCatalogById(@PathVariable @UUID final String catalogId) {

        catalogDeleteService.deleteCatalogById(catalogId);
        return CustomResponse.SUCCESS;
    }

}
