package com.returdev.product.managers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * A utility class that manages the retrieval of messages from the application's resource bundle.
 * The {@link MessageManager} class interacts with the {@link MessageSource} to retrieve
 * localized messages based on the application's locale.
 */
@Component
@RequiredArgsConstructor
public class MessageManager {

    /**
     * The {@link MessageSource} used to fetch messages from the resource bundle.
     */
    private final MessageSource messageSource;

    /**
     * Retrieves a message from the resource bundle using the specified message resource key and parameters.
     * The message is resolved according to the current locale, which is determined by
     * {@link LocaleContextHolder#getLocale()}.
     *
     * @param messageResource the key for the message to be retrieved.
     * @param params the parameters to be used in the message, or {@code null} if no parameters are needed.
     * @return the resolved message string.
     */
    public String getMessage(String messageResource, Object[] params){
        return messageSource.getMessage(
                messageResource,
                params,
                LocaleContextHolder.getLocale()
        );
    }

    /**
     * Retrieves a message from the resource bundle using the specified message resource key.
     * This method is a convenience overload for cases where no parameters are needed for the message.
     *
     * @param messageResource the key for the message to be retrieved.
     * @return the resolved message string.
     */
    public String getMessage(String messageResource){
        return getMessage(messageResource, null);
    }

}

