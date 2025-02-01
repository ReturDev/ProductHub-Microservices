package com.returdev.product.services.exception;


import com.returdev.product.managers.MessageManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for creating and managing custom exceptions with localized messages.
 */
@Service
@RequiredArgsConstructor
public class ExceptionService {

    private final MessageManager messageManager;

    /**
     * Creates an {@link EntityNotFoundException} with a localized message for the given {@code entityId}.
     *
     * @param entityId The ID of the entity that was not found.
     * @return A new {@link EntityNotFoundException} instance with the appropriate message.
     */
    public EntityNotFoundException createEntityNotFoundException(Long entityId) {
        return new EntityNotFoundException(
                messageManager.getMessage(
                        "exception.entity_not_found.message",
                        new Long[]{entityId}
                )
        );
    }

    public EntityNotFoundException createEntityNotFoundException() {
        return new EntityNotFoundException(
                messageManager.getMessage(
                        "exception.some_entity_not_found.message"
                )
        );
    }

    /**
     * Creates an {@link IllegalArgumentException} with a localized message based on the provided message key.
     *
     * @param messageKey The key for the message to be used in the exception.
     * @return A new {@link IllegalArgumentException} instance with the appropriate message.
     */
    public IllegalArgumentException createIllegalArgumentException(String messageKey) {
        return new IllegalArgumentException(messageManager.getMessage(messageKey));
    }
}

