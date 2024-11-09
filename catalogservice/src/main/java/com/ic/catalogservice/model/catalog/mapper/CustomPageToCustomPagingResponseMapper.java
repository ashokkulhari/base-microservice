package com.ic.catalogservice.model.catalog.mapper;

import com.ic.catalogservice.model.common.CustomPage;
import com.ic.catalogservice.model.common.dto.response.CustomPagingResponse;
import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.dto.response.CatalogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link CustomPageToCustomPagingResponseMapper} for converting {@link CustomPage<Catalog>} to {@link CustomPagingResponse<CatalogResponse>}.
 */
@Mapper
public interface CustomPageToCustomPagingResponseMapper {

    CatalogToCatalogResponseMapper catalogToCatalogResponseMapper = Mappers.getMapper(CatalogToCatalogResponseMapper.class);

    /**
     * Converts a CustomPage<Catalog> object to CustomPagingResponse<CatalogResponse>.
     *
     * @param catalogPage The CustomPage<Catalog> object to convert.
     * @return CustomPagingResponse<CatalogResponse> object containing mapped data.
     */
    default CustomPagingResponse<CatalogResponse> toPagingResponse(CustomPage<Catalog> catalogPage) {

        if (catalogPage == null) {
            return null;
        }

        return CustomPagingResponse.<CatalogResponse>builder()
                .content(toCatalogResponseList(catalogPage.getContent()))
                .totalElementCount(catalogPage.getTotalElementCount())
                .totalPageCount(catalogPage.getTotalPageCount())
                .pageNumber(catalogPage.getPageNumber())
                .pageSize(catalogPage.getPageSize())
                .build();

    }

    /**
     * Converts a list of Catalog objects to a list of CatalogResponse objects.
     *
     * @param catalogs The list of Catalog objects to convert.
     * @return List of CatalogResponse objects containing mapped data.
     */
    default List<CatalogResponse> toCatalogResponseList(List<Catalog> catalogs) {

        if (catalogs == null) {
            return null;
        }

        return catalogs.stream()
                .map(catalogToCatalogResponseMapper::map)
                .collect(Collectors.toList());

    }

    /**
     * Initializes and returns an instance of CustomPageToCustomPagingResponseMapper.
     *
     * @return Initialized CustomPageToCustomPagingResponseMapper instance.
     */
    static CustomPageToCustomPagingResponseMapper initialize() {
        return Mappers.getMapper(CustomPageToCustomPagingResponseMapper.class);
    }

}
