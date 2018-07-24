package comp3350.bookworm.BusinessLogic;

import java.util.List;

import comp3350.bookworm.Application.Service;
import comp3350.bookworm.BusinessLogic.Time.TimeProvider;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.InvalidAccountException;
import comp3350.bookworm.Objects.Exceptions.UserNotSignedInException;
import comp3350.bookworm.Persistence.LoginUserPersistence;
import comp3350.bookworm.Persistence.ShoppingCartPersistence;

public class ShoppingCartManager {
    private ShoppingCartPersistence shoppingCartPersistence;
    private LoginUserPersistence loginUserPersistence;
    private double DELIVERFEE = 5.0;
    private double GSTRATE = 0.05;
    private double PSTRATE = 0.08;

    public ShoppingCartManager() {
        shoppingCartPersistence = Service.getShoppingCartPersistence();
        loginUserPersistence = Service.getLoginUserPersistence();
    }

    public ShoppingCartManager(final ShoppingCartPersistence shoppingCartPersistence, final LoginUserPersistence loginUserPersistence) {
        this();
        this.shoppingCartPersistence = shoppingCartPersistence;
        this.loginUserPersistence = loginUserPersistence;
    }

    public void addBookToUsersCart(String currentUsername, Book newBook) throws UserNotSignedInException, InvalidAccountException {
        if(currentUsername.equals(""))
            throw new UserNotSignedInException();

        if(!loginUserPersistence.loggedIn())
            throw new InvalidAccountException();

        shoppingCartPersistence.addBookToUsersCart(currentUsername, newBook);
    }

    public List<Book> getShoppingCartForCurrentUser(String currentUsername){
        return shoppingCartPersistence.getShoppingCartForUser(currentUsername);
    }

    public String getShoppingCartTotal(String currentUsername){
        return Double.toString(shoppingCartPersistence.getShoppingCartTotal(currentUsername));
    }

    public boolean shoppingCartIsEmpty(String currentUsername){
        return shoppingCartPersistence.shoppingCartIsEmpty(currentUsername);
    }

    public void clearShoppingCart(String currentUsername){
        shoppingCartPersistence.clearShoppingCart(currentUsername);
    }

    public double getItemTotal(TimeProvider timeProvider){
        // check half-price day
        double totalInFigures = shoppingCartPersistence.getShoppingCartTotal(loginUserPersistence.getUsername());


        if(timeProvider.isHalfPriceDay())
            totalInFigures /= 2;
        return totalInFigures;
    }

    public double getDeliveryFee(){
        return DELIVERFEE;
    }

    public double getTotalBeforeTax(double itemTotal){
        return itemTotal + getDeliveryFee();
    }

    public double getGST(double itemTotal){
        return getTotalBeforeTax(itemTotal) * GSTRATE;
    }

    public double getPST(double itemTotal){
        return getTotalBeforeTax(itemTotal) * PSTRATE;
    }

    public double getOrderTotal(double itemTotal){
        return getTotalBeforeTax(itemTotal) + getGST(itemTotal) + getPST(itemTotal);
    }
}
