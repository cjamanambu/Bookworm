package comp3350.bookworm.BusinessLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.bookworm.Application.Service;
import comp3350.bookworm.BusinessLogic.Validator.BookValidator;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.BookNotFoundException;
import comp3350.bookworm.Objects.Exceptions.DuplicateBookException;
import comp3350.bookworm.Objects.Exceptions.InvalidAccountException;
import comp3350.bookworm.Objects.Exceptions.InvalidBookException;
import comp3350.bookworm.Persistence.BookListPersistence;
import comp3350.bookworm.Persistence.OrderHistoryPersistence;
import comp3350.bookworm.Persistence.ShoppingCartPersistence;
import comp3350.bookworm.Persistence.WishlistPersistence;


public class BookManager {
    private BookListPersistence bookListPersistence;
    private OrderHistoryPersistence orderHistoryPersistence;
    private WishlistPersistence wishlistPersistence ;

    private ShoppingCartPersistence shoppingCartPersistence;
    private AccountManager accountManager;
    private BookValidator bookValidator;


    public BookManager(){
        accountManager = new AccountManager();
        bookListPersistence = Service.getBookListPersistence();
        orderHistoryPersistence = Service.getOrderHistoryPersistence();
        shoppingCartPersistence = Service.getShoppingCartPersistence();
        bookValidator = new BookValidator();
        wishlistPersistence = Service.getWishlistPersistence();
    }

    public BookManager(BookListPersistence bookListPersistence, OrderHistoryPersistence orderHistoryPersistence, ShoppingCartPersistence shoppingCartPersistence, WishlistPersistence wishlistPersistence,AccountManager accountManager) {
        this();
        this.accountManager = accountManager;
        this.bookListPersistence = bookListPersistence;
        this.orderHistoryPersistence = orderHistoryPersistence;
        this.shoppingCartPersistence = shoppingCartPersistence;
        this.wishlistPersistence = wishlistPersistence;
    }
    public void addBook(Book bookToadd) throws DuplicateBookException, InvalidBookException {
        if(!bookValidator.isValidBook(bookToadd))
            throw new InvalidBookException();
        try
        {
            bookListPersistence.insertBook(bookToadd);
        }
        catch (Exception e)
        {
            throw new DuplicateBookException();
        }

    }

    public Book searchBook(String bookName) throws BookNotFoundException{
        Book book = bookListPersistence.getBook(bookName);
        if(!bookValidator.isValidBook(book))
            throw new BookNotFoundException();

        return book;
    }

    public void deleteBook(String bookName) {
        try {
            bookListPersistence.deleteBook(bookName);
        } catch (BookNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBookList() {
        return bookListPersistence.getBookList();
    }

    public List<Book> getBestSellerList() {
        List<Book> baseList = bookListPersistence.getBookList();
        Collections.sort(baseList);

        return baseList;
    }

    public List<Book> getBestSellerList(List<Book> baseList) {
        Collections.sort(baseList);

        return baseList;
    }

    public List<Book> getSuggestedBooks() throws BookNotFoundException, InvalidAccountException{
        List<Book> suggestedBooks = new ArrayList<>();
        List<Book> bestSeller = getBestSellerList();
        List<String> orderHistGenre = new ArrayList<>();
        List<String> prevOrderedBooks = new ArrayList<>();
        List<String> orderHistAuthors = new ArrayList<>();

        List<Book> userOrderHist;

        userOrderHist = getOrderHistoryForCurrentUser();
        //create a list of authors the user has ordered before.
        for(int y = 0; y < userOrderHist.size(); y++){
            if(!(orderHistAuthors.contains(userOrderHist.get(y).getAuthorName()))){
                orderHistAuthors.add(userOrderHist.get(y).getAuthorName());
            }
        }
        //create a list of book genres the user has ordered before.
        for(int z = 0; z < userOrderHist.size(); z++){
            if(!(orderHistGenre.contains(userOrderHist.get(z).getCategory()))){
                orderHistGenre.add(userOrderHist.get(z).getCategory());
            }
        }
        //create a list of book titles the user has ordered before.
        for(int z = 0; z < userOrderHist.size(); z++){
            if(!(prevOrderedBooks.contains(userOrderHist.get(z).getBookName()))){
                prevOrderedBooks.add(userOrderHist.get(z).getBookName());
            }
        }

        //sort the books into the suggested book list if the user has ordered the same type before.
        for(int i = 0; i < bestSeller.size(); i++){
            if(!prevOrderedBooks.contains(bestSeller.get(i).getBookName())){
                for(int x = 0; x < orderHistGenre.size(); x++) //add if they order the genre before.
                    if((bestSeller.get(i)).getCategory().equals(orderHistGenre.get(x))){
                        suggestedBooks.add((bestSeller.get(i)));
                    }
                for(int j = 0; j < orderHistAuthors.size(); j++){ //add if they have ordered the author before.
                    if((bestSeller.get(i)).getAuthorName().equals(orderHistAuthors.get(j))){
                        if(!(suggestedBooks.contains((bestSeller.get(i))))){ //make sure the book was not added due to genre already.
                            suggestedBooks.add((bestSeller.get(i)));
                        }
                    }
                }
            }
        }

        return suggestedBooks;
    }

    public List<Book> getWishlist() throws InvalidAccountException, BookNotFoundException{
        List<Book> wishList;
        if(!accountManager.anyLoggedInUser()) {
            throw new InvalidAccountException();
        }
        wishList = wishlistPersistence.getWishlistForUser(accountManager.getLoggedInUsername());
        if(wishList == null || wishList.isEmpty()) {
            throw new BookNotFoundException();
        }
        return wishList;
    }

    public void addBookToWishlist(String currentUser, List<Book> bookToAdd){
        wishlistPersistence.addBookToWishlist(currentUser, bookToAdd);
    }

    public void clearWishlist(String currentUser){
        wishlistPersistence.clearWishlist(currentUser);
    }

    public List<Book> getOrderHistoryForCurrentUser() throws InvalidAccountException, BookNotFoundException{
        if(!accountManager.anyLoggedInUser())
            throw new InvalidAccountException();
        List<Book> bookList = orderHistoryPersistence.getOrderHistoryForUser(accountManager.getLoggedInUsername());

        if(!bookValidator.isValidBookList(bookList))
            throw new BookNotFoundException();

        return bookList;
    }

    public void addBookToOrderHistory(String currentUsername, List<Book> newBooks){
        orderHistoryPersistence.addNewOrderedBooks(currentUsername, newBooks);
    }

}
