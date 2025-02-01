package com.returdev.user.services.user;

import com.returdev.user.entities.UserEntity;
import com.returdev.user.enums.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.util.Set;
import java.util.UUID;

/**
 * Service interface for managing user-related operations.
 * <p>
 * This interface defines methods for retrieving, creating, updating, and deleting user entities,
 * as well as modifying user roles and updating specific user properties like full name or password.
 * </p>
 */
@Validated
public interface UserService {

    /**
     * Retrieves a user entity by its unique ID.
     *
     * @param id The unique identifier of the user.
     *           Must not be null, validated using {@link NotNull}.
     * @return The {@link UserEntity} associated with the given ID.
     */
    UserEntity getUserById(@NotNull(message = "{validation.not_null.message}") UUID id);

    /**
     * Retrieves a user entity by its email address.
     *
     * @param email The email address of the user.
     *              Must be a valid email format, validated using {@link Email}.
     * @return The {@link UserEntity} associated with the given email address.
     */
    UserEntity getUserByEmail(@Email(message = "{validation.email.message}") String email);

    /**
     * Saves a new user entity.
     *
     * @param user The {@link UserEntity} to be saved. Validated using {@link Valid}.
     * @return The saved {@link UserEntity}.
     */
    UserEntity saveUser(@Valid UserEntity user);

    /**
     * Updates the full name (name and surnames) of a user.
     *
     * @param id The unique identifier of the user. Must not be null.
     * @param newName The new name for the user. Must be non-blank, contain at least 3 characters,
     *                  and match the specified pattern for valid names.
     * @param newSurnames The new surnames for the user. Must not be null, match the specified pattern,
     *                    and be at most 150 characters in length.
     */
    void updateUserFullName(
            @NotNull(message = "{validation.not_null.message}") UUID id,
            @NotBlank(message = "{validation.not_blank.message}")
            @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{3,}$", message = "{validation.pattern.name.message}")
            @Size(min = 3, max = 50, message = "{validation.size.message}") String newName,
            @NotNull(message = "{validation.not_null.message}")
            @Size(max = 150, message = "{validation.size.max.message}")
            @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s']*$", message = "{validation.pattern.surname.message}") String newSurnames
    );

    /**
     * Updates the password for a user.
     *
     * @param id The unique identifier of the user. Must not be null.
     * @param hashPassword The new hashed password for the user. Must not be blank.
     */
    void updateUserPassword(
            @NotNull(message = "{validation.not_null.message}") UUID id,
            @NotBlank(message = "{validation.not_blank.message}") String hashPassword
    );

    /**
     * Modifies the roles associated with a user.
     *
     * @param id The unique identifier of the user. Must not be null.
     * @param userRoles The set of roles to assign to the user. Must not be empty.
     */
    void modifyUserRoles(
            @NotNull(message = "{validation.not_null.message}") UUID id,
            @NotEmpty(message = "{validation.not_empty.message}") Set<UserRole> userRoles
    );

    /**
     * Deletes a user entity by its unique ID.
     *
     * @param id The unique identifier of the user to be deleted. Must not be null.
     */
    void deleteUserById(
            @NotNull(message = "{validation.not_null.message}") UUID id
    );
}

