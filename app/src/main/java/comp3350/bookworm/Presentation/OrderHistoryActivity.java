package comp3350.bookworm.Presentation;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import comp3350.bookworm.BusinessLogic.BookManager;
import comp3350.bookworm.Objects.Exceptions.BookNotFoundException;
import comp3350.bookworm.Objects.Exceptions.InvalidAccountException;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.R;

public class OrderHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        BookManager bookManager = new BookManager();

        try {
            List<Book> books = bookManager.getOrderHistoryForCurrentUser();
            BookArrayAdapter bookArrayAdapter = new BookArrayAdapter(this, (ArrayList<Book>) books);
            ListView listView = (ListView) findViewById(R.id.bookList_orderhistory);
            listView.setAdapter(bookArrayAdapter);
        }
        catch (InvalidAccountException e) {
            int duration = Toast.LENGTH_LONG;
            Context context = getApplicationContext();

            Toast toast = Toast.makeText(context, R.string.InvalidUsername, duration);
            toast.show();
        }
        catch (BookNotFoundException e) {
            BookArrayAdapter bookArrayAdapter = new BookArrayAdapter(this, new ArrayList<Book>());
            ListView listView = (ListView) findViewById(R.id.bookList_orderhistory);
            listView.setAdapter(bookArrayAdapter);
        }
    }
}
