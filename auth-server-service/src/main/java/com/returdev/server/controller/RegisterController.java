package com.returdev.server.controller;

import com.returdev.server.model.dto.UserRequestDto;
import com.returdev.server.service.authorization.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling user registration requests.
 * <p>
 * This controller provides an endpoint for registering a new user with email and other details.
 * </p>
 */
@RestController("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final AuthorizationService authorizationService;

    /**
     * Registers a new user with the provided user request data transfer object.
     *
     * @param userRequestDto the user request data transfer object containing user information.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUserWithEmail(@RequestBody UserRequestDto userRequestDto) {
        authorizationService.registerUser(userRequestDto);
    }

}
