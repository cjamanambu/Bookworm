
package comp3350.bookworm.Persistence;

import java.util.List;

import comp3350.bookworm.Objects.Book;

public interface OrderHistoryPersistence {
    List<Book> getOrderHistoryForUser(String username);

    void addNewOrderedBooks(String username, List<Book> newBooks);
}
