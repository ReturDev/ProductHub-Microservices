package com.returdev.product.services.contact;

import com.returdev.product.entities.ContactInfoEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface for managing {@link ContactInfoEntity} instances.
 */
public interface ContactInfoService {

    /**
     * Retrieves a {@link ContactInfoEntity} by its ID.
     *
     * @param id the ID of the contact info to retrieve.
     * @return the {@link ContactInfoEntity} with the specified ID.
     */
    ContactInfoEntity getContactInfoById(Long id);

    /**
     * Updates an existing {@link ContactInfoEntity}.
     *
     * @param contactInfo the {@link ContactInfoEntity} with updated details.
     * @return the updated {@link ContactInfoEntity}.
     */
    @Transactional
    ContactInfoEntity updateContactInfo(@Valid ContactInfoEntity contactInfo);


    /**
     * Updates the contact information of an existing contact based on the provided details.
     * If any field is provided, it will be updated. Fields that are not provided remain unchanged.
     *
     * @param contactId the ID of the contact to update
     * @param newName the new name for the contact (must not be blank, with a length between 3 and 50 characters)
     * @param newObservations the new observations for the contact (must not be null, with a maximum length of 150 characters)
     * @param newPhoneNumber the new phone number for the contact (must match the valid phone number pattern)
     * @param newEmail the new email address for the contact (must be a valid email format)
     * @param isCommercial a flag indicating if the contact is commercial (can be null if not updating)
     * @return the updated {@link ContactInfoEntity} with the new contact information
     */
    @Transactional
    ContactInfoEntity updateContactInfo(
            @NotNull(message = "{validation.not_null.message}")
            Long contactId,

            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "{validation.size.message}")
            String newName,

            @NotNull(message = "{validation.not_null.message}")
            @Size(max = 150, message = "{validation.size.max.message}")
            String newObservations,

            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 15, message = "{validation.size.message}")
            @Pattern(regexp = "^\\+?[0-9. ()-]{8,15}$", message = "{validation.phone_format.message}")
            String newPhoneNumber,

            @Email(message = "{validation.email.message}")
            String newEmail,

            Boolean isCommercial
    );



    /**
     * Saves a new {@link ContactInfoEntity}.
     *
     * @param contactInfo the {@link ContactInfoEntity} to save.
     * @return the saved {@link ContactInfoEntity}.
     */
    ContactInfoEntity save(@Valid ContactInfoEntity contactInfo);

    /**
     * Deletes the {@link ContactInfoEntity} with the specified ID.
     *
     * @param contactId the ID of the contact info to delete.
     */
    void delete(@NotNull(message = "{validation.not_null.message}") Long contactId);
}

