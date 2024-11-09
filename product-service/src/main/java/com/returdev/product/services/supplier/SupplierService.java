package com.returdev.product.services.supplier;


import com.returdev.product.entities.SupplierEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing suppliers in the system.
 */
public interface SupplierService {


    /**
     * Retrieves a supplier by its ID.
     *
     * @param id the unique identifier of the supplier
     * @return the {@link SupplierEntity} associated with the provided ID
     */
    SupplierEntity getSupplierById(@NotNull(message = "{validation.not_null.message}") Long id);

    /**
     * Finds suppliers with names containing the specified text.
     *
     * @param name the text to search within supplier names.
     * @param includeInactive if {@code true}, includes inactive suppliers in the results.
     * @param pageable the pagination information.
     * @return a paginated list of suppliers containing the specified name.
     */
    Page<SupplierEntity> getSupplierByNameContaining(
            @NotBlank(message = "{validation.not_blank.message}")
            String name,
            boolean includeInactive,
            Pageable pageable
    );

    /**
     * Finds suppliers with names starting with the specified text.
     *
     * @param name the text with which supplier names should start.
     * @param includeInactive if {@code true}, includes inactive suppliers in the results.
     * @param pageable the pagination information.
     * @return a paginated list of suppliers starting with the specified name.
     */
    Page<SupplierEntity> getSupplierByNameStartingWith(
            @NotBlank(message = "{validation.not_blank.message}")
            String name,
            boolean includeInactive,
            Pageable pageable
    );

    /**
     * Updates an existing supplier's details.
     *
     * @param supplier the supplier entity with updated information.
     * @return the updated supplier entity.
     */
    SupplierEntity updateSupplier(@Valid SupplierEntity supplier);

    /**
     * Updates the name of a supplier.
     *
     * @param supplierId the ID of the supplier to update.
     * @param newName the new name to set for the supplier.
     * @return the {@link SupplierEntity} updated.
     */
    SupplierEntity updateSupplierName(
            @NotNull(message = "{validation.not_null.message}")
            Long supplierId,
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "{validation.size.message}")
            String newName
    );

    /**
     * Updates the observations/notes for a supplier.
     *
     * @param supplierId the ID of the supplier to update.
     * @param newObservations the new observations to set for the supplier.
     * @return the {@link SupplierEntity} updated.
     */
    SupplierEntity updateSupplierObservations(
            @NotNull(message = "{validation.not_null.message}")
            Long supplierId,
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(max = 150, message = "{validation.size.max.message}")
            String newObservations
    );

    /**
     * Activates a supplier, marking it as active.
     *
     * @param supplierId the ID of the supplier to activate.
     */
    void activateSupplier(@NotNull(message = "{validation.not_null.message}") Long supplierId);

    /**
     * Inactivates a supplier, marking it as inactive.
     *
     * @param supplierId the ID of the supplier to inactivate.
     */
    void inactivateSupplier(@NotNull(message = "{validation.not_null.message}") Long supplierId);

    /**
     * Saves a new supplier to the system.
     *
     * @param supplier the supplier entity to save.
     */
    SupplierEntity saveSupplier(@Valid SupplierEntity supplier);

}

