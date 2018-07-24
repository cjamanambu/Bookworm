package comp3350.bookworm.Business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.BusinessLogic.ShoppingCartManager;
import comp3350.bookworm.BusinessLogic.Time.Stub.TimeProviderStub;
import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.InvalidAccountException;
import comp3350.bookworm.Objects.Exceptions.InvalidCredentialException;
import comp3350.bookworm.Objects.Exceptions.UserNotSignedInException;
import comp3350.bookworm.utils.TestUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class ShoppingCartManagerIT {
    private ShoppingCartManager shoppingCartManager;
    private AccountManager accountManager;

    @Before
    public void setUp() throws IOException {
        TestUtils.copyDB();
        shoppingCartManager = new ShoppingCartManager();
        accountManager = new AccountManager();
    }

    @After
    public void tearDown() {
        TestUtils.delete();
        shoppingCartManager = null;
        accountManager = null;
    }

    @Test
    public void testAddBookToUsersCart(){
        System.out.println("\nStarting test add book to user's cart");
        String username = "admin";
        String password = "admin";
        Account accountToAddTo = new Account(username, password);
        Book bookToAdd = new Book("Franklin Goes to School", "Paulette Bourgeois", "Children's Book", "Childrens", 15.0, 5.0, 99);

        try{
            accountManager.login(accountToAddTo);
            shoppingCartManager.addBookToUsersCart(accountToAddTo.getUserName(), bookToAdd);
        } catch (InvalidCredentialException e) {
            fail("Exceptions not expected");
        } catch (UserNotSignedInException e) {
            fail("Exceptions not expected");
        } catch (InvalidAccountException e) {
            fail("Exceptions not expected");
        }

        System.out.println("\nFinishing test add book to user's cart");
    }

    @Test
    public void testGetShoppingCartForCurrentUser(){
        System.out.println("\nStarting test get shopping cart for current user");
        String username = "admin";
        String password = "admin";
        Account accountToGetFrom = new Account(username, password);
        Book bookToAdd = new Book("C++", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 1);
        List<Book> list = new ArrayList<>();
        try {
            accountManager.login(accountToGetFrom);
            shoppingCartManager.clearShoppingCart(username);
            shoppingCartManager.addBookToUsersCart(username, bookToAdd);
            list = shoppingCartManager.getShoppingCartForCurrentUser(username);
        } catch (InvalidCredentialException e) {
            fail("Exceptions not expected");
        } catch (InvalidAccountException e) {
            fail("Exceptions not expected");
        } catch (UserNotSignedInException e) {
            fail("Exceptions not expected");
        }

        List<Book> expectedCart = new ArrayList<>();
        expectedCart.add(bookToAdd);
        assertNotNull(list);
        assertEquals(bookToAdd.getBookName(), list.get(0).getBookName());

        System.out.println("\nFinishing test get shopping cart for current user");
    }

    @Test
    public void testGetShoppingCartTotal(){
        System.out.println("\nStarting test get shopping cart total");
        String username = "admin";
        String password = "admin";
        Account account = new Account(username, password);
        String total = "10.0";
        Book bookToAdd = new Book("C++", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 1);
        String shoppingCartTotal = "";
        try {
            accountManager.login(account);
            shoppingCartManager.addBookToUsersCart(username, bookToAdd);
            shoppingCartTotal = shoppingCartManager.getShoppingCartTotal(username);
        } catch (InvalidCredentialException e) {
            fail("Exceptions not expected");
        } catch (InvalidAccountException e) {
            fail("Exceptions not expected");
        } catch (UserNotSignedInException e) {
            fail("Exceptions not expected");
        }
        assertEquals(total, shoppingCartTotal);

        System.out.println("\nStarting test get shopping cart total");
    }

    @Test
    public void testShoppingCartIsEmpty(){
        System.out.println("\nStarting test get shopping cart total");
        String username = "admin";
        String password = "admin";
        assertTrue(shoppingCartManager.shoppingCartIsEmpty(username));
        System.out.println("\nStarting test get shopping cart total");
    }

    @Test
    public void testClearShoppingCart(){
        System.out.println("\nStarting test get shopping cart total");
        String username = "admin";
        String password = "admin";
        Account account = new Account(username, password);
        Book bookToAdd = new Book("C++", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 1);

        try {
            accountManager.login(account);
            shoppingCartManager.addBookToUsersCart(username, bookToAdd);
            shoppingCartManager.clearShoppingCart(username);
        } catch (InvalidCredentialException e) {
            fail("Exceptions not expected");
        } catch (InvalidAccountException e) {
            fail("Exceptions not expected");
        } catch (UserNotSignedInException e) {
            fail("Exceptions not expected");
        }
        assertTrue(shoppingCartManager.shoppingCartIsEmpty(username));
        System.out.println("\nStarting test get shopping cart total");
    }

    @Test
    public void testGetItemIntotal() {
        System.out.println("\nStarting testGetItemIntotal");
        double total = 10;
        String username = "admin";
        String password = "admin";
        Account account = new Account(username, password);
        Book bookToAdd = new Book("C++", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 1);
        double shoppingCartTotal = 0.0;
        try {
            accountManager.login(account);
            shoppingCartManager.addBookToUsersCart(username, bookToAdd);
            shoppingCartTotal = shoppingCartManager.getItemTotal(new TimeProviderStub());
        } catch (InvalidCredentialException e) {
            fail("Exceptions not expected");
        } catch (InvalidAccountException e) {
            fail("Exceptions not expected");
        } catch (UserNotSignedInException e) {
            fail("Exceptions not expected");
        }
        assertEquals(total/2, shoppingCartTotal);
        System.out.println("Finishing testGetItemIntotal");
    }

    @Test
    public void testGetOrderIntotal() {
        System.out.println("\nStarting testGetOrderIntotal");
        double total = 10;
        String username = "admin";
        String password = "admin";
        Account account = new Account(username, password);
        Book bookToAdd = new Book("C++", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 1);
        double shoppingCartTotal = 0.0;
        double totalAfterAll = 0.0;
        try {
            accountManager.login(account);
            shoppingCartManager.addBookToUsersCart(username, bookToAdd);
            shoppingCartTotal = shoppingCartManager.getItemTotal(new TimeProviderStub());
            totalAfterAll = shoppingCartManager.getOrderTotal(shoppingCartTotal);
        } catch (InvalidCredentialException e) {
            fail("Exceptions not expected");
        } catch (InvalidAccountException e) {
            fail("Exceptions not expected");
        } catch (UserNotSignedInException e) {
            fail("Exceptions not expected");
        }
        double expectedTotal = shoppingCartManager.getTotalBeforeTax(shoppingCartTotal) + shoppingCartManager.getGST(shoppingCartTotal) + shoppingCartManager.getPST(shoppingCartTotal);
        assertEquals(expectedTotal, totalAfterAll);
        System.out.println("Finishing testGetOrderIntotal");
    }
}
