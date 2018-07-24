package comp3350.bookworm.Persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.InvalidBookException;
import comp3350.bookworm.Objects.Exceptions.DuplicateBookException;

public class BookListPersistenceStub implements BookListPersistence {
    private List<Book> bookList;

    public BookListPersistenceStub() {
        this.bookList = new ArrayList<>();

        bookList.add(new Book("C++", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 1));
        bookList.add(new Book("Java", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 2));
        bookList.add(new Book("C#", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 3));
        bookList.add(new Book("C++ : The Return of the C++ King", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 4));
        bookList.add(new Book("Ruby on Rails", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 5));
        bookList.add(new Book("ABC's For Kids", "Daniel J. Fung", "Children's book", "Children's", 10.0, 5.0, 6));
        bookList.add(new Book("Cooking with Fire: BBQ recipes", "Daniel J. Fung", "Cooking book", "Cooking", 10.0, 5.0, 7));
        bookList.add(new Book("Steamy Romance 5: The Reckoning", "Daniel J. Fung", "Romance book", "Romance", 10.0, 5.0, 8));
        bookList.add(new Book("How to Fix an ID10T error Vol 2", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 9));
        bookList.add(new Book("How to Fix an ID10T error Vol 1", "Daniel J. Fung", "Programming book", "Programming", 10.0, 5.0, 10));
        bookList.add(new Book("Harry Potter and the Evil Potato", "Daniel J. Fung", "Fantasy book", "Fantasy", 10.0, 5.0, 11));
    }

    @Override
    public void insertBook(Book book) throws InvalidBookException, DuplicateBookException {
        for (Book b : bookList) {
            if (b.getBookName().equals(book.getBookName()))
                throw new DuplicateBookException();
        }
        try {
            this.bookList.add(book);
        } catch (Exception e) {
            throw new InvalidBookException();
        }

    }


    @Override
    public Book getBook(String bookName) {
        for (Book b : bookList) {
            if (b.getBookName().equals(bookName))
                return b;
        }
        return null;
    }

    @Override
    public List<Book> getBookList() {
        return bookList;
    }

    @Override
    public void deleteBook(String bookName) {
        boolean deleted = false;
        for (int i = 0; i < bookList.size() && deleted == false; i++) {
            if (bookList.get(i).getBookName().equalsIgnoreCase(bookName)) {
                bookList.remove(i);
                deleted = true;
            }
        }
    }
}
