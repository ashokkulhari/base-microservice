package com.ic.catalogservice.model.catalog.dto.request;

import com.ic.catalogservice.model.common.dto.request.CustomPagingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a paging request object for retrieving catalogs as {@link CatalogPagingRequest}.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CatalogPagingRequest extends CustomPagingRequest {


}
