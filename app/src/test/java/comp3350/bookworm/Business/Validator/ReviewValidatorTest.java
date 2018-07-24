package comp3350.bookworm.Business.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.bookworm.BusinessLogic.Validator.ReviewValidator;
import comp3350.bookworm.Objects.Review;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ReviewValidatorTest {
    private ReviewValidator reviewValidator;

    @Before
    public void setup() {
        reviewValidator = new ReviewValidator();
    }

    @After
    public void tearDown() {
        reviewValidator = null;
    }

    @Test
    public void testReviewValidator() {
        System.out.println("\nStarting testReviewValidator");

        Review invalidReview = new Review("", 100, "");
        Review validReview = new Review("Harry", 4, "love this BBBBBBOOOOOOOOKKKKKKKKKKK!!!!!!!!!");

        assertFalse(reviewValidator.isValidReview(invalidReview));
        assertTrue(reviewValidator.isValidReview(validReview));

        System.out.println("Finishing testReviewValidator");
    }
}
