package com.ic.userservice.model.user.entity;

import com.ic.userservice.model.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents an entity named {@link InvalidTokenEntity} for storing invalid tokens in the system.
 * This entity tracks tokens that have been invalidated to prevent their reuse.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "invalid_token")
public class InvalidTokenEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_id")
    private String tokenId;

}
