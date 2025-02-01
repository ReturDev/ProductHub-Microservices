package com.returdev.server.model.dto;

/**
 * Data transfer object for user registration requests.
 * <p>
 * This record encapsulates the necessary information for registering a new user, including email, password, name, and surnames.
 * </p>
 */
public record UserRequestDto(
        String email,
        String password,
        String name,
        String surnames
) {}
