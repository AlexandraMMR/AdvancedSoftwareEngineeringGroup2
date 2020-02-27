package uk.ac.bath.cm50286.group2.newbank.server.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static uk.ac.bath.cm50286.group2.newbank.server.util.StringUtils.convertToTitleCaseSplitting;
import static uk.ac.bath.cm50286.group2.newbank.server.util.StringUtils.requireNonBlank;

class StringUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"  "})
    @NullAndEmptySource
    void requireNonBlankThrowsForNullOrBlankString(final String blankString) {
        final String message = "String must not be blank";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> requireNonBlank(blankString, message))
                .withMessage(message);
    }

    @ParameterizedTest
    @ValueSource(strings = {"foo", "a", "1234", "X  X  X"})
    void requireNonBlankReturnsStringWhenNotBlank(final String nonBlankString) {
        assertThat(requireNonBlank(nonBlankString, "String must not be blank"))
                .isEqualTo(nonBlankString);
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "HELLO", "hElLo"})
    void canCovertDifferentCasesStringToTitleCase(final String hello) {
        final String actual = convertToTitleCaseSplitting(hello);

        assertThat(actual).isEqualTo("Hello");
    }

    @Test
    void canCovertSentenceToTitleCase() {
        final String actual = convertToTitleCaseSplitting("hEllo WORld");

        assertThat(actual).isEqualTo("Hello World");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void providingBlankStringReturnsBlank(final String emptyString) {
        final String actual = convertToTitleCaseSplitting(emptyString);

        assertThat(actual).isEqualTo(emptyString);
    }
}