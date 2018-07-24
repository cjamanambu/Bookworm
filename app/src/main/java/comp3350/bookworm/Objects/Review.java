package comp3350.bookworm.Objects;

public class Review {
    private final String reviewWriter;
    private final int ratingPoint;
    private final String content;

    public Review( String rWriter, int rating, String content ) {
        reviewWriter = rWriter;
        ratingPoint = rating;
        this.content = content;
    }

    public String getReviewWriter() { return reviewWriter; }

    public int getRatingPoint() { return ratingPoint; }

    public String getContent() { return content; }

}
