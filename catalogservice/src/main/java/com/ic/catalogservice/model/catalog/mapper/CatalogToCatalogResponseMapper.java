package com.ic.catalogservice.model.catalog.mapper;

import com.ic.catalogservice.client.ProductServiceClient;
import com.ic.catalogservice.model.catalog.Catalog;
import com.ic.catalogservice.model.catalog.dto.response.CatalogResponse;
import com.ic.catalogservice.model.catalog.dto.response.ProductResponse;
import com.ic.catalogservice.model.catalog.entity.CatalogDocument;
import com.ic.catalogservice.model.common.dto.response.CustomResponse;
import com.ic.catalogservice.model.common.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper interface named {@link CatalogToCatalogResponseMapper} for converting {@link Catalog} to {@link CatalogResponse}.
 */
@Mapper
public interface CatalogToCatalogResponseMapper extends BaseMapper<Catalog, CatalogResponse> {

    /**
     * Maps Catalog to CatalogResponse.
     *
     * @param source The Catalog object to map.
     * @return CatalogResponse object containing mapped data.
     */
    @Override
    CatalogResponse map(Catalog source);


    /**
     * Initializes and returns an instance of CatalogToCatalogResponseMapper.
     *
     * @return Initialized CatalogToCatalogResponseMapper instance.
     */
    static CatalogToCatalogResponseMapper initialize() {
        return Mappers.getMapper(CatalogToCatalogResponseMapper.class);
    }


}
