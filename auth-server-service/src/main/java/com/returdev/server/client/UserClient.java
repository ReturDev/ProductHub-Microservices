package com.returdev.server.client;

import com.returdev.server.model.UserDataModel;
import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Feign client interface for interacting with the user service.
 * <p>
 * This interface defines methods for retrieving user information by email and registering a new user.
 * </p>
 */
@FeignClient(name = "${feign.client.name}", url = "${feign.client.url}")
public interface UserClient {

    /**
     * Retrieves user information by email.
     *
     * @param email the email of the user to retrieve.
     * @return the user data model containing user information.
     */
    @GetMapping("/{email}")
    UserDataModel getUserByEmail(@NonNull @PathVariable("email") String email);

    /**
     * Registers a new user.
     *
     * @param userDataModel the user data model containing user information to register.
     */
    @PostMapping("/register")
    void registerUser(@RequestBody UserDataModel userDataModel);

}
