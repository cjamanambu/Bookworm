package comp3350.bookworm.Persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Persistence.ShoppingCartPersistence;

public class ShoppingCartPersistenceHSQLDB implements ShoppingCartPersistence {
    private final String dbPath;

    public ShoppingCartPersistenceHSQLDB(final String dbPath) {
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
    public List<Book> getShoppingCartForUser(String username) {
        List<String> bookNameList = new ArrayList<>();
        List<Book> bookList = new ArrayList<>();

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM ShoppingCart WHERE username = ?");
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
    public double getShoppingCartTotal(String username) {
        List<String> bookNameList = new ArrayList<>();
        List<Book> bookList = new ArrayList<>();
        Double total = 0.0;

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM ShoppingCart WHERE username = ?");
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

            for(Book b : bookList) {
                total += b.getBookPrice();
            }

            return total;
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean shoppingCartIsEmpty(String username) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM ShoppingCart WHERE username = ?");
            st.setString(1, username);
            final ResultSet rs = st.executeQuery();

            if(!rs.next()) {
                return true;
            }
            rs.close();
            st.close();

            return false;
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void clearShoppingCart(String username) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM ShoppingCart WHERE username = ?");
            st.setString(1, username);

            st.executeUpdate();
        }
        catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void addBookToUsersCart(String username, Book newBook) {
        try (final Connection c = connection()) {
            PreparedStatement st = c.prepareStatement("INSERT INTO ShoppingCart VALUES(NULL, ?, ?)");
            st.setString(1, username);
            st.setString(2, newBook.getBookName());

            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
