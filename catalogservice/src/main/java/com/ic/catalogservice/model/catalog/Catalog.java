package com.ic.catalogservice.model.catalog;

import com.ic.catalogservice.model.catalog.dto.response.ProductResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a domain model for a catalog as {@link Catalog}.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Catalog  {

    private String id;
    private String catalogName;
    private String description;
    private BigDecimal catalogPrice;
    private List<Long> productIds;

}
