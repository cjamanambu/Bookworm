package comp3350.bookworm.Persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Address;
import comp3350.bookworm.Objects.Exceptions.DuplicateUsernameException;
import comp3350.bookworm.Objects.Payment;

public class AccountPersistenceStub implements AccountPersistence {
    private List<Account> accounts;


    public AccountPersistenceStub() {
        this.accounts = new ArrayList<>();

        accounts.add(new Account("admin", "admin","admin@bookworm.ca"));
        accounts.add(new Account("visitor", "visitor","visitor@bookworm.ca"));
        accounts.add(new Account("mingo", "mingo","mingo@bookworm.ca"));
        accounts.add(new Account("piklu", "piklu","piklu@bookworm.ca"));
        accounts.add(new Account("q", "q","q@bookworm.ca"));
        accounts.add(new Account("cj", "cj","cj@bookworm.ca"));
        accounts.add(new Account("harrison", "harrison","harrison@bookworm.ca"));
        accounts.add(new Account("jeff", "jeff","jeff@bookworm.ca"));
    }

    @Override
    public Account getAccount(String accountUserName){
        for(Account account : accounts){
            if(account.getUserName().equals(accountUserName))
                return account;
        }
        return null;
    }

    @Override
    public Account getAccountInfo(Account currentAccount) {
        for(Account account : accounts) {
            if(account.equals(currentAccount)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public Account insertAccount(Account currentAccount) throws DuplicateUsernameException {

        for(Account account : accounts) {
            if (account.getUserName().equals(currentAccount.getUserName()))
                throw new DuplicateUsernameException();
        }
        accounts.add(currentAccount);
        return currentAccount;
    }

    @Override
    public Account updateAccount(String username, Account account) {
        for(Account a : accounts){
            if(a.getUserName().equals(username)) {
                a.setUserName(account.getUserName());
                a.setPassword(account.getPassword());
                a.setEmail(account.getEmail());
            }
        }
        return account;
    }

    @Override
    public void deleteAccount(Account currentAccount) {
        int index;

        index = accounts.indexOf(currentAccount);
        if (index >= 0)
        {
            accounts.remove(index);
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        return accounts;
    }

    @Override
    public void updateAddress(String username, Address add) {
        for(Account a : accounts) {
            if(a.getUserName().equals(username)) {
                a.updateAddress(add.getAddressLine1(), add.getAddressLine2(), add.getCity(), add.getProvince(), add.getPostalCode());
                break;
            }
        }
    }

    @Override
    public Address getAccountAddress(String username) {
        for(Account a : accounts) {
            if(a.getUserName().equals(username)) {
                return a.getAddress();
            }
        }
        return new Address();
    }

    @Override
    public void setPaymentOption(String username, Payment p){
        for(Account a : accounts) {
            if(a.getUserName().equals(username)) {
                a.updatePaymentOption(p.getIssuingNetwork(), p.getCardNumber(), p.getcVV(), p.getExpiry());
            }
        }
    }

    @Override
    public Payment getPaymentOption(String username){
        for(Account a : accounts) {
            if(a.getUserName().equals(username)) {
                return a.getPaymentOption();
            }
        }
        return new Payment();
    }
}


