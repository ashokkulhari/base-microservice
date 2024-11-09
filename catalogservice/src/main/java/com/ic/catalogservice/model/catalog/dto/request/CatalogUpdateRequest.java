package com.ic.catalogservice.model.catalog.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a request object for updating an existing catalog as {@link CatalogUpdateRequest}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogUpdateRequest {

    @Size(
            min = 1,
            message = "Catalog name can't be blank."
    )
    private String catalogName;

    @DecimalMin(
            value = "0.0001",
            message = "Amount must be bigger than 0"
    )
    private BigDecimal catalogPrice;

    private String description;


    @Size(min = 1, message = "Catalog must contain at least one product.")
    private List<Long> productIds;
}
