package com.returdev.product.services.supplier;

import com.returdev.product.entities.SupplierEntity;
import com.returdev.product.repositories.SupplierRepository;
import com.returdev.product.services.exception.ExceptionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    public SupplierEntity getSupplierById(
            @NotNull(message = "{validation.not_null.message}") Long id
    ) {
        return supplierRepository.findById(id).orElseThrow(() -> exceptionService.createEntityNotFoundException(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<SupplierEntity> getSupplierByNameContaining(
            @NotBlank(message = "{validation.not_blank.message}") String name,
            boolean includeInactive,
            Pageable pageable
    ) {
        return supplierRepository.getSupplierByNameContaining(name, includeInactive, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<SupplierEntity> getSupplierByNameStartingWith(
            @NotBlank(message = "{validation.not_blank.message}") String name,
            boolean includeInactive,
            Pageable pageable
    ) {
        return supplierRepository.getSupplierByNameStartingWith(name, includeInactive, pageable);
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no existing supplier is found with the provided {@code supplierId}.
     * @throws IllegalArgumentException if the provided {@code supplier} has a {@code null} ID.
     */
    @Override
    public SupplierEntity updateSupplier(@Valid SupplierEntity supplier) {
        if (supplier.getId() == null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_null.message");
        }

        Long supplierId = supplier.getId();

        if (!supplierRepository.existsById(supplierId)) {
            throw exceptionService.createEntityNotFoundException(supplierId);
        }

        return supplierRepository.save(supplier);
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no existing supplier is found with the provided {@code supplierId}.
     */
    @Override
    public SupplierEntity updateSupplierName(
            @NotNull(message = "{validation.not_null.message}") Long supplierId,
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "{validation.size.message}")
            String newName
    ) {
        return supplierRepository.updateSupplierName(supplierId, newName).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(supplierId)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no existing supplier is found with the provided {@code supplierId}.
     */
    @Override
    public SupplierEntity updateSupplierObservations(
            @NotNull(message = "{validation.not_null.message}") Long supplierId,
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(max = 150, message = "{validation.size.max.message}")
            String newObservations
    ) {
        return supplierRepository.updateSupplierObservations(supplierId, newObservations).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(supplierId)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no existing supplier is found with the provided {@code supplierId}.
     */
    @Override
    public void activateSupplier(
            @NotNull(message = "{validation.not_null.message}") Long supplierId
    ) {
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
    public void inactivateSupplier(
            @NotNull(message = "{validation.not_null.message}") Long supplierId
    ) {
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
    public SupplierEntity saveSupplier(@Valid SupplierEntity supplier) {

        if (supplier.getId() != null){
            throw exceptionService.createIllegalArgumentException("exception.id_is_not_null.message");
        }

        return supplierRepository.save(supplier);
    }
}



