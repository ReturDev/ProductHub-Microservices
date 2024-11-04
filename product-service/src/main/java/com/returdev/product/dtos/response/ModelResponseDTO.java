package com.returdev.product.dtos.response;

/**
 * Data Transfer Object (DTO) for transferring essential model information.
 *
 * @param id the unique identifier of the model
 * @param name the name of the model
 * @param summary a brief description or summary of the model
 * @param brand the brand associated with this model, represented as a {@link BrandResponseDTO}
 * @param isProductionActive indicates if the model is currently in active production
 */
public record ModelResponseDTO(
        Long id,
        String name,
        String summary,
        BrandResponseDTO brand,
        boolean isProductionActive
) {}
