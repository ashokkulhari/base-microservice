package com.ic.catalogservice.model.catalog.dto.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogCreateRequest {

    @NotBlank(message = "Catalog name can't be blank.")
    private String catalogName;

    private String description;

    @DecimalMin(value = "0.0001", message = "Catalog price must be greater than 0")
    private BigDecimal catalogPrice;

    @Size(min = 1, message = "Catalog must contain at least one product.")
    private List<Long> productIds;
}
