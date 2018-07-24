package comp3350.bookworm.Objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void testCreatingBook() {
        Book book;

        System.out.println( "\nStarting test for creating a Book object.\n" );
        System.out.println( "Creating an object of Book\n" +
                "Book Title: A course in probability\n" +
                "Author Name: Neil A. Weiss\n" +
                "Preview: preview content" +
                "Price: " + 110.66 +
                "Rating: " + 4.5 +
                "Number of sell: " + 0 );

        book = new Book( "A course in probability", "Neil A. Weiss", "preview content", "math", 110.66, 4.5, 0);

        assertNotNull( book );

        assertTrue( "A course in probability".equals( book.getBookName() ) );
        assertTrue( "Neil A. Weiss".equals( book.getAuthorName() ) );
        assertTrue( "preview content".equals( book.getPreview() ) );
        assertTrue( 110.66 == book.getBookPrice() );
        assertTrue( 4.5 == book.getBookRating() );
        assertTrue( 0 == book.getBookSoldNum() );

        System.out.println( "\nFinished testBook." );

    }

    @Test
    public void testBookCompare() {
        System.out.println( "\nStarting test Book compare.\n" );
        Book book1, book2;

        book1 = new Book( "A course in probability", "Neil A. Weiss", "preview content", "math", 110.66, 4.5, 0);
        book2 = new Book( "A course in probability", "Neil A. Weiss", "preview content", "math", 110.66, 4.5, 5);

        assertEquals(book1.compareTo(book1), 0);
        assertEquals(book1.compareTo(book2), 5);

        System.out.println( "\nFinished test Book compare." );

    }

    @Test
    public void testHalfPrice() {
        System.out.println( "\nStarting test half-price.\n" );
        Book book;
        double price = 10;

        book = new Book( "A course in probability", "Neil A. Weiss", "preview content", "math", price, 4.5, 0);

        assertEquals((int)book.getHalfBookPrice(), 5);


        System.out.println( "\nFinished test half-price." );
    }

}
