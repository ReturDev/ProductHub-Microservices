package com.returdev.user.dtos.request;

import com.returdev.user.enums.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for receiving information about a user's account from a request.
 * <p>
 *
 * @param id The unique identifier of the user account. This field is optional and can be used for updating an existing account.
 * @param name The name of the user. It must be non-blank, have a minimum length of 3 characters, and follow a pattern that allows letters and spaces.
 * @param surnames The surnames of the user. It must be non-null, have a maximum length of 150 characters, and follow a pattern allowing letters, spaces, and apostrophes.
 * @param email The email address of the user. It must be a valid email format.
 * @param hashPassword The hashed password for the user's account. It must not be empty or just whitespace.
 * @param roles A set of roles associated with the user. It must not be empty.
 * @param authProvider The authentication provider details associated with the user, encapsulated in an {@link AuthProviderRequestDTO}.
 *                     This field is validated and must not be null.
 */
public record AccountRequestDTO(
        UUID id,

        @NotBlank(message = "{validation.not_blank.message}")
        @Size(min = 3, max = 50, message = "{validation.size.message}")
        @Pattern(
                regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{3,}$",
                message = "{validation.pattern.name.message}"
        )
        String name,

        @NotNull(message = "{validation.not_null.message}")
        @Size(max = 150, message = "{validation.size.max.message}")
        @Pattern(
                regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s']*$",
                message = "{validation.pattern.surname.message}"
        )
        String surnames,

        @Email(message = "{validation.email.message}")
        String email,

        @Pattern(regexp = "^(?!\\s*$).+", message = "{validation.not_empty_string.message")
        String hashPassword,

        @NotEmpty(message = "{validation.not_empty.message}")
        Set<UserRole> roles,

        @Valid AuthProviderRequestDTO authProvider

) {}
