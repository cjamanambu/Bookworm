package comp3350.bookworm.Persistence;


public interface LoginUserPersistence {

    String getUsername();

    void setUsername(String username);

    Boolean loggedIn();

    void logout();
}
