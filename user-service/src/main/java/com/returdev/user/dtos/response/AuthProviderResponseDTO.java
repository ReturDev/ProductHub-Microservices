package com.returdev.user.dtos.response;

import com.returdev.user.enums.AuthProvider;

/**
 * Data Transfer Object (DTO) for authentication provider responses.
 *
 * @param providerName The name of the authentication provider (e.g., GOOGLE, FACEBOOK),
 *                     represented as an {@link AuthProvider} enum.
 * @param providerId The unique identifier assigned by the authentication provider for the user.
 */
public record AuthProviderResponseDTO(
        AuthProvider providerName,
        String providerId
) {}

