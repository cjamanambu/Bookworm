package comp3350.bookworm.Persistence;

public class LoginUserPersistenceStub implements LoginUserPersistence {
    private static String username = "";
    private static Boolean loggedIn = null;

    public LoginUserPersistenceStub() {
        if(loggedIn == null)
            loggedIn = false;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
        loggedIn = true;
    }

    @Override
    public Boolean loggedIn() {
        return loggedIn;
    }

    @Override
    public void logout() {
        loggedIn = false;
    }
}
