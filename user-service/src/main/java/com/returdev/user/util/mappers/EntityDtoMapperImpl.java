package com.returdev.user.util.mappers;

import com.returdev.user.dtos.request.AccountRequestDTO;
import com.returdev.user.dtos.request.AuthProviderRequestDTO;
import com.returdev.user.dtos.response.AccountResponseDTO;
import com.returdev.user.dtos.response.AuthProviderResponseDTO;
import com.returdev.user.entities.AuthProviderEntity;
import com.returdev.user.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Implementation of the {@link EntityDtoMapper} interface.
 */
@Component
public class EntityDtoMapperImpl implements EntityDtoMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthProviderResponseDTO authProviderEntityToResponseDto(AuthProviderEntity authProvider) {
        return new AuthProviderResponseDTO(
                authProvider.getName(),
                authProvider.getProviderId()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthProviderEntity authProviderRequestDtoToEntity(AuthProviderRequestDTO authProvider) {
        return new AuthProviderEntity(
                null,
                authProvider.providerName(),
                authProvider.providerId(),
                null
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthProviderEntity authProviderRequestDtoToEntity(AuthProviderRequestDTO authProvider, UUID userId) {

        UserEntity user = new UserEntity();
        user.setId(userId);

        return new AuthProviderEntity(
                null,
                authProvider.providerName(),
                authProvider.providerId(),
                user
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountResponseDTO userEntityToAccountResponseDto(UserEntity user) {

        Set<AuthProviderResponseDTO> authProviders = null;

        if (user.getAuthProviders() != null){
            authProviders = user.getAuthProviders().stream()
                    .map(this::authProviderEntityToResponseDto)
                    .collect(Collectors.toSet());
        }

        return new AccountResponseDTO(
                user.getId(),
                user.getName(),
                user.getSurnames(),
                user.getEmail(),
                user.getHashPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getRoles(),
                authProviders
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity accountRequestDtoToUserEntity(AccountRequestDTO account) {

        Set<AuthProviderEntity> authProviderEntities = null;

        if (account.authProvider() != null){
            authProviderEntities = Set.of(authProviderRequestDtoToEntity(account.authProvider()));
        }

        return new UserEntity(
                account.id(),
                account.name(),
                account.surnames(),
                account.email(),
                account.hashPassword(),
                null,
                null,
                account.roles(),
                authProviderEntities
        );
    }
}

