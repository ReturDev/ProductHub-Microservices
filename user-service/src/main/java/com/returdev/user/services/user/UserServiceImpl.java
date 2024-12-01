package com.returdev.user.services.user;

import com.returdev.user.entities.UserEntity;
import com.returdev.user.enums.UserRole;
import com.returdev.user.repositories.UserRepository;
import com.returdev.user.services.exception.ExceptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for managing user-related operations.
 * <p>
 * This class provides the implementation of the {@link UserService} interface,
 * handling operations such as retrieving users, saving new users, updating user
 * information, modifying roles, and deleting users. It interacts with the
 * {@link UserRepository} for database operations and uses the {@link ExceptionService}
 * for exception handling.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ExceptionService exceptionService;

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException If no user is found with the provided ID.
     */
    @Override
    public UserEntity getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> exceptionService.createEntityNotFoundExceptionById(id));
    }

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException If no user is found with the provided email.
     */
    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                exceptionService.createEntityNotFoundException(
                        "exception.entity_not_found.email.message",
                        email
                )
        );
    }

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException If the user's ID is not null or if the user credentials are invalid.
     */
    @Override
    public UserEntity saveUser(UserEntity user) {
        if (user.getId() != null) {
            throw exceptionService.createIllegalArgumentException("exception.id_is_not_null.message");
        }

        if (user.getHashPassword().isBlank() &&
                (user.getAuthProviders() == null || user.getAuthProviders().isEmpty())) {
            throw exceptionService.createIllegalArgumentException("exception.user_creation.bad_credentials.message");
        }

        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException If no user is found with the provided ID.
     */
    @Override
    public void updateUserFullName(UUID id, String newName, String newSurnames) {
        int result = userRepository.updateUserFullName(id, newName, newSurnames);

        if (result == 0) {
            throw exceptionService.createEntityNotFoundExceptionById(id);
        }
    }

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException If no user is found with the provided ID.
     */
    @Override
    public void updateUserPassword(UUID id, String hashPassword) {
        int result = userRepository.updateUserHashPassword(id, hashPassword);

        if (result == 0) {
            throw exceptionService.createEntityNotFoundExceptionById(id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void modifyUserRoles(UUID userId, Set<UserRole> userRoles) {

        Set<UserRole> currentUserRoles = getUserById(userId).getRoles();

        // Identify roles to delete
        Set<String> rolesToDelete = currentUserRoles.stream()
                .filter(currentRole -> !userRoles.contains(currentRole))
                .map(Enum::name)
                .collect(Collectors.toSet());

        if (!rolesToDelete.isEmpty()) {
            userRepository.deleteUserRoles(userId, rolesToDelete);
        }

        // Add new roles
        userRoles.stream()
                .filter(userRole -> !currentUserRoles.contains(userRole))
                .forEach(userRole -> userRepository.saveUserRole(userId, userRole.name()));
    }

    /**
     /**
     * {@inheritDoc}
     * @throws EntityNotFoundException If no user is found with the provided ID.
     */
    @Transactional
    @Override
    public void deleteUserById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw exceptionService.createEntityNotFoundExceptionById(id);
        }
        userRepository.deleteById(id);
    }
}

