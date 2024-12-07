package com.returdev.user.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.returdev.user.exceptions.InvalidEnumValueException;
import com.returdev.user.util.EnumUtil;
import org.apache.catalina.User;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing the different roles a user can have in the system.
 * <p>
 * This enum defines a set of user roles with varying levels of privileges.
 * </p>
 * <ul>
 *     <li>{@code GOD} - A super user with the highest level of privileges.</li>
 *     <li>{@code ADMIN} - A user with administrative privileges.</li>
 *     <li>{@code MANAGER} - A user with managerial privileges.</li>
 *     <li>{@code USER} - A standard user with basic privileges.</li>
 *     <li>{@code SUPPORT} - A user with support privileges, typically for handling customer inquiries.</li>
 * </ul>
 * These roles help determine access control and authorization across the system.
 */
public enum UserRole {
    /**
     * The GOD role represents a superuser with the highest level of privileges,
     * capable of performing all system operations.
     */
    GOD,

    /**
     * The ADMIN role represents an administrator with full access to manage users,
     * configurations, and other sensitive system resources.
     */
    ADMIN,

    /**
     * The MANAGER role represents a user with management-level access, typically
     * to oversee specific areas or teams within the system.
     */
    MANAGER,

    /**
     * The USER role represents a standard user with basic access to the system's
     * features, typically with limited permissions.
     */
    USER,

    /**
     * The SUPPORT role represents a user with privileges to manage customer inquiries,
     * assist users, and access support-related tools.
     */
    SUPPORT;

    /**
     * Converts a string value to an {@link UserRole} enum value.
     * <p>
     * This method attempts to parse the provided string value into a corresponding {@link UserRole} enum.
     * If the value does not match any valid enum value, it throws an {@link InvalidEnumValueException}.
     * </p>
     *
     * @param value The string representation of the {@link UserRole} to be converted.
     * @return The corresponding {@link UserRole} enum.
     * @throws IllegalArgumentException If the provided string does not match any valid {@link UserRole} enum value.
     * @throws InvalidEnumValueException If the provided value is not a valid enum value for {@link UserRole}.
     */
    @JsonCreator
    public static UserRole fromString(String value) throws IllegalArgumentException {
        try {
            return UserRole.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidEnumValueException(
                    value,
                    "validation.invalid_enum_value.message",
                    EnumUtil.getValidValuesToString(List.of(UserRole.values()))
            );
        }
    }


}

