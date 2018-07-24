package comp3350.bookworm.BusinessLogic;

import java.util.List;

import comp3350.bookworm.Application.Service;
import comp3350.bookworm.BusinessLogic.Validator.ReviewValidator;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.InvalidReviewException;
import comp3350.bookworm.Objects.Review;
import comp3350.bookworm.Persistence.ReviewPersistence;

public class ReviewManager {
    private ReviewPersistence reviewPersistence;
    private ReviewValidator reviewValidator;

    public ReviewManager() {
        reviewPersistence = Service.getReviewPersistence();
        reviewValidator = new ReviewValidator();
    }

    public ReviewManager(ReviewPersistence reviewPersistence) {
        this();
        this.reviewPersistence = reviewPersistence;
    }

    public void addReview( Book book, Review reviewToAdd ) throws InvalidReviewException {
        if (!reviewValidator.isValidReview(reviewToAdd))
            throw new InvalidReviewException();
        reviewPersistence.addNewReview(book, reviewToAdd);
    }

    public List <Review> getReviewList( String bookName ) {
        return reviewPersistence.getReviewsToShow( bookName );
    }

    public double getBookRating( String bookName ) {
        double meanRating = 0;
        List <Review> reviewList = reviewPersistence.getReviewsToShow( bookName );
        for ( int index=0; index<reviewList.size(); index++ ) {
            meanRating += (reviewList.get(index)).getRatingPoint();
        }

        meanRating = meanRating/reviewList.size();

        return meanRating;
    }
}
