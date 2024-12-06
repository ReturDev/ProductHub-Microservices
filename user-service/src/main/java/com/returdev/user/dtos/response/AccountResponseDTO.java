package com.returdev.user.dtos.response;

import com.returdev.user.enums.UserRole;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for representing a user's account response.
 *
 * @param id The unique identifier of the user account.
 * @param name The name of the user.
 * @param surnames The surnames of the user.
 * @param email The email address of the user.
 * @param hashPassword The hashed password for the user's account (may not be exposed in all use cases).
 * @param createdAt The timestamp representing when the user account was created.
 * @param updatedAt The timestamp representing when the user account was last updated.
 * @param roles A set of roles assigned to the user. It represents the user's permissions and access within the system.
 * @param authProviders A set of authentication providers associated with the user. Each provider is represented by an {@link AuthProviderResponseDTO}.
 */
public record AccountResponseDTO(

        UUID id,

        String name,

        String surnames,

        String email,

        String hashPassword,

        Instant createdAt,

        Instant updatedAt,

        Set<UserRole> roles,

        Set<AuthProviderResponseDTO> authProviders

) {}
