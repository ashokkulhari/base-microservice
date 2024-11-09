package com.ic.productservice.model.product;

import com.ic.productservice.model.common.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Represents a domain model for a product as {@link Product}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseDomainModel {

    private Long id;
    private String name;
    private BigDecimal amount;
    private BigDecimal unitPrice;

}
