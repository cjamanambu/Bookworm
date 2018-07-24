package comp3350.bookworm.Persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.Objects.Book;


public class OrderHistoryPersistenceStub implements OrderHistoryPersistence {
    private Map<String, List<Book>> orderHistorys;

    public OrderHistoryPersistenceStub() {
        List<Account> accounts = new AccountPersistenceStub().getAllAccounts();

        List<Book> bookList = new ArrayList<>();

        bookList.add(new Book("Steamy Romance 5: The Reckoning", "Daniel J. Fung", "Romance book", "Romance", 10.0, 5.0, 8));
        bookList.add(new Book("How to Fix an ID10T error Vol 2", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 9));
        bookList.add(new Book("How to Fix an ID10T error Vol 1", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 10));

        orderHistorys = new HashMap<>();
        for(Account a : accounts) {
            orderHistorys.put(a.getUserName(), bookList);
        }
    }

    @Override
    public List<Book> getOrderHistoryForUser(String username) {
        return orderHistorys.get(username);
    }

    @Override
    public void addNewOrderedBooks(String username, List<Book> newBooks)
    {
        List<Book> bookList = orderHistorys.get(username);
        // check if account exits
        if(bookList != null) {
            bookList.addAll(newBooks);
        }
        else {
            List<Book> newList = new ArrayList<>();
            newList.addAll(newBooks);
            orderHistorys.put(username, newList);
        }
    }
}
