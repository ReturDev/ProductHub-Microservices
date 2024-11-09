package com.returdev.product.services.contact;

import com.returdev.product.entities.ContactInfoEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

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
    ContactInfoEntity updateContactInfo(@Valid ContactInfoEntity contactInfo);

    /**
     * Updates the name of an existing {@link ContactInfoEntity}.
     *
     * @param contactId the ID of the contact info to update.
     * @param newName the new name to set.
     * @return the updated {@link ContactInfoEntity}.
     */
    ContactInfoEntity updateContactInfoName(
            @NotNull(message = "{validation.not_null.message}")
            Long contactId,
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "{validation.size.message}")
            String newName
    );

    /**
     * Updates the observations of an existing {@link ContactInfoEntity}.
     *
     * @param contactId the ID of the contact info to update.
     * @param newObservations the new observations to set.
     * @return the updated {@link ContactInfoEntity}.
     */
    ContactInfoEntity updateContactInfoObservations(
            @NotNull(message = "{validation.not_null.message}")
            Long contactId,
            @NotNull(message = "{validation.not_null.message}")
            @Size(max = 150, message = "{validation.size.max.message}")
            String newObservations
    );

    /**
     * Updates the phone number of an existing {@link ContactInfoEntity}.
     *
     * @param contactId the ID of the contact info to update.
     * @param newPhoneNumber the new phone number to set.
     * @return the updated {@link ContactInfoEntity}.
     */
    ContactInfoEntity updateContactInfoPhoneNumber(
            @NotNull(message = "{validation.not_null.message}")
            Long contactId,
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 15, message = "{validation.size.message}")
            @Pattern(regexp = "^\\+?[0-9. ()-]{8,15}$", message = "{validation.phone_format.message}")
            String newPhoneNumber
    );

    /**
     * Updates the email address of an existing {@link ContactInfoEntity}.
     *
     * @param contactId the ID of the contact info to update.
     * @param newEmail the new email address to set.
     * @return the updated {@link ContactInfoEntity}.
     */
    ContactInfoEntity updateContactInfoEmail(
            @NotNull(message = "{validation.not_null.message}")
            Long contactId,
            @Email(message = "{validation.email.message}")
            String newEmail
    );

    /**
     * Updates the commercial status of an existing {@link ContactInfoEntity}.
     *
     * @param contactId the ID of the contact info to update.
     * @param isCommercial the new commercial status to set.
     * @return the updated {@link ContactInfoEntity}.
     */
    ContactInfoEntity updateContactInfoIsCommercial(
            @NotNull(message = "{validation.not_null.message}")
            Long contactId,
            boolean isCommercial
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

