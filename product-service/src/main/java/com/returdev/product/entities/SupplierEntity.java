package com.returdev.product.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents a supplier entity in the system.
 * Suppliers have a unique name, observations, and associated contact information.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "supplier")
public class SupplierEntity {

    /**
     * Unique identifier for each supplier, generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the supplier, which must be unique and cannot be blank.
     * This field has a minimum length of 3 and a maximum length of 50 characters.
     */
    @NotBlank(message = "{validation.not_blank.message}")
    @Size(min = 3, max = 50, message = "{validation.size.message}")
    @Column(name = "name", unique = true, length = 50, nullable = false)
    private String name;

    /**
     * Observations or notes about the supplier.
     * This field cannot be null and uses a large object (LOB) type in the database for extended text.
     */
    @NotNull(message = "{validation.not_null.message}")
    @Lob
    @Column(name = "observations", nullable = false)
    private String observations;

    /**
     * List of contact information entries associated with the supplier.
     * Uses eager fetching to load contacts immediately with the supplier.
     * The orphanRemoval attribute removes contact entries when they are removed from this list.
     */
    @OneToMany(mappedBy = "supplier", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ContactInfoEntity> contacts;

    /**
     * Indicates if the supplier is active. Default value is true.
     */
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

}