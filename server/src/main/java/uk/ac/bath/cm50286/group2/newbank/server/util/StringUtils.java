package uk.ac.bath.cm50286.group2.newbank.server.util;

import static org.apache.commons.lang3.StringUtils.isBlank;

public final class StringUtils {

    private StringUtils() {
    }

    public static String requireNonBlank(final String toCheck, final String message) {
        if (isBlank(toCheck))
            throw new IllegalArgumentException(message);
        return toCheck;
    }
}
