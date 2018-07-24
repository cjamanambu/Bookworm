package comp3350.bookworm.Objects.Exceptions;

public class DuplicateUsernameException extends Exception {

    public DuplicateUsernameException() {
        super("Username already exists");
    }
}
