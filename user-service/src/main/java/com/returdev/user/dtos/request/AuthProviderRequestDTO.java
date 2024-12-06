package com.returdev.user.dtos.request;

import com.returdev.user.enums.AuthProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for receiving information about an authentication provider from a request.
 *
 * @param providerName The name of the authentication provider (e.g., "Google", "Facebook"). It cannot be null.
 * @param providerId The unique identifier of the authentication provider for the user. It cannot be blank.
 */
public record AuthProviderRequestDTO(
        @NotNull(message = "{validation.not_null.message}")
        AuthProvider providerName,
        @NotBlank(message = "{validation.not_blank.message}")
        String providerId
) {}
