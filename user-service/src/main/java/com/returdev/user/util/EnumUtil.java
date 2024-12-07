package com.returdev.user.util;

import java.util.List;

/**
 * Utility class for working with enums.
 */
public class EnumUtil {

    private EnumUtil() {}

    /**
     * Generates a human-readable string representing the valid enum values from the provided list of enums.
     *
     * @param validValues A list of valid enum values to be formatted into a string.
     * @param <E> The type of the enum.
     * @return A string containing the enum values formatted as a list, with commas separating the values and "or"
     *         before the last value.
     */
    public static <E extends Enum<E>> String getValidValuesToString(List<E> validValues) {
        List<String> names = validValues.stream()
                .map(Enum::name)
                .toList();
        return String.join(", ", names.subList(0, names.size() - 1)) + " or " + names.get(names.size() - 1);
    }

}

