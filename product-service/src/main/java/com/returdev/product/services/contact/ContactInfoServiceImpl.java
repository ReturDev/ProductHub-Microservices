package com.returdev.product.services.contact;

import com.returdev.product.entities.ContactInfoEntity;
import com.returdev.product.repositories.ContactInfoRepository;
import com.returdev.product.services.exception.ExceptionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    public ContactInfoEntity updateContactInfo(@Valid ContactInfoEntity contactInfo) {
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
    public ContactInfoEntity updateContactInfoName(
            @NotNull(message = "{validation.not_null.message}") Long contactId,
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 50, message = "{validation.size.message}") String newName
    ) {
        return contactRepository.updateContactInfoName(contactId, newName)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no contact is found with the provided {@code contactId}.
     */
    @Override
    public ContactInfoEntity updateContactInfoObservations(
            @NotNull(message = "{validation.not_null.message}") Long contactId,
            @NotNull(message = "{validation.not_null.message}")
            @Size(max = 150, message = "{validation.size.max.message}") String newObservations
    ) {
        return contactRepository.updateContactInfoObservations(contactId, newObservations)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no contact is found with the provided {@code contactId}.
     */
    @Override
    public ContactInfoEntity updateContactInfoPhoneNumber(
            @NotNull(message = "{validation.not_null.message}") Long contactId,
            @NotBlank(message = "{validation.not_blank.message}")
            @Size(min = 3, max = 15, message = "{validation.size.message}")
            @Pattern(regexp = "^\\+?[0-9. ()-]{8,15}$", message = "{validation.phone_format.message}") String newPhoneNumber
    ) {
        return contactRepository.updateContactInfoPhoneNumber(contactId, newPhoneNumber)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no contact is found with the provided {@code contactId}.
     */
    @Override
    public ContactInfoEntity updateContactInfoEmail(
            @NotNull(message = "{validation.not_null.message}") Long contactId,
            @Email(message = "{validation.email.message}") String newEmail
    ) {
        return contactRepository.updateContactInfoEmail(contactId, newEmail)
                .orElseThrow(() -> exceptionService.createEntityNotFoundException(contactId));
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException if no contact is found with the provided {@code contactId}.
     */
    @Override
    public ContactInfoEntity updateContactInfoIsCommercial(
            @NotNull(message = "{validation.not_null.message}") Long contactId,
            boolean isCommercial
    ) {
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
        if (contactId == null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_null.message");
        }
        contactRepository.deleteById(contactId);
    }
}

