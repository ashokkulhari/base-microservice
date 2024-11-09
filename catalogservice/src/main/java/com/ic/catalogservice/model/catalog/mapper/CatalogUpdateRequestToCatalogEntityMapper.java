package com.ic.catalogservice.model.catalog.mapper;

import com.ic.catalogservice.model.catalog.dto.request.CatalogUpdateRequest;
import com.ic.catalogservice.model.catalog.entity.CatalogDocument;
import com.ic.catalogservice.model.common.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link CatalogUpdateRequestToCatalogEntityMapper} for updating {@link CatalogDocument} using {@link CatalogUpdateRequest}.
 */
@Mapper
public interface CatalogUpdateRequestToCatalogEntityMapper extends BaseMapper<CatalogUpdateRequest, CatalogDocument> {

    /**
     * Maps fields from CatalogUpdateRequest to update CatalogEntity.
     *
     * @param catalogDocumentToBeUpdate The CatalogEntity object to be updated.
     * @param catalogUpdateRequest    The CatalogUpdateRequest object containing updated fields.
     */
    @Named("mapForUpdating")
    default void mapForUpdating(CatalogDocument catalogDocumentToBeUpdate, CatalogUpdateRequest catalogUpdateRequest) {
        catalogDocumentToBeUpdate.setCatalogName(catalogUpdateRequest.getCatalogName());
        catalogDocumentToBeUpdate.setCatalogPrice(catalogUpdateRequest.getCatalogPrice());
    }

    /**
     * Initializes and returns an instance of CatalogUpdateRequestToCatalogEntityMapper.
     *
     * @return Initialized CatalogUpdateRequestToCatalogEntityMapper instance.
     */
    static CatalogUpdateRequestToCatalogEntityMapper initialize() {
        return Mappers.getMapper(CatalogUpdateRequestToCatalogEntityMapper.class);
    }

}
