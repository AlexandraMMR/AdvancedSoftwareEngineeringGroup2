package uk.ac.bath.cm50286.group2.newbank.server.controller;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountDAO;
import uk.ac.bath.cm50286.group2.newbank.server.dao.AccountTypeDAO;
import uk.ac.bath.cm50286.group2.newbank.server.model.Account;
import uk.ac.bath.cm50286.group2.newbank.server.model.AccountType;
import uk.ac.bath.cm50286.group2.newbank.server.model.Customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AccountControllerTest {

    private static final String MAIN = "Main";
    private static final String SAVINGS = "Savings";
    private static final int MAIN_ID = 1;
    private static final int SAVINGS_ID = 2;

    @Test
    void customerCannotBeNull() {
        final AccountController accountController = new AccountController(mock(AccountDAO.class), mock(AccountTypeDAO.class));

        assertThatNullPointerException()
                .isThrownBy(() -> accountController.createAccount(null, "Savings"))
                .withMessage("FAIL - Customer must not be null");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Checking", "foo", "123", "   "})
    @NullAndEmptySource
    void createAccountWillThrowOnInvalidAccountName(final String accountName) {
        final AccountController accountController = new AccountController(mock(AccountDAO.class), mock(AccountTypeDAO.class));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> accountController.createAccount(mock(Customer.class), accountName))
                .withMessage("FAIL - Invalid command");
    }

    @Test
    void createAccountWillThrowIfAccountAlreadyExists() {
        final AccountDAO accountDAO = mock(AccountDAO.class);
        final Customer customer = mock(Customer.class);
        final Account account = mock(Account.class);
        final AccountTypeDAO accountTypeDAO = mock(AccountTypeDAO.class);
        final AccountType accountType = mock(AccountType.class);

        final AccountController accountController = new AccountController(accountDAO, accountTypeDAO);

        given(accountDAO.getAccountsForCustomer(customer)).willReturn(ImmutableList.of(account));
        given(account.getAccountName()).willReturn(MAIN);
        given(accountType.getAcctdesc()).willReturn(MAIN);
        given(accountTypeDAO.getAllAccountTypes()).willReturn(ImmutableList.of(accountType));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> accountController.createAccount(customer, MAIN))
                .withMessage("FAIL - Account already exists");
    }

    @Test
    void createAccountWillCreateNewAccountForCustomer() {
        final AccountDAO accountDAO = mock(AccountDAO.class);
        final Customer customer = mock(Customer.class);
        final Account account = mock(Account.class);
        final AccountTypeDAO accountTypeDAO = mock(AccountTypeDAO.class);
        final AccountType mainAccountType = mock(AccountType.class);
        final AccountType savingsAccountType = mock(AccountType.class);

        final AccountController accountController = new AccountController(accountDAO, accountTypeDAO);

        given(accountDAO.getAccountsForCustomer(customer)).willReturn(ImmutableList.of(account));
        given(account.getAccountName()).willReturn(MAIN);
        given(account.getAcctTypeID()).willReturn(MAIN_ID);
        given(mainAccountType.getAcctdesc()).willReturn(MAIN);
        given(mainAccountType.getAccttypeid()).willReturn(MAIN_ID);
        given(savingsAccountType.getAcctdesc()).willReturn(SAVINGS);
        given(savingsAccountType.getAccttypeid()).willReturn(SAVINGS_ID);
        given(accountTypeDAO.getAllAccountTypes()).willReturn(ImmutableList.of(mainAccountType, savingsAccountType));

        assertThat(accountController.createAccount(customer, SAVINGS)).isEqualTo("SUCCESS");
    }
}