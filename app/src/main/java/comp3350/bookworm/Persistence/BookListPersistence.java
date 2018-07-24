
package comp3350.bookworm.Persistence;

import java.util.List;

import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.BookNotFoundException;
import comp3350.bookworm.Objects.Exceptions.DuplicateBookException;
import comp3350.bookworm.Objects.Exceptions.InvalidBookException;

public interface BookListPersistence {
    void insertBook(Book book) throws InvalidBookException, DuplicateBookException;
    Book getBook(String bookName) throws BookNotFoundException;
    void deleteBook(String bookName) throws BookNotFoundException;
    List<Book> getBookList();
}
