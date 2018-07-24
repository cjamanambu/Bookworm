package comp3350.bookworm.Persistence;

import java.util.List;

import comp3350.bookworm.Objects.Book;

public interface WishlistPersistence {
    List<Book> getWishlistForUser(String username);
    void addBookToWishlist(String username, List<Book> newBook);
    void clearWishlist(String username);
}
