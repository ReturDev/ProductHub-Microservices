package com.returdev.product.repositories;

import com.returdev.product.entities.ContactInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing ContactInfoEntity instances.
 * Provides methods for retrieving, updating, and managing category data in the database.
 */
@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfoEntity, Long> {

    /**
     * Updates the name of the contact information entity.
     *
     * @param contactId the ID of the contact info to update.
     * @param newName the new name to set.
     * @return an Optional containing the updated {@link ContactInfoEntity}, or empty if not found.
     */
    @Query(value = "CALL updateContactInfoName(:id, :name)", nativeQuery = true)
    Optional<ContactInfoEntity> updateContactInfoName(@Param("id") Long contactId, @Param("name") String newName);

    /**
     * Updates the observations of the contact information entity.
     *
     * @param contactId the ID of the contact info to update.
     * @param newObservations the new observations to set.
     * @return an Optional containing the updated {@link ContactInfoEntity}, or empty if not found.
     */
    @Query(value = "CALL updateContactInfoObservations(:id, :observations)", nativeQuery = true)
    Optional<ContactInfoEntity> updateContactInfoObservations(@Param("id") Long contactId, @Param("observations") String newObservations);

    /**
     * Updates the phone number of the contact information entity.
     *
     * @param contactId the ID of the contact info to update.
     * @param newPhoneNumber the new phone number to set.
     * @return an Optional containing the updated {@link ContactInfoEntity}, or empty if not found.
     */
    @Query(value = "CALL updateContactInfoPhoneNumber(:id, :phoneNumber)", nativeQuery = true)
    Optional<ContactInfoEntity> updateContactInfoPhoneNumber(@Param("id") Long contactId, @Param("phoneNumber") String newPhoneNumber);

    /**
     * Updates the email address of the contact information entity.
     *
     * @param contactId the ID of the contact info to update.
     * @param newEmail the new email address to set.
     * @return an Optional containing the updated {@link ContactInfoEntity}, or empty if not found.
     */
    @Query(value = "CALL updateContactInfoEmail(:id, :email)", nativeQuery = true)
    Optional<ContactInfoEntity> updateContactInfoEmail(@Param("id") Long contactId, @Param("email") String newEmail);

    /**
     * Updates the commercial status of the contact information entity.
     *
     * @param contactId the ID of the contact info to update.
     * @param isCommercial the new commercial status to set.
     * @return an Optional containing the updated {@link ContactInfoEntity}, or empty if not found.
     */
    @Query(value = "CALL updateContactInfoIsCommercial(:id, :isCommercial)", nativeQuery = true)
    Optional<ContactInfoEntity> updateContactInfoIsCommercial(@Param("id") Long contactId, @Param("isCommercial") boolean isCommercial);
}



