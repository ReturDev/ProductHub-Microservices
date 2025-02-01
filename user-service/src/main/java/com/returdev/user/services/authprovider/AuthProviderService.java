package com.returdev.user.services.authprovider;

import com.returdev.user.entities.AuthProviderEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;


/**
 * Service interface for managing authentication providers associated with users.
 * <p>
 * This interface defines methods for saving and deleting authentication providers.
 * Authentication providers represent external services or systems used for user authentication
 * (e.g., Google, Facebook, etc.).
 * </p>
 */
@Validated
public interface AuthProviderService {

    /**
     * Saves a new authentication provider.
     * <p>
     * This method is used to persist a new {@link AuthProviderEntity} in the database.
     * </p>
     *
     * @param authProvider the {@link AuthProviderEntity} to save, which must be valid
     *                     according to the constraints specified in the entity.
     * @return the saved {@link AuthProviderEntity}.
     */
    AuthProviderEntity saveAuthProvider(@Valid AuthProviderEntity authProvider);

    /**
     * Deletes an existing authentication provider by its ID.
     * <p>
     * This method removes the {@link AuthProviderEntity} with the given ID from the database.
     * The provided ID must not be {@code null}.
     * </p>
     *
     * @param id the ID of the authentication provider to delete, must not be {@code null}.
     */
    void deleteAuthProvider(
            @NotNull(message = "{validation.not_null.message}") Long id
    );

}


