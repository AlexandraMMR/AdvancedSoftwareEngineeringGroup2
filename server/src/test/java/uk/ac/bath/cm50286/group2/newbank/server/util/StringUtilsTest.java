package uk.ac.bath.cm50286.group2.newbank.server.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
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
}