package com.ic.productservice.model.product.entity;

import com.ic.productservice.model.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Represents a persistent entity for a product as {@link ProductEntity}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(
            name = "amount",
            precision = 24,
            scale = 4
    )
    private BigDecimal amount;

    @Column(
            name = "unit_price",
            precision = 24,
            scale = 4
    )
    private BigDecimal unitPrice;

}
