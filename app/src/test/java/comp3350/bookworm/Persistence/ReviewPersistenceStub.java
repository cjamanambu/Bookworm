package comp3350.bookworm.Persistence;

import comp3350.bookworm.Objects.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp3350.bookworm.Objects.Book;

public class ReviewPersistenceStub implements ReviewPersistence {
    private Map <String, List<Review>> reviews;

    public ReviewPersistenceStub() {
        List <Book> bookLists = new BookListPersistenceStub().getBookList();

        List <Review> reviewList = new ArrayList <>();

        reviewList.add( new Review("Harry", 4, "love this BBBBBBOOOOOOOOKKKKKKKKKKK!!!!!!!!!") );

        reviews = new HashMap <>();
        for ( Book b : bookLists )
            reviews.put( b.getBookName(), reviewList );

    }

    @Override
    public List <Review> getReviewsToShow( String bookName ) {
        return reviews.get( bookName );
    }

    @Override
    public void addNewReview( Book currentBook, Review newReview ) {
        List <Review> reviewList = reviews.get( currentBook.getBookName() );

        if ( reviewList != null ) {
            reviewList.add(newReview);
        } else {
            List <Review> newReviewList = new ArrayList <>();
            newReviewList.add( newReview );
            reviews.put( currentBook.getBookName(), newReviewList );
        }
    }
}
