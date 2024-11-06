package com.returdev.product.repositories;

import com.returdev.product.entities.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository interface for managing {@link SupplierEntity} entities.
 * Provides methods for retrieving, updating, and modifying suppliers in the database.
 */
@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    /**
     * Retrieves a paginated list of suppliers whose names contain the specified substring.
     * Filters suppliers based on their active status.
     *
     * @param name the substring to search for in supplier names.
     * @param includeInactive if true, includes inactive suppliers in the results; otherwise, only active suppliers are returned.
     * @param pageable pagination information.
     * @return a page of matching suppliers.
     */
    @Query("SELECT s FROM SupplierEntity s WHERE (:includeInactive = true OR s.isActive = true) AND s.name LIKE CONCAT('%', :name, '%')")
    Page<SupplierEntity> getSupplierByNameContaining(@Param("name") String name, @Param("includeInactive") boolean includeInactive, Pageable pageable);

    /**
     * Retrieves a paginated list of suppliers whose names start with the specified prefix.
     * Filters suppliers based on their active status.
     *
     * @param name the prefix to search for at the beginning of supplier names.
     * @param includeInactive if true, includes inactive suppliers in the results; otherwise, only active suppliers are returned.
     * @param pageable pagination information.
     * @return a page of matching suppliers.
     */
    @Query("SELECT s FROM SupplierEntity s WHERE (:includeInactive = true OR s.isActive = true) AND s.name LIKE CONCAT(:name, '%')")
    Page<SupplierEntity> getSupplierByNameStartingWith(@Param("name") String name, @Param("includeInactive") boolean includeInactive, Pageable pageable);

    /**
     * Updates the name of a supplier using a stored procedure.
     *
     * @param supplierId the ID of the supplier to update.
     * @param newName the new name to set for the supplier.
     * @return an Optional containing the updated SupplierEntity if found and updated successfully.
     */
    @Modifying
    @Transactional
    @Query(value = "CALL update_supplier_name(:id, :name)", nativeQuery = true)
    Optional<SupplierEntity> updateSupplierName(@Param("id") Long supplierId, @Param("name") String newName);

    /**
     * Updates the observations of a supplier using a stored procedure.
     *
     * @param supplierId the ID of the supplier to update.
     * @param newObservations the new observations to set for the supplier.
     * @return an Optional containing the updated SupplierEntity if found and updated successfully.
     */
    @Modifying
    @Transactional
    @Query(value = "CALL update_supplier_observations(:id, :observations)", nativeQuery = true)
    Optional<SupplierEntity> updateSupplierObservations(@Param("id") Long supplierId, @Param("observations") String newObservations);

    /**
     * Activates a supplier by setting its {@code isActive} status to true.
     *
     * @param supplierId the ID of the supplier to activate.
     * @return the number of rows affected.
     */
    @Modifying
    @Transactional
    @Query("UPDATE SupplierEntity s SET s.isActive = true WHERE s.id = :supplierId")
    int activateSupplier(@Param("supplierId") Long supplierId);

    /**
     * Deactivates a supplier by setting its {@code isActive} status to false.
     *
     * @param supplierId the ID of the supplier to deactivate.
     * @return the number of rows affected.
     */
    @Modifying
    @Transactional
    @Query("UPDATE SupplierEntity s SET s.isActive = false WHERE s.id = :supplierId")
    int deactivateSupplier(@Param("supplierId") Long supplierId);

}

