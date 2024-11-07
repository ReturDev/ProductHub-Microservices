package com.returdev.product.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for category requests.
 * This DTO is used to receive category information during creation or update operations.
 * The class includes validation annotations to ensure the data conforms to the required constraints.
 *
 * @param id The unique identifier for the category (used for update operations).
 * @param name The name of the category, which must be between 3 and 50 characters, non-blank, and start with an uppercase letter.
 * @param summary A brief summary or description of the category, which must not be null and cannot exceed 150 characters.
 */
public record CategoryRequestDTO(
        Long id,
        @NotBlank(message = "${validation.not_blank.message}")
        @Size(min = 3, max = 50, message = "${validation.size.message}")
        String name,
        @NotNull(message = "${validation.not_null.message}")
        @Size(max = 150, message = "${validation.size.max.message}")
        String summary
) {}
