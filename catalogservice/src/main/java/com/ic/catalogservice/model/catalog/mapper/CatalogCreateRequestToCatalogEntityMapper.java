package com.ic.catalogservice.model.catalog.mapper;

import com.ic.catalogservice.model.catalog.dto.request.CatalogCreateRequest;
import com.ic.catalogservice.model.catalog.entity.CatalogDocument;
import com.ic.catalogservice.model.common.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link CatalogCreateRequestToCatalogEntityMapper} for converting {@link CatalogCreateRequest} to {@link CatalogDocument}.
 */
@Mapper
public interface CatalogCreateRequestToCatalogEntityMapper extends BaseMapper<CatalogCreateRequest, CatalogDocument> {

    /**
     * Maps CatalogCreateRequest to CatalogEntity for saving purposes.
     *
     * @param catalogCreateRequest The CatalogCreateRequest object to map.
     * @return CatalogEntity object containing mapped data.
     */
    @Named("mapForSaving")
    default CatalogDocument mapForSaving(CatalogCreateRequest catalogCreateRequest) {
        return CatalogDocument.builder()
                .catalogPrice(catalogCreateRequest.getCatalogPrice())
                .catalogName(catalogCreateRequest.getCatalogName())
                .description(catalogCreateRequest.getDescription())
                .productIds(catalogCreateRequest.getProductIds())
                .build();
    }

    /**
     * Initializes and returns an instance of CatalogCreateRequestToCatalogEntityMapper.
     *
     * @return Initialized CatalogCreateRequestToCatalogEntityMapper instance.
     */
    static CatalogCreateRequestToCatalogEntityMapper initialize() {
        return Mappers.getMapper(CatalogCreateRequestToCatalogEntityMapper.class);
    }

}
