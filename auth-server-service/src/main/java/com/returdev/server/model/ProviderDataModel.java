package com.returdev.server.model;

/**
 * Data model representing provider information.
 * <p>
 * This record encapsulates the provider's unique identifier and name.
 * </p>
 *
 * @param providerId The unique identifier for the provider.
 * @param providerName The name of the provider.
 */
public record ProviderDataModel(
        String providerId,
        String providerName
) {}
