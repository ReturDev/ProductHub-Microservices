package com.returdev.product.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Custom exception for handling invalid identifier errors.
 * This exception is thrown when an invalid identifier is encountered,
 * and it provides a resource key for retrieving localized error messages.
 */
@RequiredArgsConstructor
@Getter
public class InvalidIdentifierException extends RuntimeException {

  /**
   * The resource key for the error message associated with this exception.
   * This key can be used to retrieve localized error messages from a message resource bundle.
   */
  private final String messageResource;

}

