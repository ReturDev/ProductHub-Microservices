package com.returdev.server.service.authorization;

import com.returdev.server.model.UserDataModel;
import com.returdev.server.model.dto.UserRequestDto;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * Service interface for authorization-related operations.
 * <p>
 * This interface defines methods for retrieving user information by email and registering users
 * with different types of data.
 * </p>
 */
public interface AuthorizationService {

    /**
     * Retrieves user information by email.
     *
     * @param email the email of the user to retrieve.
     * @return the user data model containing user information.
     */
    UserDataModel getUserByEmail(String email);

    /**
     * Registers a new user with the given provider name and OAuth2 user details.
     *
     * @param providerName the name of the authentication provider.
     * @param oAuth2User the OAuth2 user details.
     */
    void registerUser(String providerName, OAuth2User oAuth2User);

    /**
     * Registers a new user with the given user request data transfer object.
     *
     * @param userRequestDto the user request data transfer object containing user information.
     */
    void registerUser(UserRequestDto userRequestDto);

}
