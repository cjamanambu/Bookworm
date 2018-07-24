package comp3350.bookworm.BusinessLogic.Validator;

import java.util.List;

import comp3350.bookworm.Objects.Book;

public class BookValidator {

    public boolean isValidBook(Book book) {
        return book != null && !book.getBookName().isEmpty();
    }

    public boolean isValidBookList(List<Book> bookList) {
        return bookList != null && !bookList.isEmpty();
    }
}
