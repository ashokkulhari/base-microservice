package com.ic.catalogservice.model.catalog.mapper;

import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.entity.CatalogDocument;
import com.ic.catalogservice.model.common.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface named {@link CatalogToCatalogEntityMapper} for converting {@link Catalog} to {@link CatalogDocument}.
 */
@Mapper
public interface CatalogToCatalogEntityMapper extends BaseMapper<Catalog, CatalogDocument> {

    /**
     * Maps Catalog to CatalogEntity.
     *
     * @param source The Catalog object to map.
     * @return CatalogEntity object containing mapped data.
     */
    @Override
    CatalogDocument map(Catalog source);

    /**
     * Initializes and returns an instance of CatalogToCatalogEntityMapper.
     *
     * @return Initialized CatalogToCatalogEntityMapper instance.
     */
    static CatalogToCatalogEntityMapper initialize() {
        return Mappers.getMapper(CatalogToCatalogEntityMapper.class);
    }

}
