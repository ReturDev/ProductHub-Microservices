package com.returdev.server.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

/**
 * Configuration class for setting up security configurations for the application.
 * This class includes configurations for the OAuth2 authorization server and default security settings.
 */
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final FederatedIdentityAuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * Configures the security filter chain for the OAuth2 authorization server.
     * <p>
     * This filter chain is responsible for ensuring that all requests to the authorization server endpoints
     * are authenticated. It also sets up custom exception handling to redirect unauthenticated users to the login page
     * when a request fails with an unauthorized error (specifically for JSON requests).
     * </p>
     *
     * @param http The {@link HttpSecurity} object used to configure the security settings.
     * @return The {@link SecurityFilterChain} configured for the authorization server.
     * @throws Exception If an error occurs during the configuration of the security filter chain.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, Customizer.withDefaults())
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .anyRequest().authenticated()
                )
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.APPLICATION_JSON)
                        )
                );

        return http.build();
    }


/**
 * Configures the default security filter chain with form-based login and OAuth2 login.
 *
 * @param http the {@link HttpSecurity} object used to configure the security filter chain.
 * @return the configured {@link SecurityFilterChain}.
 * @throws Exception if an error occurs during configuration.
 */
@Bean
@Order(2)
public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    return http
            .formLogin(Customizer.withDefaults())
            .oauth2Login( oauth -> oauth.successHandler(authenticationSuccessHandler))
            .build();
}

    /**
     * Configures the authentication provider with a user details service and password encoder.
     *
     * @param userDetailService the {@link UserDetailsService} used to load user-specific data.
     * @return the configured {@link DaoAuthenticationProvider}.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authProvider;
    }

    /**
     * Configures an in-memory registered client repository for the OAuth2 authorization server.
     *
     * @param properties the {@link OAuth2AuthorizationServerProperties} containing client registration properties.
     * @return the configured {@link InMemoryRegisteredClientRepository}.
     */
    @Bean
    public InMemoryRegisteredClientRepository registeredClientRepository(OAuth2AuthorizationServerProperties properties) {

        String clientName = "product-hub-client";
        OAuth2AuthorizationServerProperties.Registration registration = properties.getClient().get(clientName).getRegistration();

        RegisteredClient registeredClient = RegisteredClient.withId(clientName)
                .clientId(registration.getClientId())
                .clientSecret(registration.getClientSecret())
                .clientAuthenticationMethods(methods ->
                        registration.getClientAuthenticationMethods().stream()
                                .map(ClientAuthenticationMethod::new)
                                .forEach(methods::add)
                )
                .authorizationGrantTypes(grants ->
                        registration.getAuthorizationGrantTypes().stream()
                                .map(AuthorizationGrantType::new)
                                .forEach(grants::add)
                )
                .scopes(scopes -> scopes.addAll(registration.getScopes()))
                .redirectUris(uris -> uris.addAll(registration.getRedirectUris()))
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(15))
                        .refreshTokenTimeToLive(Duration.ofHours(12))
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    /**
     * Creates a {@link JWKSource} with an RSA key pair for token signing.
     *
     * @return the configured {@link JWKSource}.
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString()) // Unique key ID.
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * Generates an RSA key pair for use with JWT signing.
     *
     * @return the generated {@link KeyPair}.
     * @throws IllegalStateException if key pair generation fails.
     */
    private static KeyPair generateRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // Key size: 2048 bits.
            return keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Creates a {@link JwtDecoder} for decoding JWT tokens using the provided {@link JWKSource}.
     *
     * @param jwkSource the {@link JWKSource} containing the key material for decoding tokens.
     * @return the configured {@link JwtDecoder}.
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * Configures and provides {@link AuthorizationServerSettings} for the authorization server.
     *
     * @return the default {@link AuthorizationServerSettings}.
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }
}
