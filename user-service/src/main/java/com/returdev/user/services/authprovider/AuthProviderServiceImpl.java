package com.returdev.user.services.authprovider;

import com.returdev.user.entities.AuthProviderEntity;
import com.returdev.user.repositories.AuthProviderRepository;
import com.returdev.user.services.exception.ExceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link AuthProviderService} interface.
 * <p>
 * This service handles the operations related to authentication providers, such as saving
 * and deleting authentication providers associated with users. It uses the {@link AuthProviderRepository}
 * for data persistence and the {@link ExceptionService} to handle exceptions.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AuthProviderServiceImpl implements AuthProviderService {

    /**
     * Repository for performing CRUD operations on {@link AuthProviderEntity}.
     */
    private final AuthProviderRepository authProviderRepository;

    /**
     * Service for handling exception-related tasks.
     */
    private final ExceptionService exceptionService;

    /**
     *{@inheritDoc}
     */
    @Override
    public AuthProviderEntity saveAuthProvider(AuthProviderEntity authProvider) {

        // Ensure that the authentication provider does not have an ID (i.e., it should be a new entity)
        if (authProvider.getId() != null){
            throw exceptionService.createIllegalArgumentException("exception.id_is_null.message");
        }

        // Save the new authentication provider entity
        return authProviderRepository.save(authProvider);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void deleteAuthProvider(Long id) {
        authProviderRepository.deleteById(id);
    }
}

