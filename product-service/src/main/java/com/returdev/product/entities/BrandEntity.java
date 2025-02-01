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
 * Represents a brand entity in the system.
 * This entity is mapped to the "brands" table in the database.
 *
 * <p>Each brand is uniquely identified by an ID and has a unique name, a summary,
 * a list of associated models, and a status indicating if the brand is active.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "brands")
@Entity
public class BrandEntity {

    /**
     * The unique identifier for the brand.
     * This value is automatically generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the brand.
     * Cannot be blank, must be unique, and has a length between 3 and 50 characters.
     */
    @NotBlank(message = "{validation.not_blank.message}")
    @Size(min = 3, max = 50, message = "{validation.size.message}")
    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    /**
     * A brief summary of the brand.
     * Cannot be null and has a maximum length of 150 characters.
     */
    @NotNull(message = "{validation.not_null.message}")
    @Size(max = 150, message = "{validation.size.max.message}")
    @Column(name = "summary", nullable = false, length = 150)
    private String summary;

    /**
     * Indicates if the brand is active.
     * Cannot be null; defaults to {@code false}.
     * This field can be used to activate or deactivate the brand within the system.
     */
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}

