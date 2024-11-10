package com.returdev.product.controllers;

import com.returdev.product.dtos.request.ContactInfoRequestDTO;
import com.returdev.product.dtos.response.SupplierResponseDTO;
import com.returdev.product.dtos.response.wrappers.ContentResponseWrapper;
import com.returdev.product.mappers.EntityDtoMapper;
import com.returdev.product.services.contact.ContactInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing contact information-related endpoints.
 * Handles CRUD operations for contact information and maps contact info entities to their respective DTO representations.
 */
@RestController
@RequestMapping("/v1/contact-info")
@RequiredArgsConstructor
public class ContactInfoController {

    private final ContactInfoService contactService;
    private final EntityDtoMapper entityDtoMapper;

    /**
     * Retrieves contact information by its ID.
     *
     * @param contactId the ID of the contact info to retrieve
     * @return a {@link ContentResponseWrapper} containing the {@link SupplierResponseDTO.ContactInfoResponseDTO} of the retrieved contact info
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<SupplierResponseDTO.ContactInfoResponseDTO> getContactInfoById(@PathVariable("id") Long contactId) {
        return entityDtoMapper.contactInfoEntityToContentResponse(
                contactService.getContactInfoById(contactId)
        );
    }

    /**
     * Updates an existing contact information entry with the provided data.
     *
     * @param contactInfoRequestDTO the DTO containing the updated contact info data
     * @return a {@link ContentResponseWrapper} containing the updated {@link SupplierResponseDTO.ContactInfoResponseDTO}
     */
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<SupplierResponseDTO.ContactInfoResponseDTO> updateContactInfo(
            @RequestBody @Valid ContactInfoRequestDTO contactInfoRequestDTO
    ) {
        return entityDtoMapper.contactInfoEntityToContentResponse(
                entityDtoMapper.contactInfoRequestToEntity(
                        contactInfoRequestDTO
                )
        );
    }

    /**
     * Partially updates specific properties of an existing contact information entry.
     *
     * @param contactInfoRequestDTO the DTO containing the properties to update
     * @return a {@link ContentResponseWrapper} containing the updated {@link SupplierResponseDTO.ContactInfoResponseDTO}
     */
    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    public ContentResponseWrapper<SupplierResponseDTO.ContactInfoResponseDTO> updateContactInfoProperty(
            @RequestBody ContactInfoRequestDTO contactInfoRequestDTO
    ) {
        return entityDtoMapper.contactInfoEntityToContentResponse(
                contactService.updateContactInfo(
                        contactInfoRequestDTO.id(),
                        contactInfoRequestDTO.name(),
                        contactInfoRequestDTO.observations(),
                        contactInfoRequestDTO.phoneNumber(),
                        contactInfoRequestDTO.email(),
                        contactInfoRequestDTO.isCommercial()
                )
        );
    }

    /**
     * Creates a new contact information entry with the provided data.
     *
     * @param contactInfoRequestDTO the DTO containing the data for the new contact info
     * @return a {@link ContentResponseWrapper} containing the created {@link SupplierResponseDTO.ContactInfoResponseDTO}
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ContentResponseWrapper<SupplierResponseDTO.ContactInfoResponseDTO> saveContactInfo(
            @RequestBody @Valid ContactInfoRequestDTO contactInfoRequestDTO
    ) {
        return entityDtoMapper.contactInfoEntityToContentResponse(
                contactService.save(
                        entityDtoMapper.contactInfoRequestToEntity(contactInfoRequestDTO)
                )
        );
    }

    /**
     * Deletes a contact information entry by its ID.
     *
     * @param contactId the ID of the contact info to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContactInfo(@PathVariable("id") Long contactId) {
        contactService.delete(contactId);
    }

}

