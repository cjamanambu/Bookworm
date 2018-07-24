package comp3350.bookworm.Persistence;

import java.util.List;

import comp3350.bookworm.Objects.Book;

public class WishListPersistenceStub implements WishlistPersistence {
    @Override
    public List<Book> getWishlistForUser(String username) {
        return null;
    }

    @Override
    public void addBookToWishlist(String username, List<Book> newBook) {

    }

    @Override
    public void clearWishlist(String username) {

    }
}
