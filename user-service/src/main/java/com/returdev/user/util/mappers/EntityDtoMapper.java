package com.returdev.user.util.mappers;

import com.returdev.user.dtos.request.AccountRequestDTO;
import com.returdev.user.dtos.request.AuthProviderRequestDTO;
import com.returdev.user.dtos.response.AccountResponseDTO;
import com.returdev.user.dtos.response.AuthProviderResponseDTO;
import com.returdev.user.entities.AuthProviderEntity;
import com.returdev.user.entities.UserEntity;

/**
 * Interface for mapping between entity objects and Data Transfer Objects (DTOs).
 * <p>
 * This interface defines methods for converting between entities and their corresponding DTOs to ensure separation
 * of concerns and facilitate data transfer between different layers of the application.
 * </p>
 */
public interface EntityDtoMapper {

    /**
     * Maps an {@link AuthProviderEntity} to an {@link AuthProviderResponseDTO}.
     *
     * @param authProvider The {@link AuthProviderEntity} to be mapped.
     * @return An {@link AuthProviderResponseDTO} containing the mapped data.
     */
    AuthProviderResponseDTO authProviderEntityToResponseDto(AuthProviderEntity authProvider);

    /**
     * Maps an {@link AuthProviderRequestDTO} to an {@link AuthProviderEntity}.
     *
     * @param authProvider The {@link AuthProviderRequestDTO} to be mapped.
     * @return An {@link AuthProviderEntity} containing the mapped data.
     */
    AuthProviderEntity authProviderRequestDtoToEntity(AuthProviderRequestDTO authProvider);

    /**
     * Maps a {@link UserEntity} to an {@link AccountResponseDTO}.
     *
     * @param user The {@link UserEntity} to be mapped.
     * @return An {@link AccountResponseDTO} containing the mapped data.
     */
    AccountResponseDTO userEntityToAccountResponseDto(UserEntity user);

    /**
     * Maps an {@link AccountRequestDTO} to a {@link UserEntity}.
     *
     * @param account The {@link AccountRequestDTO} to be mapped.
     * @return A {@link UserEntity} containing the mapped data.
     */
    UserEntity accountRequestDtoToUserEntity(AccountRequestDTO account);

}

