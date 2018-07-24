package comp3350.bookworm.BusinessLogic.Validator;

import comp3350.bookworm.Objects.Review;

public class ReviewValidator {

    public boolean isValidReview(Review review) {
        return review != null && review.getRatingPoint() <= 5 && review.getRatingPoint() >= 0;
    }
}
