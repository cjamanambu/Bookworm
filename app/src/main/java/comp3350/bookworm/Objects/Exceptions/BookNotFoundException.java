package comp3350.bookworm.Objects.Exceptions;

public class BookNotFoundException extends Exception {

    public BookNotFoundException()
    {
        super("Book not found");
    }
}
