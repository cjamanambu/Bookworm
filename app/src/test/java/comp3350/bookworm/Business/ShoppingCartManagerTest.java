package comp3350.bookworm.Business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
import comp3350.bookworm.Persistence.AccountPersistence;
import comp3350.bookworm.Persistence.LoginUserPersistence;
import comp3350.bookworm.Persistence.ShoppingCartPersistence;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShoppingCartManagerTest {
    private ShoppingCartManager shoppingCartManager;
    private AccountManager accountManager;
    private AccountPersistence accountPersistence;
    private LoginUserPersistence loginUserPersistence;
    private ShoppingCartPersistence shoppingCartPersistence;

    @Before
    public void setUp() {
        loginUserPersistence = mock(LoginUserPersistence.class);
        accountPersistence = mock(AccountPersistence.class);
        shoppingCartPersistence = mock(ShoppingCartPersistence.class);
        accountManager = new AccountManager(accountPersistence, loginUserPersistence);
        shoppingCartManager = new ShoppingCartManager(shoppingCartPersistence, loginUserPersistence);
    }

    @After
    public void tearDown() {
        loginUserPersistence = null;
        shoppingCartPersistence = null;
        accountPersistence = null;
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

        when(loginUserPersistence.loggedIn()).thenReturn(true);
        when(accountPersistence.getAccountInfo(accountToAddTo)).thenReturn(accountToAddTo);
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

        verify(loginUserPersistence).loggedIn();
        verify(accountPersistence).getAccountInfo(accountToAddTo);

        System.out.println("\nFinishing test add book to user's cart");
    }

    @Test
    public void testGetShoppingCartForCurrentUser(){
        System.out.println("\nStarting test get shopping cart for current user");
        String username = "admin";
        String password = "admin";
        Account accountToGetFrom = new Account(username, password);


        List<Book> expectedCart = new ArrayList<>();
        expectedCart.add(new Book("C++", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 1));
        when(shoppingCartPersistence.getShoppingCartForUser(username)).thenReturn(expectedCart);

        List<Book> cart = shoppingCartManager.getShoppingCartForCurrentUser(accountToGetFrom.getUserName());
        assertNotNull(cart);
        assertEquals(expectedCart, cart);

        verify(shoppingCartPersistence).getShoppingCartForUser(username);
        System.out.println("\nFinishing test get shopping cart for current user");
    }

    @Test
    public void testGetShoppingCartTotal(){
        System.out.println("\nStarting test get shopping cart total");
        String username = "admin";
        String total = "10.0";

        when(shoppingCartPersistence.getShoppingCartTotal(username)).thenReturn(10.0);

        String shoppingCartTotal = shoppingCartManager.getShoppingCartTotal(username);

        assertEquals(total, shoppingCartTotal);
        verify(shoppingCartPersistence).getShoppingCartTotal(username);

        System.out.println("\nStarting test get shopping cart total");
    }

    @Test
    public void testShoppingCartIsEmpty(){
        System.out.println("\nStarting test get shopping cart total");
        String username = "admin";

        when(shoppingCartPersistence.shoppingCartIsEmpty(username)).thenReturn(true);

        assertTrue(shoppingCartManager.shoppingCartIsEmpty(username));
        verify(shoppingCartPersistence).shoppingCartIsEmpty(username);
        System.out.println("\nStarting test get shopping cart total");
    }

    @Test
    public void testClearShoppingCart(){
        System.out.println("\nStarting test get shopping cart total");
        String username = "admin";

        shoppingCartManager.clearShoppingCart(username);
        verify(shoppingCartPersistence).clearShoppingCart(username);
        System.out.println("\nStarting test get shopping cart total");
    }

    @Test
    public void testGetItemIntotal() {
        System.out.println("\nStarting testGetItemIntotal");
        String username = "admin";
        double total = 10;

        when(loginUserPersistence.getUsername()).thenReturn(username);
        when(shoppingCartPersistence.getShoppingCartTotal(username)).thenReturn(total);

        assertEquals(total/2, shoppingCartManager.getItemTotal(new TimeProviderStub()));
        verify(loginUserPersistence).getUsername();
        verify(shoppingCartPersistence).getShoppingCartTotal(username);

        System.out.println("Finishing testGetItemIntotal");
    }

    @Test
    public void testGetDeliveryFee() {
        System.out.println("\nStarting testGetDeliveryFee");
        double total = 5;
        assertEquals(total, shoppingCartManager.getDeliveryFee());
        System.out.println("Finishing testGetDeliveryFee");
    }

    @Test
    public void testTotalBeforeTax() {
        System.out.println("\nStarting testTotalBeforeTax");
        double total = 10;
        assertEquals(total + shoppingCartManager.getDeliveryFee(), shoppingCartManager.getTotalBeforeTax(total));
        System.out.println("Finishing testTotalBeforeTax");
    }

    @Test
    public void testGetGST() {
        System.out.println("\nStarting testGetGST");
        double total = 10;
        assertEquals(shoppingCartManager.getTotalBeforeTax(total) * 0.05, shoppingCartManager.getGST(total));
        System.out.println("Finishing testGetGST");
    }

    @Test
    public void testGetPST() {
        System.out.println("\nStarting testGetPST");
        double total = 10;
        assertEquals(shoppingCartManager.getTotalBeforeTax(total) * 0.08, shoppingCartManager.getPST(total));
        System.out.println("Finishing testGetPST");
    }

    @Test
    public void testGetOrderTotal() {
        System.out.println("\nStarting testGetOrderTotal");
        double total = 10;
        double expected = shoppingCartManager.getTotalBeforeTax(total) + shoppingCartManager.getGST(total) + shoppingCartManager.getPST(total);
        assertEquals(expected, shoppingCartManager.getOrderTotal(total));
        System.out.println("Finishing testGetOrderTotal");
    }



}
