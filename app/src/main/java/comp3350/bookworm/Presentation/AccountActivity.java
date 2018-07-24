package comp3350.bookworm.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.R;

public class AccountActivity extends AppCompatActivity {
    AccountManager accountManager = new AccountManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Button libraryBtn = (Button) findViewById(R.id.account_library);
        Button discoverBtn = (Button) findViewById(R.id.account_discover);

        libraryBtn.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                startActivity(new Intent(AccountActivity.this, LibraryActivity.class));
                finish();
            }
        });

        discoverBtn.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                startActivity(new Intent(AccountActivity.this, HomePage.class));
                finish();
            }
        });

        if(accountManager.anyLoggedInUser()) {
            findViewById(R.id.account_visitor).setVisibility(View.INVISIBLE);
            findViewById(R.id.account_logined).setVisibility(View.VISIBLE);
            String currentUserText = "Hi there, "+ accountManager.getLoggedInUsername().toUpperCase()+". Welcome back.";
            ((TextView)findViewById(R.id.account_uername)).setText(currentUserText);
        }
        else {
            findViewById(R.id.account_logined).setVisibility(View.INVISIBLE);
            findViewById(R.id.account_visitor).setVisibility(View.VISIBLE);
        }

        final Button loginBtn = (Button) findViewById(R.id.btn_login_account_page);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, LoginActivity.class));
            }
        });

        final Button logoutBtn = (Button) findViewById(R.id.btn_account_logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                accountManager.logout();
                startActivity(new Intent(AccountActivity.this, HomePage.class));
            }
        });

        final Button purchaseBtn = (Button) findViewById(R.id.btn_Purchase_History);
        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

               startActivity(new Intent(AccountActivity.this, OrderHistoryActivity.class));
            }
        });

        final Button tempBtn = (Button) findViewById(R.id.button4);
        tempBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                startActivity(new Intent(AccountActivity.this, ShoppingCartActivity.class));
            }
        });

        final Button editProfBtn = (Button) findViewById(R.id.btn_Edit_Profie);
        editProfBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                startActivity(new Intent(AccountActivity.this, EditProfieActivity.class));
            }
        });

        final Button wishlistbtn = (Button) findViewById(R.id.btn_account_wishlist);
        wishlistbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                startActivity(new Intent(AccountActivity.this, WishlistActivity.class));
            }
        });

    }

}
