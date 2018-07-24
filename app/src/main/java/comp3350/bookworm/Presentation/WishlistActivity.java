package comp3350.bookworm.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.BusinessLogic.BookManager;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.BookNotFoundException;
import comp3350.bookworm.Objects.Exceptions.InvalidAccountException;
import comp3350.bookworm.R;


public class WishlistActivity extends AppCompatActivity {

    private SearchListAdapter bookAdapter;
    private ListView listViewbooks;
    private List<Book> searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        final AccountManager accountManager = new AccountManager();
        final BookManager bookManager = new BookManager();

        List<Book> wishlist = new ArrayList<>();
        try {
            wishlist.addAll(bookManager.getWishlist());
            final String currentLoggedInUser = accountManager.getLoggedInUsername();

            //final BookArrayAdapter bookAdapter;
            listViewbooks = (ListView) findViewById(R.id.list_wishlist);
            searchList = (ArrayList<Book>) wishlist;
            bookAdapter = new SearchListAdapter(this, (ArrayList<Book>) searchList);
            listViewbooks.setAdapter(bookAdapter);

            // Construct ArrayAdapter for best-seller
            final BookArrayAdapter wishBooksArrayAdapter = new BookArrayAdapter(this, (ArrayList<Book>) wishlist);
            final ListView listView = (ListView) findViewById(R.id.list_wishlist);
            listView.setAdapter(wishBooksArrayAdapter);

            final Button buttonWishlist = (Button) findViewById( R.id.btn_clear_wishlist );
            buttonWishlist.setOnClickListener( new View.OnClickListener() {
                public void onClick( View v ) {
                    bookManager.clearWishlist(currentLoggedInUser);
                    restartActivity();

                }
            });
        }catch (InvalidAccountException e){
        }catch (BookNotFoundException e){
            Toast.makeText(WishlistActivity.this, "No Books being Found", Toast.LENGTH_SHORT).show();
        }

    }

    public void restartActivity(){
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }
}
