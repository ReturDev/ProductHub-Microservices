package com.returdev.product.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.util.List;


/**
 * Data Transfer Object (DTO) for product requests.
 * This DTO is used to create or update product information, including name, summary, product code,
 * barcode, associated model, category, supplier IDs, dimensions, and visibility.
 * It includes validation annotations to ensure the data meets the required constraints.
 *
 * @param id The unique identifier of the product.
 * @param name The name of the product. Must be non-blank, between 3 and 50 characters.
 * @param summary A brief description of the product, non-null and up to 150 characters.
 * @param productCode A unique code for the product. Must be between 3 and 20 characters.
 * @param barcode A unique barcode for the product, between 8 and 30 characters.
 * @param modelId The identifier of the model associated with the product.
 * @param categoryId The identifier of the category associated with the product.
 * @param supplierIds A list of supplier IDs associated with the product, must not be empty.
 * @param dimensions The dimensions of the product (weight, length, width, height).
 * @param isHidden A flag indicating whether the product is hidden or not.
 */
public record ProductRequestDTO(
        Long id,
        @NotBlank(message = "${validation.not_blank.message}")
        @Size(min = 3, max = 50, message = "${validation.size.message}")
        String name,
        @NotNull(message = "${validation.not_null.message}")
        @Size(max = 150, message = "${validation.size.max.message}")
        String summary,
        @NotEmpty(message = "${validation.not_empty.message}")
        @Size(min = 3, max = 20, message = "${validation.size.message}")
        @JsonProperty("product_code")
        String productCode,
        @NotNull(message = "${validation.not_null.message}")
        @Size(min = 8, max = 30, message = "${validation.size.message}")
        String barcode,
        @JsonProperty("model_id")
        Long modelId,
        @JsonProperty("category_id")
        Long categoryId,
        @NotEmpty(message = "${validation.not_empty.message}")
        @JsonProperty("supplier_ids")
        List<Long> supplierIds,
        DimensionsRequestDTO dimensions,
        @JsonProperty("is_hidden")
        boolean isHidden
) {

    /**
     * Data Transfer Object (DTO) for the product's dimensions.
     * This includes the weight and measurements (length, width, and height) of the product.
     *
     * @param weightKg The weight of the product in kilograms.
     * @param lengthCm The length of the product in centimeters.
     * @param widthCm  The width of the product in centimeters.
     * @param heightCm The height of the product in centimeters.
     */
    public record DimensionsRequestDTO(
            @JsonProperty("weight_kg")
            float weightKg,
            @JsonProperty("length_cm")
            float lengthCm,
            @JsonProperty("width_cm")
            float widthCm,
            @JsonProperty("height_cm")
            float heightCm
    ) {}

}
