package comp3350.bookworm.Persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Review;
import comp3350.bookworm.Persistence.ReviewPersistence;

public class ReviewPersistenceHSQLDB implements ReviewPersistence {
    private final String dbPath;

    public ReviewPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Review fromResultSet(final ResultSet rs) throws SQLException {
        final String writer = rs.getString("WRITER");
        final String content = rs.getString("CONTENT");
        final int rating = rs.getInt("RATING");
        return new Review(writer, rating, content);
    }

    @Override
    public List<Review> getReviewsToShow(String bookName) {
        List<Review> reviewList = new ArrayList<>();

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM REVIEW WHERE BOOKNAME = ?");
            st.setString(1, bookName);
            final ResultSet rs = st.executeQuery();

            while(rs.next()) {
                reviewList.add(fromResultSet(rs));
            }
            rs.close();
            st.close();

            return reviewList;
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void addNewReview(Book currentBook, Review newReview) {
        try (final Connection c = connection()) {
            PreparedStatement st = c.prepareStatement("INSERT INTO REVIEW VALUES(NULL, ?, ?, ?, ?)");
            st.setString(1, newReview.getReviewWriter());
            st.setString(2, currentBook.getBookName());
            st.setInt(3, newReview.getRatingPoint());
            st.setString(4, newReview.getContent());

            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }
}
