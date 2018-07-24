
package comp3350.bookworm.Application;

import comp3350.bookworm.Persistence.AccountPersistence;
import comp3350.bookworm.Persistence.BookListPersistence;
import comp3350.bookworm.Persistence.LoginUserPersistence;
import comp3350.bookworm.Persistence.OrderHistoryPersistence;
import comp3350.bookworm.Persistence.ReviewPersistence;
import comp3350.bookworm.Persistence.ShoppingCartPersistence;
import comp3350.bookworm.Persistence.WishlistPersistence;
import comp3350.bookworm.Persistence.hsqldb.AccountPersistenceHSQLDB;
import comp3350.bookworm.Persistence.hsqldb.BookListPersistenceHSQLDB;
import comp3350.bookworm.Persistence.hsqldb.LoginUserPersistenceHSQLDB;
import comp3350.bookworm.Persistence.hsqldb.OrderHistoryPersistenceHSQLDB;
import comp3350.bookworm.Persistence.hsqldb.ReviewPersistenceHSQLDB;
import comp3350.bookworm.Persistence.hsqldb.ShoppingCartPersistenceHSQLDB;
import comp3350.bookworm.Persistence.hsqldb.WishlistPersistenceHSQLDB;

public class Service {
    private static AccountPersistence accountPersistence = null;
    private static BookListPersistence bookListPersistence = null;
    private static LoginUserPersistence loginUserPersistence = null;
    private static OrderHistoryPersistence orderHistoryPersistence = null;
    private static ShoppingCartPersistence shoppingCartPersistence = null;
    private static ReviewPersistence reviewPersistence = null;
    private static WishlistPersistence wishlistPersistence = null;

    public static synchronized AccountPersistence getAccountPersistence()
    {
        if (accountPersistence == null) {
            //accountPersistence = new AccountPersistenceStub();
            //accountPersistence = new AccountPersistenceHSQLDB(Main.getDBPathName());
//            accountPersistence = new AccountPersistenceStub();
            accountPersistence = new AccountPersistenceHSQLDB(Main.getDBPathName());
        }
        return accountPersistence;
    }
    public static synchronized BookListPersistence getBookListPersistence()
    {
        if(bookListPersistence == null) {
           //bookListPersistence = new BookListPersistenceStub();
            bookListPersistence = new BookListPersistenceHSQLDB(Main.getDBPathName());
        }
        return bookListPersistence;
    }
    public static synchronized OrderHistoryPersistence getOrderHistoryPersistence()
    {
        if(orderHistoryPersistence == null) {
//            orderHistoryPersistence = new OrderHistoryPersistenceStub();
            orderHistoryPersistence = new OrderHistoryPersistenceHSQLDB(Main.getDBPathName());
        }
        return orderHistoryPersistence;
    }

    public static synchronized LoginUserPersistence getLoginUserPersistence() {
        if(loginUserPersistence == null) {
//            loginUserPersistence = new LoginUserPersistenceStub();
            loginUserPersistence = new LoginUserPersistenceHSQLDB(Main.getDBPathName());
        }
        return loginUserPersistence;
    }

    public static synchronized ShoppingCartPersistence getShoppingCartPersistence() {
        if(shoppingCartPersistence == null) {
//            shoppingCartPersistence = new ShoppingCartPersistenceStub();
            shoppingCartPersistence = new ShoppingCartPersistenceHSQLDB(Main.getDBPathName());
        }
        return shoppingCartPersistence;
    }

    public static synchronized ReviewPersistence getReviewPersistence() {
        if ( reviewPersistence == null ) {
//            reviewPersistence = new ReviewPersistenceStub();
            reviewPersistence = new ReviewPersistenceHSQLDB( Main.getDBPathName() );
        }
        return reviewPersistence;
    }

    public static synchronized WishlistPersistence getWishlistPersistence()
    {
        if(wishlistPersistence == null) {
            wishlistPersistence = new WishlistPersistenceHSQLDB(Main.getDBPathName());
        }
        return wishlistPersistence;
    }

}

