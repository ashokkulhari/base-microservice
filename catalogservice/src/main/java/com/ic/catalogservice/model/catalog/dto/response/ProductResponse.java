package com.ic.catalogservice.model.catalog.dto.response;


import lombok.*;

import java.math.BigDecimal;

/**
 * Represents a response object containing product details as {@link ProductResponse}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private BigDecimal amount;
    private BigDecimal unitPrice;

}
