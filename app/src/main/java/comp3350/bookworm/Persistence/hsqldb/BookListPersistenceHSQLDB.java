package comp3350.bookworm.Persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.DuplicateBookException;
import comp3350.bookworm.Persistence.BookListPersistence;

public class BookListPersistenceHSQLDB implements BookListPersistence {
    private final String dbPath;

    public BookListPersistenceHSQLDB(final String dbPath) {
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
    public void insertBook(Book book) throws DuplicateBookException {
        try (final Connection c = connection()) {
            PreparedStatement st = c.prepareStatement("INSERT INTO BOOKS VALUES(?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, book.getBookName());
            st.setString(2, book.getAuthorName());
            st.setString(3, book.getPreview());
            st.setString(4, book.getCategory());
            st.setDouble(5, book.getBookPrice());
            st.setDouble(6, book.getBookRating());
            st.setInt(7, book.getBookSoldNum());

            st.executeUpdate();
        } catch (final SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException)
                throw new DuplicateBookException();
            else
                throw new PersistenceException(e);
        }
    }

    @Override
    public Book getBook(String bookName) {
        Book book = null;

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM Books WHERE bookname = ?");
            st.setString(1, bookName);
            final ResultSet rs = st.executeQuery();
            if(rs.next())
                book = fromResultSet(rs);
            rs.close();
            st.close();

            return book;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteBook(String bookName) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM Books WHERE bookname = ?");
            st.setString(1, bookName);
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Book> getBookList() {
        final List<Book> books = new ArrayList<>();

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM Books");
            while (rs.next()) {
                final Book book = fromResultSet(rs);
                books.add(book);
            }
            rs.close();
            st.close();

            return books;
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
