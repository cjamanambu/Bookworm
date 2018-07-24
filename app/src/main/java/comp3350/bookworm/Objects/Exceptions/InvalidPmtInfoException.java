package comp3350.bookworm.Objects.Exceptions;

public class InvalidPmtInfoException extends Exception {
    public InvalidPmtInfoException(){
        super("Invalid Payment information");
    }
}
