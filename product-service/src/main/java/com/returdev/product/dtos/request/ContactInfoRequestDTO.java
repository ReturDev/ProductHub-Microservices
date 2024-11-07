package com.returdev.product.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;


/**
 * Data Transfer Object (DTO) for contact information requests.
 * This DTO is used to create or update contact information for a supplier, including name,
 * observations, phone number, email, commercial status, and the associated supplier ID.
 * It includes validation annotations to ensure the data meets the required constraints.
 *
 * @param id The unique identifier of the contact information.
 * @param name The name of the contact person, which must be non-blank, between 3 and 50 characters,
 *                       and start with an uppercase letter.
 * @param observations Observations or notes about the contact, which must be non-null and not exceed 150 characters.
 * @param phoneNumber The contact's phone number, which must be in a valid format and between 8 and 15 characters.
 * @param email The contact's email address, which must be a valid email format.
 * @param isCommercial A flag indicating whether the contact is commercial or not.
 * @param supplierId The ID of the supplier associated with the contact.
 */
public record ContactInfoRequestDTO(
        Long id,
        @NotBlank(message = "{validation.not_blank.message}")
        @Size(min = 3, max = 50, message = "{validation.size.message}")
        String name,
        @NotNull(message = "{validation.not_null.message}")
        @Size(max = 150, message = "{validation.size.max.message}")
        String observations,
        @NotBlank(message = "{validation.not_blank.message}")
        @Size(min = 3, max = 15, message = "{validation.size.message}")
        @Pattern(
                regexp = "^\\+?[0-9. ()-]{8,15}$",
                message = "{validation.phone_format.message}"
        )
        @JsonProperty("phone_number")
        String phoneNumber,
        @Email(message = "{validation.email.message}")
        String email,
        @JsonProperty("is_commercial")
        boolean isCommercial,
        @NotNull(message = "{validation.not_null.message}")
        @JsonProperty("supplier_id")
        Long supplierId
) {}
