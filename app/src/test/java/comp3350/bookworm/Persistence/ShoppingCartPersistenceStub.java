package comp3350.bookworm.Persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Book;

public class ShoppingCartPersistenceStub implements ShoppingCartPersistence {
    private Map<String, List<Book>> shoppingCarts;

    public ShoppingCartPersistenceStub(){
        List<Account> accounts = new AccountPersistenceStub().getAllAccounts();
        List<Book> carts = new ArrayList<>();

        shoppingCarts = new HashMap<>();
        for(Account account : accounts)
            shoppingCarts.put(account.getUserName(), carts);
    }

    @Override
    public List<Book> getShoppingCartForUser(String username) {
        return shoppingCarts.get(username);
    }

    @Override
    public void addBookToUsersCart(String currentUsername, Book newBook) {
        List<Book> cart = shoppingCarts.get(currentUsername);
        if(cart != null){
            cart.add(newBook);
        }
        else {
            List<Book> newCart = new ArrayList<>();
            newCart.add(newBook);
            shoppingCarts.put(currentUsername, newCart);
        }

    }

    @Override
    public double getShoppingCartTotal(String currentUsername){
        double total = 0.0;
        List<Book> cart = shoppingCarts.get(currentUsername);
        for(Book book : cart)
            total += book.getBookPrice();
        return total;
    }

    @Override
    public boolean shoppingCartIsEmpty(String currentUsername){
        List<Book> cart = shoppingCarts.get(currentUsername);
        return cart.isEmpty();
    }

//    @Override
//    public int getNumberOfItemsInCart(String currentUsername){
//        List<Book> cart = shoppingCarts.get(currentUsername);
//        return cart.size();
//    }

    @Override
    public void clearShoppingCart(String currentUsername){
        List<Book> cart = shoppingCarts.get(currentUsername);
        cart.clear();
    }
}
