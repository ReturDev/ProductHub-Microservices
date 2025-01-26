package com.returdev.server.service.authorization;


import com.returdev.server.client.UserClient;
import com.returdev.server.model.ProviderDataModel;
import com.returdev.server.model.UserDataModel;
import com.returdev.server.model.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Implementation of the {@link AuthorizationService} interface for authorization-related operations.
 * <p>
 * This class provides methods to retrieve user information by email and register users with different types of data.
 * </p>
 *
 * @see AuthorizationService
 */
@Component
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Retrieves user information by email.
     *
     * @param email the email of the user to retrieve.
     * @return the user data model containing user information.
     */
    @Override
    public UserDataModel getUserByEmail(String email) {
        return userClient.getUserByEmail(email);
    }

    /**
     * Registers a new user with the given provider name and OAuth2 user details.
     *
     * @param providerName the name of the authentication provider.
     * @param oAuth2User the OAuth2 user details.
     * @throws IllegalArgumentException if the provider is unsupported.
     */
    @Override
    public void registerUser(String providerName, OAuth2User oAuth2User) {

        UserDataModel userData = null;

        if (providerName.equals("google")) {
            userData = new UserDataModel(
                    oAuth2User.getAttribute("email"),
                    null,
                    oAuth2User.getAttribute("name"),
                    oAuth2User.getAttribute("family_name"),
                    getRegistrationRoles(),
                    false,
                    new ProviderDataModel(
                            providerName,
                            oAuth2User.getAttribute("sub")
                    )
            );
        }

        if (userData == null) {
            throw new IllegalArgumentException("Unsupported provider");
        }

        userClient.registerUser(userData);
    }

    /**
     * Registers a new user with the given user request data transfer object.
     *
     * @param userRequestDto the user request data transfer object containing user information.
     */
    @Override
    public void registerUser(UserRequestDto userRequestDto) {

        userClient.registerUser(
                new UserDataModel(
                        userRequestDto.email(),
                        passwordEncoder.encode(userRequestDto.password()),
                        userRequestDto.name(),
                        userRequestDto.surnames(),
                        getRegistrationRoles(),
                        false,
                        null
                )
        );
    }

    /**
     * Retrieves the default roles for registration.
     *
     * @return a set of default roles.
     */
    private Set<String> getRegistrationRoles() {
        return Set.of("ROLE_DEFAULT");
    }
}
