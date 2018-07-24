package comp3350.bookworm.Objects;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void testAccount1()
    {
        Account account;

        System.out.println("\nStarting testAccount1");

        account = new Account("admin", "adminPassword");

        assertNotNull(account);
        assertTrue("admin".equals(account.getUserName()));
        assertTrue("adminPassword".equals(account.getPassword()));

        System.out.println("Finished testAccount1");
    }

    @Test
    public void testAccount2()
    {
        Account account;

        System.out.println("\nStarting testAccount2");

        account = new Account("admin", "adminPassword", "email@bookworm.ca");

        assertNotNull(account);
        assertTrue("admin".equals(account.getUserName()));
        assertTrue("adminPassword".equals(account.getPassword()));
        assertTrue("email@bookworm.ca".equals(account.getEmail()));

        String newName = "newName";
        String password = "newPass";
        String email = "newEmail@bookworm.ca";

        account.setUserName(newName);
        account.setPassword(password);
        account.setEmail(email);

        assertEquals(account.getUserName(), newName);
        assertEquals(account.getPassword(), password);
        assertEquals(account.getEmail(), email);

        System.out.println("Finished testAccount2");
    }

    @Test
    public void testAccountEquals() {
        System.out.println("\nStarting testAccountEquals");


        String username1 = "u1";
        String username2 = "u2";
        String password1 = "p1";
        String password2 = "p2";

        Account a1 = new Account(username1, password1);
        Account a2 = new Account(username2, password2);

        assertTrue(a1.equals(a1));
        assertFalse(a1.equals(a2));

        System.out.println("\nFinishing testAccountEquals");

    }

}
