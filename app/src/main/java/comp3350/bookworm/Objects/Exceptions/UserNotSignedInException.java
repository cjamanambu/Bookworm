package comp3350.bookworm.Objects.Exceptions;

public class UserNotSignedInException extends Exception {
    public UserNotSignedInException(){
        super("User is not signed in");
    }
}
