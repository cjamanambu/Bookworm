package comp3350.bookworm.Business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Exceptions.DuplicateUsernameException;
import comp3350.bookworm.Objects.Exceptions.InvalidAccountException;
import comp3350.bookworm.Objects.Exceptions.InvalidAddressException;
import comp3350.bookworm.Objects.Exceptions.InvalidCredentialException;
import comp3350.bookworm.Objects.Exceptions.InvalidEmailAddressException;
import comp3350.bookworm.Objects.Exceptions.InvalidPmtInfoException;
import comp3350.bookworm.Objects.Payment;
import comp3350.bookworm.Persistence.AccountPersistence;
import comp3350.bookworm.Persistence.AccountPersistenceStub;
import comp3350.bookworm.Persistence.LoginUserPersistence;
import comp3350.bookworm.Persistence.LoginUserPersistenceStub;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class AccountManagerTest {
    private AccountManager accountManager;
    private AccountManager accountManagerMock;
    private AccountPersistence accountPersistence;
    private LoginUserPersistence loginUserPersistence;

    @Before
    public void setup() {
        accountPersistence = mock(AccountPersistence.class);
        loginUserPersistence = mock(LoginUserPersistence.class);
        accountManagerMock = new AccountManager(accountPersistence, loginUserPersistence);
        accountManager = new AccountManager(new AccountPersistenceStub(), new LoginUserPersistenceStub());
    }

    @After
    public void tearDown() {
        accountManager = null;
        accountManagerMock = null;
        accountPersistence = null;
        loginUserPersistence = null;
    }

    @Test
    public void testInvalidCredential() {
        System.out.println("\nStarting testInvalidCredential");

        String username = "SomeInvalidUsername";
        String password = "SomeInvalidPassword";

        try {
            accountManager.login(new Account(username, password));
            fail("Exception expected.");
        }
        catch (InvalidCredentialException e) {

        }

        System.out.println("Finishing testInvalidCredential");
    }

    @Test
    public void testValidCredential() {
        System.out.println("\nStarting testValidCredential");

        accountManager.logout();
        assertFalse(accountManager.anyLoggedInUser());

        String username = "admin";
        String password = "admin";

        try {
            accountManager.login(new Account(username, password));
        }
        catch (InvalidCredentialException e) {
            fail("Exception not expected.");
        }

        assertTrue(accountManager.anyLoggedInUser());
        assertEquals(username, accountManager.getLoggedInUsername());

        System.out.println("Finishing testValidCredential");

    }

    @Test
    public void testDuplicateUser() {
        System.out.println("\nStarting testDuplicateUser");

        String username = "admin";
        String password = "admin";
        String email = "admin@bookworm.ca";

        try {
            accountManager.singup(new Account(username, password, email));
            fail("Exception expected.");
        } catch (DuplicateUsernameException e) {

        } catch (InvalidEmailAddressException e) {
            fail("Exception not expected.");
        }

        System.out.println("Finishing testDuplicateUser");
    }

    @Test
    public void testValidSignup() {
        System.out.println("\nStarting testValidSignup");

        String username = "user1";
        String password = "pass1";
        String email = "user1@bookworm.ca";

        try {
            accountManager.singup(new Account(username, password, email));
        } catch (DuplicateUsernameException e) {
            fail("Exception not expected.");
        } catch (InvalidEmailAddressException e) {
            fail("Exception not expected.");
        }

        System.out.println("Finishing testValidSignup");
    }

    @Test
    public void testGetEmail() {
        System.out.println("\nStarting testGetEmail");

        String username = "user1";
        String password = "pass1";
        String email = "user1@bookworm.ca";
        Account account = new Account(username, password, email);
        when(accountPersistence.getAccount(username)).thenReturn(account);

        try {
            accountManagerMock.singup(account);
            String email2 = accountManagerMock.getUserEmail(username);
            assertEquals(email, email2);
        } catch (DuplicateUsernameException e) {
            fail("Exception not expected.");
        } catch (InvalidEmailAddressException e) {
            fail("Exception not expected.");
        } catch (InvalidCredentialException e) {
            fail("Exception not expected.");
        }
        verify(accountPersistence).getAccount(username);

        System.out.println("Finishing testGetEmail");
    }

    @Test
    public void testEditProfie() {
        System.out.println("\nStarting testEditProfie");

        Account ac = new Account("some Name", "Some pass", "someEmail@bookworm.ca");
        String username = "newName";
        String password = "newPass";
        String email = "newEmail@bookworm.ca";

        try{
            accountManager.singup(ac);
            accountManager.updateProfie("some Name", username, email, password);
        }catch(InvalidEmailAddressException e){
            fail("Exception not expected. ");
        } catch (DuplicateUsernameException e) {
            fail("Exception not expected. ");
        } catch (InvalidAccountException e) {
            fail("Exception not expected. ");
        }

        assertEquals(ac.getUserName(), username);
        assertEquals(ac.getPassword(), password);
        assertEquals(ac.getEmail(), email);

        System.out.println("\nFinishing testEditProfie");
    }

    @Test
    public void testEditAddress() {
        System.out.println("\nStarting testEditAddress");

        String username = "name";
        String password = "pass";
        String email = "email@bookworm.ca";
        String addr_line_1 = "Some place";
        String addr_line_2 = "Some other place";
        String city = "Winnipeg";
        String province = "MB";
        String post_code = "R3T 2M8";

        Account ac = new Account(username, password, email);

        try{
            accountManager.singup(ac);
            accountManager.login(ac);
            accountManager.updateAddress(username, addr_line_1, addr_line_2, city, province, post_code);
        }catch(InvalidEmailAddressException e){
            fail("Exception not expected. ");
        } catch (DuplicateUsernameException e) {
            fail("Exception not expected. ");
        } catch (InvalidAddressException e) {
            fail("Exception not expected. ");
        } catch (InvalidCredentialException e) {
            fail("Exception not expected. ");
        }

        assertEquals(ac.getAddress(), accountManager.getLoggedInAddress());

        System.out.println("\nFinishing testEditAddress");
    }

    @Test
    public void testLogout() {
        System.out.println("\nStarting testLogout");

        accountManager.logout();
        assertFalse(accountManager.anyLoggedInUser());

        String username = "admin";
        String password = "admin";

        try {
            accountManager.login(new Account(username, password));
        }
        catch (InvalidCredentialException e) {
            fail("Exception not expected.");
        }

        assertTrue(accountManager.anyLoggedInUser());
        assertEquals(username, accountManager.getLoggedInUsername());

        accountManager.logout();

        assertFalse(accountManager.anyLoggedInUser());

        System.out.println("Finishing testLogout");
    }


    @Test
    public void testUpdatePmtOption() {
        System.out.println("\nStarting testUpdatePmtOption");

        String username = "admin";
        String issueNet = "RBC";
        String cardNo = "1234123412341324";
        String cYY = "123";
        String expiry = "12/2019";

        when(accountPersistence.getPaymentOption(username)).thenReturn(new Payment(issueNet, cardNo, cYY, expiry));
        when(loginUserPersistence.getUsername()).thenReturn(username);

        try {
            accountManagerMock.updatePmtOption(username, issueNet, cardNo, cYY, expiry);

            Payment payment = accountManagerMock.getLoggedInPaymentInfo();
            assertEquals(issueNet, payment.getIssuingNetwork());
            assertEquals(cardNo, payment.getCardNumber());
            assertEquals(cYY, payment.getcVV());
            assertEquals(expiry, payment.getExpiry());
        } catch (InvalidPmtInfoException e) {
            fail("Exception not expected.");
        }

        verify(accountPersistence).getPaymentOption(username);

        System.out.println("Finishing testUpdatePmtOption");
    }

}
