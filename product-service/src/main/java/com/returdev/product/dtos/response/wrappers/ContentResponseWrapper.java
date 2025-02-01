package com.returdev.product.dtos.response.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper for responses containing a single data object.
 * It encapsulates the data content with the property key {@code "data"}.
 *<p>
 * This class is used to standardize the response format, ensuring that all responses
 * contain a single data item wrapped under the key {@code "data"}, which is useful in
 * APIs for consistency and easy deserialization.
 *
 * @param <T> The type of the data contained in the response.
 */
public record ContentResponseWrapper<T>(
        @JsonProperty("data") T content
) {}
