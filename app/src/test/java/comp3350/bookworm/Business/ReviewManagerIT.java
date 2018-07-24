package comp3350.bookworm.Business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import comp3350.bookworm.BusinessLogic.ReviewManager;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.InvalidReviewException;
import comp3350.bookworm.Objects.Review;
import comp3350.bookworm.utils.TestUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class ReviewManagerIT {
    private ReviewManager reviewManager;

    @Before
    public void setUp() throws IOException {
        TestUtils.copyDB();
        reviewManager = new ReviewManager();
    }

    @After
    public void tearDown() {
        TestUtils.delete();
        reviewManager = null;
    }

    @Test
    public void testAddValidReview() {
        System.out.println("\nStarting testAddValidReview.");

        String bookName = "basic test";
        Book book = new Book( bookName, "Author", "Preview", "test", 10.0, 0, 20);
        Review review = new Review("tester", 3, "I'd absolutely recommend this book to everyone");

        try {
            reviewManager.addReview(book, review);
        } catch (InvalidReviewException e) {
            fail("Exception not expected");
        }

        List<Review> reviewCommited = reviewManager.getReviewList(bookName);
        Review reviewJustMade = null;
        for(Review r : reviewCommited) {
            if(r.getReviewWriter().equals("tester")) {
                reviewJustMade = r;
            }
        }
        assertTrue(reviewJustMade != null);
        assertEquals("I'd absolutely recommend this book to everyone", reviewJustMade.getContent());
        assertEquals(3, reviewJustMade.getRatingPoint());
        assertFalse(reviewCommited.isEmpty());

        System.out.println("Finishing testAddValidReview.");
    }

    @Test
    public void testAddInvalidReview() {
        System.out.println("\nStarting testAddInvalidReview.");

        String bookName = "0 <= rating point <= 5";

        Book book = new Book(bookName, "Author", "Privew", "test", 10.0, 0, 20);

        Review review = new Review("tester", 100, "N/A");

        try {
            reviewManager.addReview( book, review );
            fail( "Exception expected." );
        } catch (InvalidReviewException e) {}

        assertTrue(reviewManager.getReviewList(bookName).isEmpty());

        System.out.println("\nFinishing testAddInvalidReview.");
    }

    @Test
    public void testGetBookRating() {
        System.out.println("\nStarting testGetBookRating.");

        String bookName = "rating point test";

        Book book = new Book(bookName, "Author", "Preview", "test", 10.0, 0,20);

        int rate1 = 1;
        int rate2 = 2;
        int rate3 = 3;
        double avgRate = (rate1 + rate2 + rate3) / 3;
        double rateFromManager = 0;

        Review review1 = new Review("tester1", rate1, "n/a");
        Review review2 = new Review("tester2", rate2, "n/a");
        Review review3 = new Review("tester3", rate3, "n/a");

        try {
            reviewManager.addReview(book, review1);
            reviewManager.addReview(book, review2);
            reviewManager.addReview(book, review3);

            rateFromManager = reviewManager.getBookRating(bookName);

        } catch (InvalidReviewException e) { fail("Exception not expected."); }

        assertTrue(rateFromManager == avgRate);

        System.out.println("\nFinishing testGetBookRating.");
    }
}
