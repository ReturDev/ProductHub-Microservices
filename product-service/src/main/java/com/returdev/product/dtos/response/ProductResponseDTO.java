package com.returdev.product.dtos.response;

/**
 * Data Transfer Object (DTO) for transferring essential product information.
 *
 * @param id the unique identifier of the product
 * @param name the name of the product
 * @param summary a brief description or summary of the product
 * @param productCode the unique code assigned to the product
 * @param barcode the barcode associated with the product
 * @param model the model information associated with the product, represented as {@link ModelResponseDTO}
 * @param category the category information associated with the product, represented as {@link CategoryResponseDTO}
 * @param dimensions the dimensions of the product, represented as {@link DimensionsResponseDTO}
 * @param isHidden indicates if the product is hidden or visible in the system
 */
public record ProductResponseDTO(
        Long id,
        String name,
        String summary,
        String productCode,
        String barcode,
        ModelResponseDTO model,
        CategoryResponseDTO category,
        DimensionsResponseDTO dimensions,
        boolean isHidden
) {

    /**
     * Inner record for transferring product dimension details.
     *
     * @param id the unique identifier of the dimensions
     * @param weightKg the weight of the product in kilograms
     * @param lengthCm the length of the product in centimeters
     * @param widthCm the width of the product in centimeters
     * @param heightCm the height of the product in centimeters
     */
    public record DimensionsResponseDTO(
            Long id,
            float weightKg,
            float lengthCm,
            float widthCm,
            float heightCm
    ) {}
}

