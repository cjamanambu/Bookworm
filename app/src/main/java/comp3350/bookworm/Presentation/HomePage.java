
package comp3350.bookworm.Presentation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.Application.Main;
import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.BusinessLogic.BookManager;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.R;

public class HomePage extends AppCompatActivity {

    private SearchView searchView;
    private SearchListAdapter bookAdapter;
    private ListView listViewbooks;
    private List<Book> searchList;
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        copyDatabaseToDevice();

        Button libraryBtn = (Button) findViewById(R.id.home_library);
        Button accountBtn = (Button) findViewById(R.id.home_account);

        libraryBtn.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                startActivity(new Intent(HomePage.this, LibraryActivity.class));
            }
        });

        accountBtn.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                startActivity(new Intent(HomePage.this, AccountActivity.class));
            }
        });


        final AccountManager accountManager = new AccountManager();
        BookManager bookManager = new BookManager();

        List<Book> allBookList = new ArrayList<>();
        allBookList.addAll(bookManager.getBookList());


        //final BookArrayAdapter bookAdapter;
        listViewbooks = (ListView) findViewById(R.id.bookList_homepage);
        searchList = (ArrayList<Book>) allBookList;
        bookAdapter = new SearchListAdapter(this, (ArrayList<Book>) searchList);
        listViewbooks.setAdapter(bookAdapter);

        // Construct ArrayAdapter for best-seller
        final BookArrayAdapter allBooksArrayAdapter = new BookArrayAdapter(this, (ArrayList<Book>) allBookList);
        final BookArrayAdapter bestsellerBookArrayAdapter = new BookArrayAdapter(this, (ArrayList<Book>) bookManager.getBestSellerList());
        List<Book> suggestionBookList;
        try {
            suggestionBookList = bookManager.getSuggestedBooks();
        }
        catch(Exception e) {
            Toast.makeText(HomePage.this, R.string.no_suggestion_msg, Toast.LENGTH_SHORT).show();
            suggestionBookList = new ArrayList<>();
        }
        final BookArrayAdapter suggestionBookArrayAdapter = new BookArrayAdapter(this, (ArrayList<Book>) suggestionBookList);

        final ListView listView = (ListView) findViewById(R.id.bookList_homepage);
        listView.setAdapter(bestsellerBookArrayAdapter);


        final ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.All_Books));
        options.add(getString(R.string.best_seller));
        options.add(getString(R.string.best_choice));

        spinner = (Spinner) findViewById(R.id.discover_spinner);
        spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,options);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (options.get(position)) {
                    case  "All Books":
                        listView.setAdapter(allBooksArrayAdapter);
                        break;
                    case "Best-seller":
                        listView.setAdapter(bestsellerBookArrayAdapter);
                        break;
                    case "Best choice":
                        if(accountManager.anyLoggedInUser()) {
                            listView.setAdapter(suggestionBookArrayAdapter);
                        }
                        else
                            Toast.makeText(HomePage.this, R.string.login_prompt, Toast.LENGTH_LONG).show();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    @Override
    public void onBackPressed() {
        bookAdapter.filter("");
        if (!searchView.isIconified()) {

            searchView.setIconified(true);
        } else {
            //bookAdapter.filter("");
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        spinner.setSelection(0);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.app_search_bar);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bookAdapter.filter(newText);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                spinner.setSelection(0);
                bookAdapter.filter("");
                return false;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                spinner.setSelection(0);
                if (!hasFocus) {
                    searchView.setIconified(true);
                }
            }
        });
        
        return super.onCreateOptionsMenu(menu);
    }

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();

            alertDialog.setTitle(this.getString(R.string.warning));
            alertDialog.setMessage("Unable to access application data: " + ioe.getMessage());

            alertDialog.show();
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            // add "true ||" to overwrite file
            if (!outFile.exists()) {
//            if (true || !outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

}
