package com.returdev.product.dtos.response;

/**
 * Data Transfer Object (DTO) for transferring essential category information.
 *
 * @param id the unique identifier of the category
 * @param name the name of the category
 * @param summary a brief description or summary of the category
 */
public record CategoryResponseDTO(
        Long id,
        String name,
        String summary
) {}

