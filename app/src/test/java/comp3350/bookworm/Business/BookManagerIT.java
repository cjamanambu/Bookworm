package comp3350.bookworm.Business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.BusinessLogic.BookManager;
import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.BookNotFoundException;
import comp3350.bookworm.Objects.Exceptions.DuplicateBookException;
import comp3350.bookworm.Objects.Exceptions.DuplicateUsernameException;
import comp3350.bookworm.Objects.Exceptions.InvalidAccountException;
import comp3350.bookworm.Objects.Exceptions.InvalidBookException;
import comp3350.bookworm.Objects.Exceptions.InvalidCredentialException;
import comp3350.bookworm.Objects.Exceptions.InvalidEmailAddressException;
import comp3350.bookworm.utils.TestUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class BookManagerIT {
    private BookManager bookManager;
    private AccountManager accountManager;

    @Before
    public void setup() throws IOException {
        TestUtils.copyDB();
        accountManager = new AccountManager();
        bookManager = new BookManager();
    }

    @After
    public void tearDown() {
        TestUtils.delete();
        bookManager = null;
        accountManager = null;
    }

    @Test
    public void testGetBookList() {
        System.out.println("\nStarting test get BookList");

        List<Book> booklist = bookManager.getBookList();

        assertNotNull(booklist);

        System.out.println("\nFinishing test get BookList");
    }

    @Test
    public void testValidSearchBook() {
        System.out.println("\nStarting test valid searchBook");
        Book bookToTest = null;
        try {
            bookToTest = bookManager.searchBook("Java");
        }
        catch (BookNotFoundException e) {
            fail("Exception not expected");
        }
        assertTrue(bookToTest.getBookName().equals("Java"));

        System.out.println("Finishing test valid searchBook");
    }

    @Test
    public void testInvalidSearchBook() {
        System.out.println("\nStarting test invalid searchBook");
        Book bookToTest = null;

        try {
            bookToTest = bookManager.searchBook("");
            fail("Failed expected");
        }
        catch (BookNotFoundException e) {

        }
        assertTrue(bookToTest == null);

        System.out.println("Finishing test invalid searchBook");
    }

    @Test
    public void testValidAddBook()
    {
        System.out.println("\nStarting test valid Book add");
        Book bookToAdd = new Book("Bengali", "Shamoresh","Bangali book", "10.0",5.0, 5, 0);

        try {
            bookManager.addBook(bookToAdd);
        }
        catch (DuplicateBookException e) {
            fail("Exception not expected");
        }
        catch (InvalidBookException e) {
            fail("Exception not expected");
        }

        Book bookFound = null;

        try{
            bookFound = bookManager.searchBook("Bengali");
            assertTrue(bookFound.getBookName().equals("Bengali"));
            bookManager.deleteBook("Bengali");
        }
        catch (BookNotFoundException e)
        {
            fail("Exception not expected");
        }

        System.out.println("Finishing test valid Book add");
    }

    @Test
    public void testInvalidAddBook()
    {
        System.out.println("\nStarting test invalid Book add");
        Book bookToAdd = new Book("", "Shamoresh","Bangali book", "10.0",5.0, 5, 0);

        try {
            bookManager.addBook(bookToAdd);
            fail("Failed expected");
        }
        catch (DuplicateBookException e) {
            fail("Exception not expected");
        }
        catch (InvalidBookException e) {

        }

        Book bookFound = null;

        try{
            bookFound = bookManager.searchBook("");
            fail("Failed expected");
        }
        catch (BookNotFoundException e) {

        }
        assertTrue(bookFound == null);

        System.out.println("Finishing test invalid Book add");
    }

    @Test
    public void testInvalidAddBookDuplicate()
    {
        System.out.println("\nStarting test invalid Book add duplicate");
        Book bookToAdd = new Book("duplicate book", "Shamoresh","Bangali book", "10.0",5.0, 5, 0);

        try {
            bookManager.addBook(bookToAdd);
        }
        catch (DuplicateBookException e) {
            fail("Exception not expected");
        }
        catch (InvalidBookException e) {
            fail("Exception not expected");
        }

        try {
            bookManager.addBook(bookToAdd);
            fail("Failed expected");
        }
        catch (DuplicateBookException e) {

        }
        catch (InvalidBookException e) {
            fail("Exception not expected");
        }

        System.out.println("Finishing test invalid Book add duplicate");
    }

    @Test
    public void testDeleteBook()
    {
        System.out.println("\nStarting test delete Book");
        Book bookToAdd = new Book("Game of thrones", "Shamoresh","Bangali book", "10.0",5.0, 5, 0);

        try {
            bookManager.addBook(bookToAdd);
        }
        catch (DuplicateBookException e) {
            fail("Exception not expected");
        }
        catch (InvalidBookException e) {
            fail("Exception not expected");
        }

        Book bookFound = null;

        try{
            bookManager.deleteBook("Game of thrones");
            bookFound = bookManager.searchBook("Game of thrones");
            fail("Failed expected");
        }
        catch (BookNotFoundException e) {

        }

        assertTrue(bookFound == null);

        System.out.println("Finishing test delete Book");
    }


    @Test
    public void testBestSeller()
    {
        System.out.println("\nStarting test Best Seller list sort");
        List<Book> bestSellerList = new ArrayList<Book>();

        try{
            bestSellerList = bookManager.getBestSellerList();
        }
        catch (Exception e)
        {
            fail("Exception not expected");
        }

        for(int i = 0; i < bestSellerList.size()-1; i++) {
            assertTrue(bestSellerList.get(i).getBookSoldNum() >= bestSellerList.get(i + 1).getBookSoldNum());
        }

        System.out.println("Finishing test Best Seller list sort");
    }

    @Test
    public void testGetSuggestedBooks(){
        System.out.println("\nStarting test get Suggested Books list");

        try{
            accountManager.login(new Account("admin", "admin"));
            List<Book> suggestedBooks = bookManager.getSuggestedBooks();
            List<Book> userOrderHist = bookManager.getOrderHistoryForCurrentUser();
            List<String> orderHistGenre = new ArrayList<>();
            List<String> orderHistAuthors = new ArrayList<>();

            //populate the order hist author list from account order history to check against suggested books.
            for(int y = 0; y < userOrderHist.size()-1; y++){
                if(!(orderHistAuthors.contains(userOrderHist.get(y).getAuthorName()))){
                    orderHistAuthors.add(userOrderHist.get(y).getAuthorName());
                }
            }
            //populate the order hist genre list from account order history to check against suggested books.
            for(int z = 0; z < userOrderHist.size()-1; z++){
                if(!(orderHistGenre.contains(userOrderHist.get(z).getCategory()))){
                    orderHistGenre.add(userOrderHist.get(z).getCategory());
                }
            }

            for(int x = 0; x < suggestedBooks.size()-1; x++){
                if(orderHistGenre.contains(suggestedBooks.get(x).getCategory()) || orderHistAuthors.contains(suggestedBooks.get(x).getAuthorName())){
                    //books should be here, everything passes.
                }else{
                    fail("Unwarrented book in suggested list");
                }
            }
        }
        catch (InvalidAccountException e) {
            fail("Exception not expected, InvalidAccountException");
        }
        catch (BookNotFoundException e) {
            fail("Exception not expected, BookNotFoundException");
        }
        catch (InvalidCredentialException e){
            fail("Exception not expected, InvalidCredentialException");
        }
        catch (Exception e){
            fail("Exception not expected");
        }
        System.out.println("\nFinishing test get Suggested Books list");
    }

    @Test
    public void testInvalidGetOrderHistoryNoLogin() {
        System.out.println("\nStarting test invalid get order history no login");

        List<Book> booklist = null;
        accountManager.logout();

        try {
            booklist = bookManager.getOrderHistoryForCurrentUser();
            fail("Fail expected");
        }
        catch (InvalidAccountException e) {

        }
        catch (BookNotFoundException e) {
            fail("Exception not expected");
        }
        assertNull(booklist);
        System.out.println("\nFinishing test invalid get order history no login");
    }

    @Test
    public void testGetOrderHistoryNoHistory() {
        System.out.println("\nStarting test get order history when there is no history");

        List<Book> booklist = null;
        String username = "new account";
        String password = "new password";
        String email = "new@bookworm.ca";
        Account newAccount = new Account(username, password, email);

        try {
            accountManager.singup(newAccount);
            accountManager.login(newAccount);
            booklist = bookManager.getOrderHistoryForCurrentUser();
            fail("Fail expected");
        }
        catch (InvalidAccountException e) {
            fail("Exception not expected");
        }
        catch (DuplicateUsernameException e){
            fail("Exception not expected");
        }
        catch (InvalidCredentialException e) {
            fail("Exception not expected");
        }
        catch (InvalidEmailAddressException e) {
            fail("Exception not expected");
        }
        catch (BookNotFoundException e) {

        }
        assertNull(booklist);
        accountManager.logout();
        System.out.println("\nFinishing test get order history when this is no history");
    }

    @Test
    public void testValidGetOrderHistory() {
        System.out.println("\nStarting test valid get order history");

        List<Book> booklist = null;
        String username = "admin";
        String password = "admin";

        try {
            accountManager.login(new Account(username, password));
            booklist = bookManager.getOrderHistoryForCurrentUser();
        }
        catch (InvalidCredentialException e) {
            fail("Exception not expected");
        }
        catch (InvalidAccountException e) {
            fail("Exception not expected");
        }
        catch (BookNotFoundException e) {
            fail("Exception not expected");
        }
        assertNotNull(booklist);
        System.out.println("\nFinishing test valid get order history");
    }

    @Test
    public void testAddOrderHistory() {
        System.out.println("\nStarting testAddOrderHistory");

        String username = "admin";
        Book book = new Book("C++", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 1);
        List<Book> list = new ArrayList<>();
        list.add(book);
        bookManager.addBookToOrderHistory(username, list);

        System.out.println("\nFinishing testAddOrderHistory");
    }

    @Test
    public void testGetWishList() {
        System.out.println("\nStarting testGetWishList");

        String username = "admin";
        String password = "admin";
        Book book = new Book("C++", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 1);
        List<Book> list = new ArrayList<>();
        list.add(book);
        List<Book> returnList = new ArrayList<>();
        try {
            accountManager.login(new Account(username, password));
            bookManager.clearWishlist(username);
            bookManager.addBookToWishlist(username, list);
            returnList = bookManager.getWishlist();
        } catch (InvalidAccountException e) {
            fail("Exception not expected");
        } catch (BookNotFoundException e) {
            fail("Exception not expected");
        } catch (InvalidCredentialException e) {
            e.printStackTrace();
        }
        assertEquals(book.getBookName(), returnList.get(0).getBookName());

        System.out.println("\nFinishing testGetWishList");
    }

    @Test
    public void testAddWishList() {
        System.out.println("\nStarting testAddWishList");

        String username = "admin";
        Book book = new Book("C++", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 1);
        List<Book> list = new ArrayList<>();
        list.add(book);
        bookManager.addBookToWishlist(username, list);
        System.out.println("\nFinishing testAddWishList");
    }

    @Test
    public void testClearWishList() {
        System.out.println("\nStarting testClearWishList");

        String username = "admin";
        String password = "admin";
        List<Book> returnList = new ArrayList<>();
        try {
            accountManager.login(new Account(username, password));
            bookManager.clearWishlist(username);
            returnList = bookManager.getWishlist();
        } catch (InvalidAccountException e) {
            fail("Exception not expected");
        } catch (BookNotFoundException e) {
            // Should trigger this exception
        } catch (InvalidCredentialException e) {
            fail("Exception not expected");
        }

        System.out.println("\nFinishing testClearWishList");
    }
}
