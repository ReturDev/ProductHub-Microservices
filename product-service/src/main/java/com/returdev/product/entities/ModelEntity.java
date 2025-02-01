package com.returdev.product.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a model entity in the system.
 * This entity is mapped to the "model" table in the database.
 *
 * <p>Each model is uniquely identified by its ID and has a name, summary, an associated brand,
 * and an indication of whether the model is currently in production.</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "models")
@Entity
public class ModelEntity {

    /**
     * The unique identifier for the model.
     * This value is automatically generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the model.
     * Cannot be blank and must have a length between 3 and 50 characters.
     */
    @NotBlank(message = "{validation.not_blank.message}")
    @Size(min = 3, max = 50, message = "{validation.size.message}")
    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    /**
     * A brief summary of the model.
     * Cannot be null and has a maximum length of 150 characters.
     */
    @NotNull(message = "{validation.not_null.message}")
    @Size(max = 150, message = "{validation.size.max.message}")
    @Column(name = "summary", nullable = false, length = 150)
    private String summary;

    /**
     * The brand associated with the model.
     * This field establishes a many-to-one relationship with the {@link BrandEntity} and cannot be null.
     */
    @NotNull(message = "{validation.not_null.message}")
    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    /**
     * Indicates if the model is currently in production.
     * This field can be used to track whether the model is actively produced or not.
     */
    @Column(name = "is_production_active")
    private boolean isProductionActive;
}

