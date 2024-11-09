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
     * @throws EntityNotFoundException if no contact is found with the provided {@code contactId}.
     */
    @Override
    public ContactInfoEntity updateContactInfoName(Long contactId, String newName) {
        return contactRepository.updateContactInfoName(contactId, newName)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no contact is found with the provided {@code contactId}.
     */
    @Override
    public ContactInfoEntity updateContactInfoObservations(Long contactId, String newObservations) {
        return contactRepository.updateContactInfoObservations(contactId, newObservations)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no contact is found with the provided {@code contactId}.
     */
    @Override
    public ContactInfoEntity updateContactInfoPhoneNumber(Long contactId, String newPhoneNumber) {
        return contactRepository.updateContactInfoPhoneNumber(contactId, newPhoneNumber)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no contact is found with the provided {@code contactId}.
     */
    @Override
    public ContactInfoEntity updateContactInfoEmail(Long contactId, String newEmail) {
        return contactRepository.updateContactInfoEmail(contactId, newEmail)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no contact is found with the provided {@code contactId}.
     */
    @Override
    public ContactInfoEntity updateContactInfoIsCommercial(Long contactId, boolean isCommercial) {
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

