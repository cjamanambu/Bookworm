package comp3350.bookworm.Business.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.BusinessLogic.Validator.BookValidator;
import comp3350.bookworm.Objects.Book;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class BookValidatorTest {
    private BookValidator bookValidator;

    @Before
    public void setup() {
        bookValidator = new BookValidator();
    }

    @After
    public void tearDown() {
        bookValidator = null;
    }

    @Test
    public void testBookValidator() {
        System.out.println("\nStarting testBookValidator");

        Book invalidBook = null;
        Book invalidBook2 = new Book("", "","","",10,3,2);
        Book validBook = new Book("Harry Potter and the Evil Potato", "Daniel J. Fung", "Fantasy book", "Fantasy", 10.0, 5.0, 11);

        assertFalse(bookValidator.isValidBook(invalidBook));
        assertFalse(bookValidator.isValidBook(invalidBook2));
        assertTrue(bookValidator.isValidBook(validBook));

        System.out.println("Finishing testBookValidator");
    }

    @Test
    public void testBookListValidator() {
        System.out.println("\nStarting testBookListValidator");

        List<Book> invalidList = null;
        List<Book> invalidList2 = new ArrayList<>();
        List<Book> validList = new ArrayList<>();
        validList.add(new Book("How to Fix an ID10T error Vol 1", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 10));
        validList.add(new Book("Harry Potter and the Evil Potato", "Daniel J. Fung", "Fantasy book", "Fantasy", 10.0, 5.0, 11));

        assertFalse(bookValidator.isValidBookList(invalidList));
        assertFalse(bookValidator.isValidBookList(invalidList2));
        assertTrue(bookValidator.isValidBookList(validList));

        System.out.println("Finishing testBookListValidator");
    }
}
