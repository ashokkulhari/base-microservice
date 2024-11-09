package com.ic.catalogservice.model.catalog.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "catalog") // Specify the collection name if different
public class CatalogDocument {
    @Id
    private String id;

    @Field("product_ids")
    private List<Long> productIds;

    @Field("catalog_name")
    private String catalogName;

    @Field("description")
    private String description;

    @Field("catalog_price")
    private BigDecimal catalogPrice;
}