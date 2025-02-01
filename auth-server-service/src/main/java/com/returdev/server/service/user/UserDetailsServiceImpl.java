package com.returdev.server.service.user;

import com.returdev.server.model.UserDataModel;
import com.returdev.server.service.authorization.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link UserDetailsService} interface to load user-specific data.
 * <p>
 * This service retrieves user details from the {@link AuthorizationService} and converts them into a {@link UserDetails} object
 * for Spring Security authentication.
 * </p>
 *
 * @see UserDetailsService
 */
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthorizationService authorizationService;

    /**
     * Loads the user details by the given email.
     *
     * @param email the email identifying the user whose data is required.
     * @return a fully populated {@link UserDetails} object.
     * @throws UsernameNotFoundException if the user could not be found or the user has no granted authorities.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDataModel userDataModel = authorizationService.getUserByEmail(email);

        Set<SimpleGrantedAuthority> roles = userDataModel.roles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new User(
                userDataModel.email(),
                userDataModel.password(),
                true,
                true,
                true,
                !userDataModel.accountLocked(),
                roles
        );
    }
}
