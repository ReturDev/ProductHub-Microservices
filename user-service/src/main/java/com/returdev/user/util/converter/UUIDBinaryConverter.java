package com.returdev.user.util.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * A JPA converter that converts a {@link UUID} to a binary format (byte array) and vice versa.
 * <p>
 * This converter is used to store {@link UUID} values in the database as a binary format
 * and retrieve them back as {@link UUID} objects when querying the database.
 * </p>
 * <p>
 * The {@link UUID} is split into two {@code long} values (most and least significant bits) and stored as a
 * 16-byte array. This enables efficient storage and retrieval of UUIDs in databases that require binary storage.
 * </p>
 *
 * @see AttributeConverter
 */
@Converter
public class UUIDBinaryConverter implements AttributeConverter<UUID, byte[]> {

    /**
     * Converts a {@link UUID} to a binary byte array to store in the database.
     * <p>
     * This method converts the UUID into a 16-byte array, with the most significant and least significant
     * bits of the UUID being placed in the array.
     * </p>
     *
     * @param uuid the UUID to convert.
     * @return the byte array representing the UUID, or {@code null} if the input UUID is {@code null}.
     */
    @Override
    public byte[] convertToDatabaseColumn(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);

        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());

        return byteBuffer.array();
    }

    /**
     * Converts a binary byte array from the database back into a {@link UUID}.
     * <p>
     * This method reconstructs a {@link UUID} from a 16-byte array by reading the most and least significant
     * bits from the byte array and creating a new {@link UUID} object.
     * </p>
     *
     * @param bytes the byte array representing the UUID.
     * @return the UUID object created from the byte array.
     * @throws IllegalArgumentException if the byte array does not represent a valid UUID.
     */
    @Override
    public UUID convertToEntityAttribute(byte[] bytes) {
        if (bytes == null || bytes.length != 16) {
            throw new IllegalArgumentException("Invalid byte array length for UUID conversion.");
        }

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();

        return new UUID(mostSignificantBits, leastSignificantBits);
    }
}

