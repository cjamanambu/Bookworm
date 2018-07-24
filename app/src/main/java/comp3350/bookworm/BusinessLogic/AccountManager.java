package comp3350.bookworm.BusinessLogic;

import comp3350.bookworm.Application.Service;
import comp3350.bookworm.BusinessLogic.Validator.AccountValidator;
import comp3350.bookworm.BusinessLogic.Validator.PaymentValidator;
import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Address;
import comp3350.bookworm.Objects.Exceptions.DuplicateUsernameException;
import comp3350.bookworm.Objects.Exceptions.InvalidAccountException;
import comp3350.bookworm.Objects.Exceptions.InvalidAddressException;
import comp3350.bookworm.Objects.Exceptions.InvalidCredentialException;
import comp3350.bookworm.Objects.Exceptions.InvalidEmailAddressException;
import comp3350.bookworm.Objects.Exceptions.InvalidPmtInfoException;
import comp3350.bookworm.Objects.Payment;
import comp3350.bookworm.Persistence.AccountPersistence;
import comp3350.bookworm.Persistence.LoginUserPersistence;

public class AccountManager {
    private AccountPersistence accountPersistence;
    private LoginUserPersistence loginUserPersistence;

    private AccountValidator accountValidator;
    private PaymentValidator paymentValidator;

    public AccountManager() {
        accountPersistence = Service.getAccountPersistence();
        loginUserPersistence = Service.getLoginUserPersistence();
        accountValidator = new AccountValidator();
        paymentValidator = new PaymentValidator();
    }

    public AccountManager(final AccountPersistence accountPersistence, final LoginUserPersistence loginUserPersistence) {
        this();
        this.accountPersistence = accountPersistence;
        this.loginUserPersistence = loginUserPersistence;
    }

    public void login (Account currentAccount) throws InvalidCredentialException {
        Account account = accountPersistence.getAccountInfo(currentAccount);
        if(account == null)
            throw new InvalidCredentialException();
        loginUserPersistence.setUsername(account.getUserName());
    }

    public void singup(Account currentAccount) throws DuplicateUsernameException, InvalidEmailAddressException {
        String email = currentAccount.getEmail();
        if(!accountValidator.isValidEmailAddress(email))
            throw new InvalidEmailAddressException();
        accountPersistence.insertAccount(currentAccount);
    }

    public String getUserEmail(String username) throws InvalidCredentialException {
        Account account = accountPersistence.getAccount(username);
        if(account == null)
            throw new InvalidCredentialException();
        return account.getEmail();
    }

    public void updateAddress(String accountUserName, String addr_line_1, String addr_line_2, String city, String province, String post_code) throws InvalidAddressException{
        if(!accountValidator.isValidAddress(addr_line_1, city, province, post_code))
            throw new InvalidAddressException();

        accountPersistence.updateAddress(accountUserName, new Address(addr_line_1, addr_line_2, city, province, post_code));
    }

    public void updateProfie(String accountUserName, String newName, String newEmail, String newPassword) throws InvalidEmailAddressException, InvalidAccountException{
        if(!accountValidator.isValidEmailAddress(newEmail))
            throw new InvalidEmailAddressException();
        if(!accountValidator.isValidUsername(newName))
            throw new InvalidAccountException();
        if(!accountValidator.isValidPassword(newPassword))
            throw new InvalidAccountException();

        loginUserPersistence.setUsername(newName);
        accountPersistence.updateAccount(accountUserName, new Account(newName, newPassword, newEmail));
    }

    public void updatePmtOption(String accountUserName, String issueNet, String cardNo, String cVV, String expiry) throws InvalidPmtInfoException{
        if(!paymentValidator.isValidPmtOption(issueNet, cardNo, cVV, expiry))
            throw new InvalidPmtInfoException();
        accountPersistence.setPaymentOption(accountUserName ,new Payment(issueNet, cardNo, cVV, expiry));
    }

    public Boolean anyLoggedInUser() {
        return loginUserPersistence.loggedIn();
    }

    public String getLoggedInUsername() {
        return loginUserPersistence.getUsername();
    }

    public Address getLoggedInAddress(){
        return accountPersistence.getAccountAddress(getLoggedInUsername());
    }

    public Payment getLoggedInPaymentInfo(){
        return accountPersistence.getPaymentOption(getLoggedInUsername());
    }

    public void logout() {
        loginUserPersistence.logout();
    }

}
