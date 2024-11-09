package com.ic.catalogservice.model.catalog.mapper;

import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.entity.CatalogDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link ListCatalogEntityToListCatalogMapper} for converting {@link List< CatalogDocument >} to {@link List<Catalog>}.
 */
@Mapper
public interface ListCatalogEntityToListCatalogMapper {

    CatalogEntityToCatalogMapper catalogEntityToCatalogMapper = Mappers.getMapper(CatalogEntityToCatalogMapper.class);

    /**
     * Converts a list of CatalogEntity objects to a list of Catalog objects.
     *
     * @param catalogEntities The list of CatalogEntity objects to convert.
     * @return List of Catalog objects containing mapped data.
     */
    default List<Catalog> toCatalogList(List<CatalogDocument> catalogEntities) {

        if (catalogEntities == null) {
            return null;
        }

        return catalogEntities.stream()
                .map(catalogEntityToCatalogMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of ListCatalogEntityToListCatalogMapper.
     *
     * @return Initialized ListCatalogEntityToListCatalogMapper instance.
     */
    static ListCatalogEntityToListCatalogMapper initialize() {
        return Mappers.getMapper(ListCatalogEntityToListCatalogMapper.class);
    }

}
