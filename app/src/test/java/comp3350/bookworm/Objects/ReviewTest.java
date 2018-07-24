package comp3350.bookworm.Objects;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewTest {

    @Test
    public void testCreatingReview() {
        Review review;

        System.out.println( "\nStarting test for creating a Review object.\n" );

        review = new Review( "tester", 3,"blah blah" );

        assertNotNull( review );

        assertTrue( "tester".equals( review.getReviewWriter() ) );
        assertTrue( 3 == review.getRatingPoint() );
        assertTrue( "blah blah".equals( review.getContent() ) );

        System.out.println( "\nFinished test for creating a Review object." );
    }
}
