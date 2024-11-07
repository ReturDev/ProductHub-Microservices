package com.returdev.product.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


/**
 * Data Transfer Object for Brand request.
 * This DTO is used to receive brand information when creating or updating a brand.
 * The class includes validation annotations to ensure that the data meets the required constraints.
 *
 * @param id Unique identifier of the brand (for update operations).
 * @param name  of the brand, must be non-blank, start with an uppercase letter, and be between 3 and 50 characters.
 * @param summary A brief summary or description of the brand, must not be null and should be up to 150 characters.
 * @param isActive Indicates whether the brand is active. If null, defaults to true.
 */
public record BrandRequestDTO(
        Long id,
        @NotBlank(message = "${validation.not_blank.message}")
        @Size(min = 3, max = 50, message = "${validation.size.message}")
        @Pattern(regexp = "^[A-Z].*", message = "${validation.name_format.message}")
        String name,
        @NotNull(message = "${validation.not_null.message}")
        @Size(max = 150, message = "${validation.size.max.message}")
        String summary,
        @JsonProperty("is_active")
        Boolean isActive
) {

        public BrandRequestDTO(Long id, String name, String summary, Boolean isActive) {
                this.id = id;
                this.name = name;
                this.summary = summary;
                this.isActive = isActive == null || isActive;
        }
}
