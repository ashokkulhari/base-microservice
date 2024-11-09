package com.ic.catalogservice.model.catalog.mapper;

import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.entity.CatalogDocument;
import com.ic.catalogservice.model.common.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link CatalogEntityToCatalogMapper} for converting {@link CatalogDocument} to {@link Catalog}.
 */
@Mapper
public interface CatalogEntityToCatalogMapper extends BaseMapper<CatalogDocument, Catalog> {

    /**
     * Maps CatalogEntity to Catalog.
     *
     * @param source The CatalogEntity object to map.
     * @return Catalog object containing mapped data.
     */
    @Override
    Catalog map(CatalogDocument source);

    /**
     * Initializes and returns an instance of CatalogEntityToCatalogMapper.
     *
     * @return Initialized CatalogEntityToCatalogMapper instance.
     */
    static CatalogEntityToCatalogMapper initialize() {
        return Mappers.getMapper(CatalogEntityToCatalogMapper.class);
    }

}
