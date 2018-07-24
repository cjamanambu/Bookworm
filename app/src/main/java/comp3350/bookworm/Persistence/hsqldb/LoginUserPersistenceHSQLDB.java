package comp3350.bookworm.Persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.bookworm.Persistence.LoginUserPersistence;

public class LoginUserPersistenceHSQLDB implements LoginUserPersistence {
    private final String dbPath;

    public LoginUserPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public String getUsername() {
        String username = "Invalid Username";

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT TOP 1 * FROM LoginAccount");
            if(rs.next())
                username = rs.getString("username");
            st.close();
            rs.close();

            return username;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void setUsername(String username) {
        try (final Connection c = connection()) {
            PreparedStatement st = c.prepareStatement("UPDATE LoginAccount " +
                    "SET username = ?, LoggedIn = 1  WHERE username in (SELECT TOP 1 username FROM LoginAccount)");
            st.setString(1, username);
            st.executeUpdate();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Boolean loggedIn() {
        int loggedin = 0;

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT TOP 1 * FROM LoginAccount");
            if(rs.next())
                loggedin = rs.getInt("LoggedIn");
            st.close();
            rs.close();

            return loggedin == 1;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void logout() {
        try (final Connection c = connection()) {
            PreparedStatement st = c.prepareStatement("UPDATE LoginAccount " +
                    "SET LoggedIn = 0 WHERE username = (SELECT TOP 1 Username FROM LoginAccount)");
            st.executeUpdate();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }


}
