package comp3350.bookworm.Objects.Exceptions;

public class InvalidEmailAddressException extends Exception {

    public InvalidEmailAddressException() {
        super("Invalid email address");
    }
}
