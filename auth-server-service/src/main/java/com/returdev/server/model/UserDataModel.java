package com.returdev.server.model;

import java.util.Set;

/**
 * Data model representing a user's information and associated authentication provider details.
 * <p>
 * This record encapsulates the user's personal details, roles, and the associated authentication provider data.
 * </p>
 *
 * @param email The user's email address, used as the primary identifier for the account.
 * @param password The user's password, stored in a hashed format for security.
 * @param name The user's first name.
 * @param surnames The user's surnames or last names.
 * @param roles A set of roles assigned to the user, representing permissions or access levels.
 * @param providerDataModel The authentication provider associated with the user, represented by a {@link ProviderDataModel}.
 */
public record UserDataModel(
        String email,
        String password,
        String name,
        String surnames,
        Set<String> roles,
        boolean accountLocked,
        ProviderDataModel providerDataModel
) {}
