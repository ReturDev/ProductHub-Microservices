package com.returdev.user.controllers;

import com.returdev.user.dtos.request.AccountRequestDTO;
import com.returdev.user.dtos.response.AccountResponseDTO;
import com.returdev.user.dtos.response.AuthProviderResponseDTO;
import com.returdev.user.services.authprovider.AuthProviderService;
import com.returdev.user.services.user.UserService;
import com.returdev.user.util.mappers.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller responsible for handling user account-related operations.
 * Provides endpoints for retrieving, creating, updating, and deleting user accounts.
 */
@RestController()
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    private final AuthProviderService authProviderService;
    private final EntityDtoMapper entityDtoMapper;

    /**
     * Retrieves the user account details by ID.
     *
     * @param id The UUID of the user account to be retrieved.
     * @return {@link AccountResponseDTO} containing the user account details.
     */
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    AccountResponseDTO getAccountById(
            @PathVariable("id") UUID id
    ){
        return entityDtoMapper.userEntityToAccountResponseDto(
                userService.getUserById(id)
        );
    }

    /**
     * Retrieves the user account details by email.
     *
     * @param email The email of the user account to be retrieved.
     * @return {@link AccountResponseDTO} containing the user account details.
     */
    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    AccountResponseDTO getAccountByEmail(
            @PathVariable("email") String email
    ){
        return entityDtoMapper.userEntityToAccountResponseDto(
                userService.getUserByEmail(email)
        );
    }

    /**
     * Creates a new user account based on the provided account request data.
     *
     * @param accountRequestDTO The request body containing the user account data.
     * @return {@link AccountResponseDTO} containing the details of the newly created account.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AccountResponseDTO saveAccount(
            @RequestBody() AccountRequestDTO accountRequestDTO
    ){

        return entityDtoMapper.userEntityToAccountResponseDto(
                userService.saveUser(
                        entityDtoMapper.accountRequestDtoToUserEntity(
                                accountRequestDTO
                        )
                )
        );

    }

    /**
     * Updates the user account's full name.
     *
     * @param account The request body containing the updated user full name.
     */
    @PatchMapping("/update/name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateUserFullName(
            @RequestBody AccountRequestDTO account
    ){

        userService.updateUserFullName(
                account.id(),
                account.name(),
                account.surnames()
        );

    }

    /**
     * Updates the user account's password.
     *
     * @param account The request body containing the updated password.
     */
    @PatchMapping("/update/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updatePassword(
            @RequestBody AccountRequestDTO account
    ){
        userService.updateUserPassword(
                account.id(),
                account.hashPassword()
        );
    }

    /**
     * Synchronizes the authentication provider information for a user account.
     *
     * @param account The request body containing the account and authentication provider details.
     * @return {@link AuthProviderResponseDTO} containing the synchronized authentication provider details.
     */
    @PatchMapping("/synchronize")
    @ResponseStatus(HttpStatus.OK)
    AuthProviderResponseDTO synchronizeAuthProvider(
            @RequestBody() AccountRequestDTO account
    ) {

        return entityDtoMapper.authProviderEntityToResponseDto(
                authProviderService.saveAuthProvider(
                        entityDtoMapper.authProviderRequestDtoToEntity(
                                account.authProvider(),
                                account.id()
                        )
                )
        );

    }

    /**
     * Updates the user account's roles.
     *
     * @param account The request body containing the updated roles for the user.
     */
    @PatchMapping("/update/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateUserRoles(
            @RequestBody AccountRequestDTO account
    ){

        userService.modifyUserRoles(
                account.id(),
                account.roles()
        );

    }

    /**
     * Deletes a user account by its ID.
     *
     * @param id The UUID of the user account to be deleted.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(
            @PathVariable UUID id
    ){

        userService.deleteUserById(id);

    }
}

