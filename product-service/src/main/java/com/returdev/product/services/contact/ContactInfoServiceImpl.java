package com.returdev.product.services.contact;

import com.returdev.product.entities.ContactInfoEntity;
import com.returdev.product.repositories.ContactInfoRepository;
import com.returdev.product.services.exception.ExceptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Implementation of the {@link ContactInfoService} interface for managing contact information.
 */
@Service
@Validated
@RequiredArgsConstructor
public class ContactInfoServiceImpl implements ContactInfoService {

    private final ContactInfoRepository contactRepository;
    private final ExceptionService exceptionService;

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no contact is found with the provided {@code id}.
     */
    @Override
    public ContactInfoEntity getContactInfoById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(id));
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the provided {@code contactInfo} has a null ID.
     * @throws EntityNotFoundException if the contact to update does not exist.
     */
    @Transactional
    @Override
    public ContactInfoEntity updateContactInfo(ContactInfoEntity contactInfo) {
        if (contactInfo.getId() == null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_null.message");
        }

        Long contactId = contactInfo.getId();
        if (!contactRepository.existsById(contactId)) {
            throw exceptionService.createEntityNotFoundException(contactId);
        }

        return contactRepository.save(contactInfo);
    }


    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if no fields are provided for update
     */
    @Override
    public ContactInfoEntity updateContactInfo(
            Long contactId,
            String newName,
            String newObservations,
            String newPhoneNumber,
            String newEmail,
            Boolean isCommercial
    ) {

        ContactInfoEntity contactInfoResult = null;

        if (newName != null){
            contactInfoResult = updateContactInfoName(contactId, newName);
        }

        if (newObservations != null) {
            contactInfoResult = updateContactInfoObservations(contactId, newObservations);
        }

        if (newPhoneNumber != null) {
            contactInfoResult = updateContactInfoPhoneNumber(contactId, newPhoneNumber);
        }

        if (newEmail != null) {
            contactInfoResult = updateContactInfoEmail(contactId, newEmail);
        }

        if (isCommercial != null) {
            contactInfoResult = updateContactInfoIsCommercial(contactId, isCommercial);
        }

        if (contactInfoResult == null) {
            throw exceptionService.createIllegalArgumentException("exception.null_update_values.message");
        }

        return contactInfoResult;
    }

    /**
     * Updates the name of the contact information for the given contact ID.
     *
     * @param contactId the ID of the contact to update
     * @param newName the new name to set for the contact
     * @return the updated {@link ContactInfoEntity} with the new name
     * @throws EntityNotFoundException if no contact with the specified ID exists
     */
    private ContactInfoEntity updateContactInfoName(Long contactId, String newName) {
        return contactRepository.updateContactInfoName(contactId, newName)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * Updates the observations of the contact information for the given contact ID.
     *
     * @param contactId the ID of the contact to update
     * @param newObservations the new observations to set for the contact
     * @return the updated {@link ContactInfoEntity} with the new observations
     * @throws EntityNotFoundException if no contact with the specified ID exists
     */
    private ContactInfoEntity updateContactInfoObservations(Long contactId, String newObservations) {
        return contactRepository.updateContactInfoObservations(contactId, newObservations)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * Updates the phone number of the contact information for the given contact ID.
     *
     * @param contactId the ID of the contact to update
     * @param newPhoneNumber the new phone number to set for the contact
     * @return the updated {@link ContactInfoEntity} with the new phone number
     * @throws EntityNotFoundException if no contact with the specified ID exists
     */
    private ContactInfoEntity updateContactInfoPhoneNumber(Long contactId, String newPhoneNumber) {
        return contactRepository.updateContactInfoPhoneNumber(contactId, newPhoneNumber)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * Updates the email of the contact information for the given contact ID.
     *
     * @param contactId the ID of the contact to update
     * @param newEmail the new email address to set for the contact
     * @return the updated {@link ContactInfoEntity} with the new email
     * @throws EntityNotFoundException if no contact with the specified ID exists
     */
    private ContactInfoEntity updateContactInfoEmail(Long contactId, String newEmail) {
        return contactRepository.updateContactInfoEmail(contactId, newEmail)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * Updates the commercial status of the contact information for the given contact ID.
     *
     * @param contactId the ID of the contact to update
     * @param isCommercial the new commercial status to set for the contact
     * @return the updated {@link ContactInfoEntity} with the new commercial status
     * @throws EntityNotFoundException if no contact with the specified ID exists
     */
    private ContactInfoEntity updateContactInfoIsCommercial(Long contactId, boolean isCommercial) {
        return contactRepository.updateContactInfoIsCommercial(contactId, isCommercial)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the {@code contactInfo} has a non-null ID, as it should be null for a new contact.
     */
    @Override
    public ContactInfoEntity save(ContactInfoEntity contactInfo) {
        if (contactInfo.getId() != null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_not_null.message");
        }
        return contactRepository.save(contactInfo);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if the {@code contactId} is null.
     */
    @Override
    public void delete(Long contactId) {
        try {
            contactRepository.deleteById(contactId);
        } catch (IllegalArgumentException ex){
            throw exceptionService.createEntityNotFoundException(contactId);
        }
    }
}

