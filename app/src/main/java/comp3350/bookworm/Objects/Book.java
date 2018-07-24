package comp3350.bookworm.Objects;

public class Book implements Comparable {
    private final String bookName;
    private final String authorName;
    private final String bookPreview;
    private String category;
    private double bookPrice;
    private double bookRating;
    private int bookSoldNum;

    public Book( String bookName, String authorName, String bookPreview, String category, double bookPrice, double bookRating, int numSold) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookPreview = bookPreview;
        this.category = category;
        this.bookPrice = bookPrice;
        this.bookRating = bookRating;
        this.bookSoldNum = numSold;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() { return authorName; }

    public String getPreview() {
        return bookPreview;
    }

    public int getBookSoldNum(){ return bookSoldNum;}

    public double getBookPrice() {
        return bookPrice;
    }

    public double getBookRating() { return bookRating; }

    public String getCategory() {return category;}

    public double getHalfBookPrice() {
        return bookPrice / 2;
    }

    @Override
    public int compareTo (Object compareNumSold) {
        int compareCopiesSold=((Book)compareNumSold).getBookSoldNum();
        return (compareCopiesSold - this.bookSoldNum);
    }
}
