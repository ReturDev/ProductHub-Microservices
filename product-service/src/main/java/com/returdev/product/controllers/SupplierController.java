package com.returdev.product.controllers;

import com.returdev.product.dtos.request.PaginationRequestDTO;
import com.returdev.product.dtos.request.SupplierRequestDTO;
import com.returdev.product.dtos.response.SupplierResponseDTO;
import com.returdev.product.dtos.response.wrappers.ContentResponseWrapper;
import com.returdev.product.dtos.response.wrappers.PaginationResponseWrapper;
import com.returdev.product.entities.SupplierEntity;
import com.returdev.product.mappers.EntityDtoMapper;
import com.returdev.product.services.supplier.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing supplier-related operations.
 * Provides endpoints for retrieving, updating, activating, and deactivating suppliers.
 */
@RestController
@RequestMapping("/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;
    private final EntityDtoMapper entityDtoMapper;

    /**
     * Retrieves a supplier by its unique identifier.
     *
     * @param supplierId the ID of the supplier to retrieve.
     * @return a {@link ContentResponseWrapper} containing the supplier's data as a {@link SupplierResponseDTO}.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<SupplierResponseDTO> getSupplierById(
            @PathVariable("id") Long supplierId
    ) {
        return entityDtoMapper.supplierEntityToContentResponse(
                supplierService.getSupplierById(supplierId)
        );
    }

    /**
     * Retrieves a paginated list of suppliers, optionally including inactive suppliers.
     *
     * @param includeInactives whether to include inactive suppliers in the response.
     * @param paginationRequestDTO the pagination information.
     * @return a {@link PaginationResponseWrapper} with a paginated list of suppliers as {@link SupplierResponseDTO} objects.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<SupplierResponseDTO> getSuppliers(
            @RequestParam(value = "showInactives", defaultValue = "false") boolean includeInactives,
            @Valid PaginationRequestDTO paginationRequestDTO
    ) {
        return entityDtoMapper.supplierEntityPageToPaginationResponse(
                supplierService.getAllSuppliers(
                        includeInactives,
                        entityDtoMapper.paginationRequestToPageable(paginationRequestDTO)
                )
        );
    }

    /**
     * Searches for suppliers by name, supporting partial matches.
     *
     * @param name the name or partial name to search for.
     * @param includeInactives whether to include inactive suppliers in the results.
     * @param pagination the pagination information.
     * @return a paginated list of matching suppliers wrapped in a {@link PaginationResponseWrapper}.
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseWrapper<SupplierResponseDTO> getSuppliersByName(
            @RequestParam() String name,
            @RequestParam(value = "showInactives", defaultValue = "false") boolean includeInactives,
            @Valid PaginationRequestDTO pagination
    ) {
        Pageable pageable = entityDtoMapper.paginationRequestToPageable(pagination);
        Page<SupplierEntity> supplierPage;

        if (name.startsWith("*")) {
            supplierPage = supplierService.getSupplierByNameContaining(name.replace("*", ""), includeInactives, pageable);
        } else {
            supplierPage = supplierService.getSupplierByNameStartingWith(name, includeInactives, pageable);
        }

        return entityDtoMapper.supplierEntityPageToPaginationResponse(supplierPage);
    }

    /**
     * Updates an existing supplier with new information provided in a request DTO.
     *
     * @param supplierRequestDTO the supplier data to update.
     * @return the updated supplier's data wrapped in a {@link ContentResponseWrapper}.
     */
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<SupplierResponseDTO> updateSupplier(
            @RequestBody @Valid SupplierRequestDTO supplierRequestDTO
    ) {
        return entityDtoMapper.supplierEntityToContentResponse(
                supplierService.updateSupplier(
                        entityDtoMapper.supplierRequestToEntity(supplierRequestDTO)
                )
        );
    }

    /**
     * Updates specific fields of an existing supplier.
     *
     * @param supplierRequestDTO the partial supplier data to update.
     * @return the updated supplier's data wrapped in a {@link ContentResponseWrapper}.
     */
    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<SupplierResponseDTO> updateSupplierProperty(
            @RequestBody SupplierRequestDTO supplierRequestDTO
    ) {
        return entityDtoMapper.supplierEntityToContentResponse(
                supplierService.updateSupplier(
                        supplierRequestDTO.id(),
                        supplierRequestDTO.name(),
                        supplierRequestDTO.observations()
                )
        );
    }

    /**
     * Activates a supplier by its unique identifier.
     *
     * @param supplierId the ID of the supplier to activate.
     */
    @PatchMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateSupplier(
            @PathVariable("id") Long supplierId
    ) {
        supplierService.activateSupplier(supplierId);
    }

    /**
     * Deactivates a supplier by its unique identifier.
     *
     * @param supplierId the ID of the supplier to deactivate.
     */
    @PatchMapping("/{id}/inactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivateSupplier(
            @PathVariable("id") Long supplierId
    ) {
        supplierService.inactivateSupplier(supplierId);
    }

    /**
     * Creates and saves a new supplier using the provided request data.
     *
     * @param supplierRequestDTO the new supplier data.
     * @return the created supplier's data wrapped in a {@link ContentResponseWrapper}.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ContentResponseWrapper<SupplierResponseDTO> saveSupplier(
            @RequestBody @Valid SupplierRequestDTO supplierRequestDTO
    ) {
        return entityDtoMapper.supplierEntityToContentResponse(
                supplierService.saveSupplier(
                        entityDtoMapper.supplierRequestToEntity(supplierRequestDTO)
                )
        );
    }
}

