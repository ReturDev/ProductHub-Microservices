package com.returdev.product.services.supplier;

import com.returdev.product.entities.SupplierEntity;
import com.returdev.product.repositories.SupplierRepository;
import com.returdev.product.services.exception.ExceptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


/**
 * Implementation of the {@link SupplierService} interface for managing suppliers in the system.
 */
@Validated
@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ExceptionService exceptionService;

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no supplier is found with the provided {@code id}.
     */
    @Override
    public SupplierEntity getSupplierById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> exceptionService.createEntityNotFoundException(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<SupplierEntity> getAllSuppliers(boolean includeInactive, Pageable pageable) {
        return includeInactive ? supplierRepository.findAll(pageable) : supplierRepository.findAllActiveSuppliers(pageable);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Page<SupplierEntity> getSupplierByNameContaining(String name, boolean includeInactive, Pageable pageable) {
        return supplierRepository.findSupplierByNameContaining(name, includeInactive, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<SupplierEntity> getSupplierByNameStartingWith(String name, boolean includeInactive, Pageable pageable) {
        return supplierRepository.findSupplierByNameStartingWith(name, includeInactive, pageable);
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no existing supplier is found with the provided {@code supplierId}.
     * @throws IllegalArgumentException if the provided {@code supplier} has a {@code null} ID.
     */
    @Override
    public SupplierEntity updateSupplier(SupplierEntity supplier) {
        if (supplier.getId() == null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_null.message");
        }

        Long supplierId = supplier.getId();

        if (!supplierRepository.existsById(supplierId)) {
            throw exceptionService.createEntityNotFoundException(supplierId);
        }

        return supplierRepository.save(supplier);
    }

    @Override
    public SupplierEntity updateSupplier(Long supplierId, String newName, String newObservations) {

        if (!supplierRepository.existsById(supplierId)) {
            throw exceptionService.createEntityNotFoundException(supplierId);
        }

        SupplierEntity supplierResponse = null;

        if (newName != null) {
            supplierResponse = updateSupplierName(supplierId, newName);
        }

        if (newObservations != null) {
            supplierResponse = updateSupplierObservations(supplierId, newObservations);
        }

        if (supplierResponse == null){
            throw exceptionService.createIllegalArgumentException("exception.null_update_values.message");
        }

        return supplierResponse;

    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no existing supplier is found with the provided {@code supplierId}.
     */
    @Override
    public void activateSupplier(Long supplierId) {
        int result = supplierRepository.activateSupplier(supplierId);

        if (result == 0) {
            throw exceptionService.createEntityNotFoundException(supplierId);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no existing supplier is found with the provided {@code supplierId}.
     */
    @Override
    public void inactivateSupplier(Long supplierId) {
        int result = supplierRepository.deactivateSupplier(supplierId);

        if (result == 0) {
            throw exceptionService.createEntityNotFoundException(supplierId);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the {@code supplier} has a non-null ID, as it should be null for a new supplier.
     */
    @Override
    public SupplierEntity saveSupplier(SupplierEntity supplier) {

        if (supplier.getId() != null){
            throw exceptionService.createIllegalArgumentException("exception.id_is_not_null.message");
        }

        return supplierRepository.save(supplier);
    }

    /**
     * Updates the name of a supplier with the specified ID.
     *
     * @param supplierId the ID of the supplier to update.
     * @param newName the new name to set for the supplier.
     * @return the updated {@link SupplierEntity} with the new name.
     * @throws EntityNotFoundException if no supplier is found with the specified ID.
     */
    private SupplierEntity updateSupplierName(Long supplierId, String newName) {
        return supplierRepository.updateSupplierName(supplierId, newName).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(supplierId)
        );
    }

    /**
     * Updates the observations field of a supplier with the specified ID.
     *
     * @param supplierId the ID of the supplier to update.
     * @param newObservations the new observations to set for the supplier.
     * @return the updated {@link SupplierEntity} with the new observations.
     * @throws EntityNotFoundException if no supplier is found with the specified ID.
     */
    private SupplierEntity updateSupplierObservations(Long supplierId, String newObservations) {
        return supplierRepository.updateSupplierObservations(supplierId, newObservations).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(supplierId)
        );
    }

}



