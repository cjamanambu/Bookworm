package comp3350.bookworm.Business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Address;
import comp3350.bookworm.Objects.Exceptions.DuplicateUsernameException;
import comp3350.bookworm.Objects.Exceptions.InvalidAccountException;
import comp3350.bookworm.Objects.Exceptions.InvalidAddressException;
import comp3350.bookworm.Objects.Exceptions.InvalidCredentialException;
import comp3350.bookworm.Objects.Exceptions.InvalidEmailAddressException;
import comp3350.bookworm.Objects.Exceptions.InvalidPmtInfoException;
import comp3350.bookworm.Objects.Payment;
import comp3350.bookworm.utils.TestUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class AccountManagerIT {
    private AccountManager accountManager;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.accountManager = new AccountManager();
    }

    @After
    public void tearDown() {
        // reset DB
//        this.tempDB.delete();
        TestUtils.delete();
        this.accountManager = null;
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
    public void testEditProfie() {
        System.out.println("\nStarting testEditProfie");

        Account ac = new Account("some Name", "Some pass", "someEmail@bookworm.ca");
        String username = "newName";
        String password = "newPass";
        String email = "newEmail@bookworm.ca";

        try{
            accountManager.singup(ac);
            accountManager.updateProfie("some Name", username, email, password);
            // try login with the new credential and verify email
            accountManager.login(new Account(username, password));
            assertEquals(email, accountManager.getUserEmail(username));
        } catch (InvalidCredentialException e) {
            fail("Exception not expected.");
        } catch (DuplicateUsernameException e) {
            fail("Exception not expected.");
        } catch (InvalidEmailAddressException e) {
            fail("Exception not expected.");
        } catch (InvalidAccountException e) {
            fail("Exception not expected.");
        }

        System.out.println("\nFinishing testEditProfie");
    }


    @Test
    public void testEditAddress() {
        System.out.println("\nStarting testEditAddress");

        String username = "admin";
        String password = "admin";
        String addr_line_1 = "Some place";
        String addr_line_2 = "Some other place";
        String city = "Winnipeg";
        String province = "MB";
        String post_code = "R3T 2M8";
        Address address = new Address();

        Account ac = new Account(username, password);

        try{
            accountManager.login(ac);
            accountManager.updateAddress(username, addr_line_1, addr_line_2, city, province, post_code);
            address = accountManager.getLoggedInAddress();
        } catch (InvalidAddressException e) {
            fail("Exception not expected. ");
        } catch (InvalidCredentialException e) {
            e.printStackTrace();
        }

        assertEquals(addr_line_1, address.getAddressLine1());
        assertEquals(addr_line_2, address.getAddressLine2());
        assertEquals(city, address.getCity());
        assertEquals(province, address.getProvince());
        assertEquals(post_code, address.getPostalCode());

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
        String password = "admin";
        String issueNet = "RBC";
        String cardNo = "1234123412341234";
        String cVV = "123";
        String expiry = "12/2019";

        Account ac = new Account(username, password);
        Payment payment = new Payment();

        try{
            accountManager.login(ac);
            accountManager.updatePmtOption(username, issueNet, cardNo, cVV, expiry);
            payment = accountManager.getLoggedInPaymentInfo();
        } catch (InvalidCredentialException e) {
            fail("Exception not expected. ");
        } catch (InvalidPmtInfoException e) {
            fail("Exception not expected. ");
        }

        assertEquals(issueNet, payment.getIssuingNetwork());
        assertEquals(cardNo, payment.getCardNumber());
        assertEquals(cVV, payment.getcVV());
        assertEquals(expiry, payment.getExpiry());

        System.out.println("\nFinishing testUpdatePmtOption");
    }
}
