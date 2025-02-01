package com.returdev.product.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Data Transfer Object for Supplier response.
 * Represents the detailed information of a supplier, including contacts.
 *
 * @param id Unique identifier of the supplier.
 * @param name Name of the supplier.
 * @param observations Observations or additional notes about the supplier.
 * @param contactInfos List of contact information associated with the supplier.
 * @param isActive Indicates if the supplier is active.
 */
public record SupplierResponseDTO(
        Long id,
        String name,
        String observations,
        @JsonProperty("concat_infos")
        List<ContactInfoResponseDTO> contactInfos,
        @JsonProperty("is_active")
        boolean isActive
) {

    /**
     * Data Transfer Object for Contact Info response.
     * Represents the details of a single contact associated with a supplier.
     *
     * @param id Unique identifier of the contact.
     * @param name Name of the contact person.
     * @param observations Observations or additional notes about the contact.
     * @param phoneNumber Phone number of the contact.
     * @param email Email address of the contact.
     * @param isCommercial Indicates if the contact is for commercial purposes.
     */
    public record ContactInfoResponseDTO(
            Long id,
            String name,
            String observations,
            @JsonProperty("phone_number")
            String phoneNumber,
            String email,
            @JsonProperty("is_commercial")
            boolean isCommercial
    ) {}

}

