
package comp3350.bookworm.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import comp3350.bookworm.BusinessLogic.BookManager;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.BookNotFoundException;
import comp3350.bookworm.Objects.Exceptions.InvalidAccountException;
import comp3350.bookworm.R;

public class LibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        BookManager bookManager = new BookManager();

        Button discoverBtn = (Button) findViewById(R.id.library_discover);
        Button accountBtn = (Button) findViewById(R.id.library_account);

        discoverBtn.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                startActivity(new Intent(LibraryActivity.this, HomePage.class));
                finish();
            }
        });

        accountBtn.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                startActivity(new Intent(LibraryActivity.this, HomePage.class));
                finish();
            }
        });

        LinearLayout visitorMode = (LinearLayout) findViewById(R.id.library_visitor);
        visitorMode.setVisibility(View.INVISIBLE);

        try {
            GridView gridView = (GridView) findViewById(R.id.library_gridView);
            BookGripAdapter booksAdapter = new BookGripAdapter(this, (ArrayList<Book>) bookManager.getOrderHistoryForCurrentUser());
            gridView.setAdapter(booksAdapter);
        }
        catch (InvalidAccountException e) {
            visitorMode.setVisibility(View.VISIBLE);
        }
        catch (BookNotFoundException e) {
            GridView gridView = (GridView) findViewById(R.id.library_gridView);
            BookGripAdapter booksAdapter = new BookGripAdapter(this, new ArrayList<Book>());
            gridView.setAdapter(booksAdapter);
        }
    }
}
