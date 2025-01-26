package com.returdev.server.config;

import com.returdev.server.service.authorization.AuthorizationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Handles successful authentication for federated identity providers.
 * <p>
 * This class implements the {@link AuthenticationSuccessHandler} interface to handle successful authentication
 * events for OAuth2 federated identity providers. It registers the authenticated user using the {@link AuthorizationService}.
 * </p>
 *
 * @see AuthenticationSuccessHandler
 */
@Component
@RequiredArgsConstructor
public class FederatedIdentityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthorizationService authorizationService;

    /**
     * Handles successful authentication by registering the authenticated user.
     *
     * @param request the {@link HttpServletRequest} object.
     * @param response the {@link HttpServletResponse} object.
     * @param authentication the {@link Authentication} object containing the authentication details.
     * @throws IOException if an input or output error occurs.
     * @throws ServletException if a servlet error occurs.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (authentication instanceof OAuth2AuthenticationToken) {
            authorizationService.registerUser(
                    ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(),
                    (OAuth2User) authentication.getPrincipal()
            );
        }

    }
}
