package com.returdev.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.returdev.user.exceptions.InvalidEnumValueException;
import com.returdev.user.util.EnumUtil;

import java.util.List;


/**
 * Enum representing various authentication providers supported by the system.
 */
public enum AuthProvider {

    /**
     * Represents authentication through Google.
     * This value is used when the authentication is done via Google accounts.
     */
    GOOGLE,

    /**
     * Represents authentication through GitHub.
     * This value is used when the authentication is done via GitHub accounts.
     */
    GITHUB;

    /**
     * Converts a string value to an {@link AuthProvider} enum.
     *
     * @param value The string representation of an {@link AuthProvider} enum.
     * @return The corresponding {@link AuthProvider} enum.
     * @throws IllegalArgumentException If the provided string does not match any valid enum value,
     *                                  an {@link InvalidEnumValueException} will be thrown.
     */
    @JsonCreator
    public static AuthProvider fromString(String value) throws IllegalArgumentException {
        try {
            return AuthProvider.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidEnumValueException(
                    value,
                    "validation.invalid_enum_value.message",
                    EnumUtil.getValidValuesToString(List.of(AuthProvider.values()))
            );
        }
    }

}


