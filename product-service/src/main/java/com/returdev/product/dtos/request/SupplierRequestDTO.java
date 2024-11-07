package com.returdev.product.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


/**
 * Data Transfer Object (DTO) for supplier requests.
 * This DTO is used to create or update supplier information, including name, observations,
 * and active status. It includes validation annotations to ensure the data meets the required constraints.
 *
 * @param id The unique identifier of the supplier.
 * @param name The name of the supplier, which must be non-blank, between 3 and 50 characters,
 *                     and start with an uppercase letter.
 * @param observations Observations or notes about the supplier, which must be non-null.
 * @param isActive A flag indicating whether the supplier is active or not. If not provided, defaults to true.
 */
public record SupplierRequestDTO(
    Long id,
    @NotBlank(message = "${validation.not_blank.message}")
    @Size(min = 3, max = 50, message = "${validation.size.message}")
    String name,
    @NotNull(message = "${validation.not_null.message}")
    String observations,
    @JsonProperty("is_active")
    Boolean isActive
) {


    /**
     * Constructor for SupplierRequestDTO. If {@code isActive} is null, it defaults to true.
     *
     * @param id The unique identifier of the supplier.
     * @param name The name of the supplier.
     * @param observations Observations about the supplier.
     * @param isActive A flag indicating if the supplier is active.
     */
    public SupplierRequestDTO(Long id, String name, String observations, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.observations = observations;
        this.isActive = isActive == null || isActive;
    }

}
