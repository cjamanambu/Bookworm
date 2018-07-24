package comp3350.bookworm.Objects.Exceptions;

public class InvalidCredentialException extends Exception {

    public InvalidCredentialException() {
        super("Invalid username or password");
    }
}
