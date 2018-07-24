package comp3350.bookworm.Persistence;

import java.util.List;

import comp3350.bookworm.Objects.Book;

public interface ShoppingCartPersistence {
    List<Book> getShoppingCartForUser(String username);
    void addBookToUsersCart(String username, Book newBook);
    double getShoppingCartTotal(String username);
    boolean shoppingCartIsEmpty(String username);
    void clearShoppingCart(String username);
}
