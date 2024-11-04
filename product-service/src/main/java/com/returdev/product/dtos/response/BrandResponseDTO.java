package com.returdev.product.dtos.response;

/**
 * Data Transfer Object (DTO) for transferring essential brand information.
 *
 * @param id the unique identifier of the brand
 * @param name the name of the brand
 * @param summary a brief description of the brand
 * @param isActive the active status of the brand
 */
public record BrandResponseDTO(
        Long id,
        String name,
        String summary,
        boolean isActive
) {}
