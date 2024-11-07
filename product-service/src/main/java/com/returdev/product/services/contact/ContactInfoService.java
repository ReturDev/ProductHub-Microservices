package com.returdev.product.services.contact;

import com.returdev.product.entities.ContactInfoEntity;

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
    ContactInfoEntity updateContactInfo(ContactInfoEntity contactInfo);

    /**
     * Updates the name of an existing {@link ContactInfoEntity}.
     *
     * @param contactId the ID of the contact info to update.
     * @param newName the new name to set.
     * @return the updated {@link ContactInfoEntity}.
     */
    ContactInfoEntity updateContactInfoName(Long contactId, String newName);

    /**
     * Updates the observations of an existing {@link ContactInfoEntity}.
     *
     * @param contactId the ID of the contact info to update.
     * @param newObservations the new observations to set.
     * @return the updated {@link ContactInfoEntity}.
     */
    ContactInfoEntity updateContactInfoObservations(Long contactId, String newObservations);

    /**
     * Updates the phone number of an existing {@link ContactInfoEntity}.
     *
     * @param contactId the ID of the contact info to update.
     * @param newPhoneNumber the new phone number to set.
     * @return the updated {@link ContactInfoEntity}.
     */
    ContactInfoEntity updateContactInfoPhoneNumber(Long contactId, String newPhoneNumber);

    /**
     * Updates the email address of an existing {@link ContactInfoEntity}.
     *
     * @param contactId the ID of the contact info to update.
     * @param newEmail the new email address to set.
     * @return the updated {@link ContactInfoEntity}.
     */
    ContactInfoEntity updateContactInfoEmail(Long contactId, String newEmail);

    /**
     * Updates the commercial status of an existing {@link ContactInfoEntity}.
     *
     * @param contactId the ID of the contact info to update.
     * @param isCommercial the new commercial status to set.
     * @return the updated {@link ContactInfoEntity}.
     */
    ContactInfoEntity updateContactInfoIsCommercial(Long contactId, boolean isCommercial);

    /**
     * Saves a new {@link ContactInfoEntity}.
     *
     * @param contactInfo the {@link ContactInfoEntity} to save.
     * @return the saved {@link ContactInfoEntity}.
     */
    ContactInfoEntity save(ContactInfoEntity contactInfo);

    /**
     * Deletes the {@link ContactInfoEntity} with the specified ID.
     *
     * @param contactId the ID of the contact info to delete.
     */
    void delete(Long contactId);
}

