package com.returdev.user.services.exception;

import com.returdev.user.util.managers.MessageManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Service responsible for creating and managing exceptions with localized messages.
 * <p>
 * This service provides utility methods to create specific types of exceptions, such as
 * {@link EntityNotFoundException} and {@link IllegalArgumentException}, with messages retrieved
 * from a {@link MessageManager}.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ExceptionService {

    private final MessageManager messageManager;

    /**
     * Creates an {@link EntityNotFoundException} with a localized message based on the provided message key
     * and a parameter to include in the message.
     *
     * @param messageResource The key for the message resource to be used in the exception.
     * @param param A parameter to include in the localized message.
     * @return A new {@link EntityNotFoundException} instance with the appropriate message.
     */
    public EntityNotFoundException createEntityNotFoundException(String messageResource, Object param) {
        return new EntityNotFoundException(
                messageManager.getMessage(
                        messageResource,
                        new Object[]{param}
                )
        );
    }

    /**
     * Creates an {@link EntityNotFoundException} for a specific entity ID.
     * <p>
     * The message is retrieved using the key "exception.entity_not_found.id.message" and includes the given entity ID.
     * </p>
     *
     * @param entityId The ID of the entity that was not found.
     * @return A new {@link EntityNotFoundException} instance with the appropriate message.
     */
    public EntityNotFoundException createEntityNotFoundExceptionById(Object entityId) {
        return createEntityNotFoundException("exception.entity_not_found.id.message", entityId);
    }

    /**
     * Creates an {@link EntityNotFoundException} for cases where multiple entities are not found.
     * <p>
     * The message is retrieved using the key "exception.some_entity_not_found.message".
     * </p>
     *
     * @return A new {@link EntityNotFoundException} instance with the appropriate message.
     */
    public EntityNotFoundException createEntityNotFoundExceptionByIds() {
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
