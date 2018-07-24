package comp3350.bookworm.Persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Address;
import comp3350.bookworm.Objects.Exceptions.DuplicateUsernameException;
import comp3350.bookworm.Objects.Payment;
import comp3350.bookworm.Persistence.AccountPersistence;

public class AccountPersistenceHSQLDB implements AccountPersistence {
    private final String dbPath;

    public AccountPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Account fromResultSet(final ResultSet rs) throws SQLException {
        final String username = rs.getString("username");
        final String password = rs.getString("password");
        final String email = rs.getString("email");
        return new Account(username, password, email);
    }

    @Override
    public Account getAccount(String accountUserName){
        Account account = null;

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM Accounts WHERE username = ?");
            st.setString(1, accountUserName);
            final ResultSet rs = st.executeQuery();
            if(rs.next())
                account = fromResultSet(rs);
            rs.close();
            st.close();

            return account;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Account getAccountInfo(Account currentAccount) {
        Account account = null;

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM Accounts WHERE username = ? AND password = ?");
            st.setString(1, currentAccount.getUserName());
            st.setString(2, currentAccount.getPassword());
            final ResultSet rs = st.executeQuery();
            if(rs.next())
                account = fromResultSet(rs);
            rs.close();
            st.close();

            return account;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Account insertAccount(Account currentAccount) throws DuplicateUsernameException {
        try (final Connection c = connection()) {
            PreparedStatement st = c.prepareStatement("INSERT INTO Accounts VALUES(?, ?, ?)");
            st.setString(1, currentAccount.getUserName());
            st.setString(2, currentAccount.getPassword());
            st.setString(3, currentAccount.getEmail());

            st.executeUpdate();

            return currentAccount;
        } catch (final SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException)
                throw new DuplicateUsernameException();
            else
                throw new PersistenceException(e);
        }
    }

    @Override
    public Account updateAccount(String username, Account account) {
        try (final Connection c = connection()) {
            PreparedStatement st = c.prepareStatement("UPDATE Accounts " +
                    "SET username = ?, password = ?, email = ? WHERE username = ?");
            st.setString(1, account.getUserName());
            st.setString(2, account.getPassword());
            st.setString(3, account.getEmail());
            st.setString(4, username);

            st.executeUpdate();

            return account;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteAccount(Account currentAccount) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM Accounts WHERE username = ?");
            st.setString(1, currentAccount.getUserName());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        final List<Account> accounts = new ArrayList<>();

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM Accounts");
            while (rs.next()) {
                final Account account = fromResultSet(rs);
                accounts.add(account);
            }
            rs.close();
            st.close();

            return accounts;
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void updateAddress(String username, Address add) {
        if(add != null){
            try (final Connection c = connection()) {
                PreparedStatement st = c.prepareStatement("UPDATE ADDRESS " +
                        "SET ADDLINE1 = ?, ADDLINE2 = ?, CITY = ?, PROVINCE = ?, POSTALCODE = ?  WHERE username = ?");
                st.setString(1, add.getAddressLine1());
                st.setString(2, add.getAddressLine2());
                st.setString(3, add.getCity());
                st.setString(4, add.getProvince());
                st.setString(5, add.getPostalCode());
                st.setString(6, username);

                st.executeUpdate();

            } catch (final SQLException e) {
                throw new PersistenceException(e);
            }
        }
    }

    @Override
    public Address getAccountAddress(String username) {
        Address address = new Address();

        try (final Connection c = connection()) {
            PreparedStatement st = c.prepareStatement("SELECT * FROM ADDRESS WHERE USERNAME = ?");
            st.setString(1, username);
            final ResultSet rs = st.executeQuery();
            if(rs.next()) {
                address.setAddressLine1(rs.getString("ADDLINE1"));
                address.setAddressLine2(rs.getString("ADDLINE2"));
                address.setCity(rs.getString("CITY"));
                address.setProvince(rs.getString("PROVINCE"));
                address.setPostalCode(rs.getString("POSTALCODE"));
            }
            st.close();
            rs.close();

            return address;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Payment getPaymentOption(String username) {
        Payment payment = new Payment();

        try (final Connection c = connection()) {
            PreparedStatement st = c.prepareStatement("SELECT * FROM PAYMENT WHERE USERNAME = ?");
            st.setString(1, username);
            final ResultSet rs = st.executeQuery();
            if(rs.next()) {
                payment.setIssuingNetwork(rs.getString("ISSUINGNETWORK"));
                payment.setCardNumber(rs.getString("CARDNUM"));
                payment.setcVV(rs.getString("CVV"));
                payment.setExpiry(rs.getString("EXPIRY"));
            }
            st.close();
            rs.close();

            return payment;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void setPaymentOption(String username, Payment paymentOption) {

        try (final Connection c = connection()) {
            PreparedStatement st = c.prepareStatement("UPDATE PAYMENT " +
                    "SET ISSUINGNETWORK = ?, CARDNUM = ?, CVV = ?, EXPIRY = ?  WHERE username = ?");
            st.setString(1, paymentOption.getIssuingNetwork());
            st.setString(2, paymentOption.getCardNumber());
            st.setString(3, paymentOption.getcVV());
            st.setString(4, paymentOption.getExpiry());
            st.setString(5, username);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
