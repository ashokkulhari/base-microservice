package com.ic.catalogservice.model.catalog.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a response object containing catalog details as {@link CatalogResponse}.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogResponse {

    private String id;
    private String catalogName;
    private String description;
    private BigDecimal catalogPrice;
    private List<ProductResponse> products;
}
