package uk.ac.bath.cm50286.group2.newbank.server.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

public final class StringUtils {

    private StringUtils() {
    }

    private static final String WORD_SEPARATOR = " ";

    public static String requireNonBlank(final String toCheck, final String message) {
        if (isBlank(toCheck))
            throw new IllegalArgumentException(message);
        return toCheck;
    }

    public static String convertToTitleCaseSplitting(final String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return Arrays
                .stream(text.split(WORD_SEPARATOR))
                .map(word -> word.isEmpty()
                        ? word
                        : Character.toTitleCase(word.charAt(0)) + word
                        .substring(1)
                        .toLowerCase())
                .collect(Collectors.joining(WORD_SEPARATOR));
    }
}
