package comp3350.bookworm.Objects.Exceptions;

public class InvalidAccountException extends Exception {
    public InvalidAccountException() {
        super("Null account");
    }
}
