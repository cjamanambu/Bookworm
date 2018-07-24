package comp3350.bookworm.Persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Persistence.WishlistPersistence;

public class WishlistPersistenceHSQLDB implements WishlistPersistence {

    private final String dbPath;

    public WishlistPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Book fromResultSet(final ResultSet rs) throws SQLException {
        final String bookName = rs.getString("bookname");
        final String authorName = rs.getString("authorname");
        final String bookPreview = rs.getString("bookpreview");
        final String category = rs.getString("category");
        final double bookPrice = rs.getDouble("bookPrice");
        final double bookRating = rs.getDouble("bookRating");
        final int numSold = rs.getInt("numSold");
        return new Book(bookName, authorName, bookPreview, category, bookPrice, bookRating, numSold);
    }

    @Override
    public List<Book> getWishlistForUser(String username) {
        List<String> bookNameList = new ArrayList<>();
        List<Book> bookList = new ArrayList<>();

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM Wishlist WHERE username = ?");
            st.setString(1, username);
            final ResultSet rs = st.executeQuery();

            while(rs.next()) {
                bookNameList.add(rs.getString("bookname"));
            }
            rs.close();
            st.close();

            PreparedStatement st2;
            ResultSet rs2;
            for(int i = 0; i < bookNameList.size(); i++) {
                st2 = c.prepareStatement("SELECT * FROM Books WHERE bookname = ?");
                st2.setString(1, bookNameList.get(i));
                rs2 = st2.executeQuery();
                if(rs2.next())
                    bookList.add(fromResultSet(rs2));
                else {
                    rs2.close();
                    st2.close();
                }
            }
            return bookList;
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void addBookToWishlist(String username, List<Book> newBook) {
        try (final Connection c = connection()) {
            PreparedStatement st;
            for(Book b : newBook) {
                st = c.prepareStatement("INSERT INTO Wishlist VALUES(NULL, ?, ?)");
                st.setString(1, username);
                st.setString(2, b.getBookName());

                st.executeUpdate();
            }
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void clearWishlist(String user) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM Wishlist WHERE username = ?");
            st.setString(1, user);

            st.executeUpdate();
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
