package uk.ac.bath.cm50286.group2.newbank.server;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.mockito.Mockito.mock;

class NewBankTest {

    @Test
    void customerCannotBeNull() {
        final NewBank newBank = NewBank.getBank();
        assertThatNullPointerException()
                .isThrownBy(() -> newBank.processRequest(null, "NEWACCOUNT Savings"))
                .withMessage("FAIL - Customer cannot be null");
    }

    @ParameterizedTest
    @ValueSource(strings = {"  "})
    @NullAndEmptySource
    void requestCannotBeBlank(final String request) {
        final NewBank newBank = NewBank.getBank();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> newBank.processRequest(mock(Customer.class), request))
                .withMessage("FAIL - Request cannot be null or blank");
    }

    @Test
    void newAccountCommandWillCreateNewAccount() {
        final NewBank newBank = NewBank.getBank();
        final String result = newBank.processRequest(mock(Customer.class), "NEWACCOUNT Savings");
        assertThat(result).isEqualTo("SUCCESS");
    }

    @Test
    void invalidRequestWillResultInFailResponse() {
        final NewBank newBank = NewBank.getBank();
        final String result = newBank.processRequest(mock(Customer.class), "NEWACCOUNT ISA");
        assertThat(result).isEqualTo("FAIL - Invalid command");
    }
}