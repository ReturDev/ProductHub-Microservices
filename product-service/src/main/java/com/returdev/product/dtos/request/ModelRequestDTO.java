package com.returdev.product.dtos.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for model requests.
 * This DTO is used to receive model information during creation or update operations.
 * The class includes validation annotations to ensure the data meets the required constraints.
 *
 * @param id The unique identifier for the model (used for update operations).
 * @param name The name of the model, which must be between 3 and 50 characters, non-blank, and start with an uppercase letter.
 * @param summary A brief summary or description of the model, which must not be null and cannot exceed 150 characters.
 * @param brandId The ID of the brand associated with the model (must be a valid, non-null brand ID).
 * @param isProductionActive Flag indicating whether the model is actively in production, defaults to true if not specified.
 */
public record ModelRequestDTO(
        Long id,
        @NotBlank(message = "{validation.not_blank.message}")
        @Size(min = 3, max = 50, message = "{validation.size.message}")
        String name,
        @NotNull(message = "{validation.not_null.message}")
        @Size(max = 150, message = "{validation.size.max.message}")
        String summary,
        @NotNull(message = "{validation.not_null.message}")
        Long brandId,
        @JsonProperty("is_production_active")
        Boolean isProductionActive
) {

    public ModelRequestDTO(Long id, String name, String summary, Long brandId, Boolean isProductionActive) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.brandId = brandId;
        this.isProductionActive = isProductionActive == null || isProductionActive;
    }
}
