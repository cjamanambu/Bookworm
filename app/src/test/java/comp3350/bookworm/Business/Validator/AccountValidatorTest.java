package comp3350.bookworm.Business.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.bookworm.BusinessLogic.Validator.AccountValidator;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class AccountValidatorTest {
    private AccountValidator accountValidator;

    @Before
    public void setup() {
        accountValidator = new AccountValidator();
    }

    @After
    public void tearDown() {
        accountValidator = null;
    }

    @Test
    public void testEmailValidator() {
        System.out.println("\nStarting testEmailValidator");

        String invalidEmail = "this is invalid email";
        String validEmail = "admin@bookworm.ca";

        assertFalse(accountValidator.isValidEmailAddress(invalidEmail));
        assertTrue(accountValidator.isValidEmailAddress(validEmail));

        System.out.println("Finishing testEmailValidator");
    }

    @Test
    public void testAddressValidator() {
        System.out.println("\nStarting testAddressValidator");

        assertFalse(accountValidator.isValidAddress("","","",""));
        assertTrue(accountValidator.isValidAddress("University of Manitoba", "Winnipeg", "MB", "R3T 2M8"));

        System.out.println("Finishing testAddressValidator");
    }

    @Test
    public void testUsernameValidator() {
        System.out.println("\nStarting testUsernameValidator");

        String invalidUsername = "";
        String validUsername = "admin";

        assertFalse(accountValidator.isValidUsername(invalidUsername));
        assertTrue(accountValidator.isValidUsername(validUsername));

        System.out.println("Finishing testUsernameValidator");
    }

    @Test
    public void testPasswordValidator() {
        System.out.println("\nStarting testPasswordValidator");

        String invalidPassword = "";
        String validPassword = "password";

        assertFalse(accountValidator.isValidUsername(invalidPassword));
        assertTrue(accountValidator.isValidUsername(validPassword));

        System.out.println("Finishing testPasswordValidator");
    }

}
