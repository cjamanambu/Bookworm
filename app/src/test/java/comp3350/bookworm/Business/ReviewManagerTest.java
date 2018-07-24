package comp3350.bookworm.Business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.BusinessLogic.ReviewManager;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.InvalidReviewException;
import comp3350.bookworm.Objects.Review;
import comp3350.bookworm.Persistence.ReviewPersistence;
import comp3350.bookworm.Persistence.ReviewPersistenceStub;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class);
public class ReviewManagerTest {
    private ReviewManager reviewManager;
    private ReviewManager reviewManagerMock;
    private ReviewPersistence reviewPersistence;

    @Before
    public void setup() {
        reviewPersistence = mock(ReviewPersistence.class);
        reviewManagerMock = new ReviewManager(reviewPersistence);
        reviewManager = new ReviewManager( new ReviewPersistenceStub() );
    }

    @After
    public void tearDown() {
        reviewManager = null;
    }

    @Test
    public void testAddValidReview() {
        System.out.println("\nStarting testAddValidReview.");

        String bookName = "basic test";
        Book book = new Book( bookName, "Author", "Preview", "test", 10.0, 0, 20);
        Review review = new Review("tester", 3, "I'd absolutely recommend this book to everyone");
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review);

        when(reviewPersistence.getReviewsToShow(bookName)).thenReturn(reviewList);

        try {
            reviewManagerMock.addReview(book, review);
        } catch (InvalidReviewException e) {
            fail("Exception not expected");
        }

        List<Review> reviewCommited = reviewManagerMock.getReviewList(bookName);
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

        verify(reviewPersistence).getReviewsToShow(bookName);
        verify(reviewPersistence).addNewReview(book, review);

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

        assertTrue(reviewManager.getReviewList(bookName) == null);

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
