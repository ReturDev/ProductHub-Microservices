package com.returdev.user.exceptions;

import lombok.Getter;

/**
 * Custom exception to be thrown when an invalid enum value is encountered.
 * <p>
 * This exception provides information about the invalid value and the list of valid enum values.
 * It is typically used when a value provided does not match any of the valid values defined in an enum.
 * </p>
 */
@Getter
public class InvalidEnumValueException extends RuntimeException {

    /**
     * The valid enum values for reference.
     * This is used to provide a list of acceptable enum values in the exception message.
     */
    private final String validValues;

    /**
     * The invalid enum value that caused the exception to be thrown.
     * This is the value that was provided but is not recognized as a valid enum value.
     */
    private final String invalidEnumValue;

    /**
     * Constructs a new {@link InvalidEnumValueException} with the specified message, valid values, and invalid value.
     * <p>
     * This constructor initializes the exception with the appropriate message, valid enum values,
     * and the invalid value that was attempted.
     * </p>
     *
     * @param message The message that describes the exception. Typically provides an error description or instructions.
     * @param validValues A string containing the valid values for the enum, typically formatted for user display.
     * @param invalidEnumValue The invalid value that was attempted to be used for the enum.
     */
    public InvalidEnumValueException(String message, String validValues, String invalidEnumValue) {
        super(message);
        this.validValues = validValues;
        this.invalidEnumValue = invalidEnumValue;
    }
}

