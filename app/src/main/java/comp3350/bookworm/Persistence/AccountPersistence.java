package comp3350.bookworm.Persistence;

import java.util.List;

import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Address;
import comp3350.bookworm.Objects.Exceptions.DuplicateUsernameException;
import comp3350.bookworm.Objects.Payment;

public interface AccountPersistence {

    Account getAccount(String accountUserName);

    Account getAccountInfo(Account currentAccount);

    Account insertAccount(Account currentAccount) throws DuplicateUsernameException;

    Account updateAccount(String username, Account Account);

    void deleteAccount(Account currentAccount);

    List<Account> getAllAccounts();

    void updateAddress(String username, Address address);
    Address getAccountAddress(String username);

    Payment getPaymentOption(String username);

    void setPaymentOption(String username, Payment paymentOption);
}