package comp3350.bookworm.Persistence;

import java.util.List;

import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Review;

public interface ReviewPersistence {
    List<Review> getReviewsToShow( String bookName );

    void addNewReview( Book currentBook, Review newReview );
}
