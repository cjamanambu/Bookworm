package comp3350.bookworm.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.BusinessLogic.BookManager;
import comp3350.bookworm.BusinessLogic.ShoppingCartManager;
import comp3350.bookworm.BusinessLogic.Time.Real.TimeProviderReal;
import comp3350.bookworm.BusinessLogic.Time.TimeProvider;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.R;

public class ShoppingCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        final BookManager bookManager = new BookManager();
        final ShoppingCartManager shoppingCartManager = new ShoppingCartManager();
        final AccountManager accountManager = new AccountManager();

        final String currentLoggedInUser = accountManager.getLoggedInUsername();

        final CartArrayAdapter shoppingCartArrayAdapter = new CartArrayAdapter(this, (ArrayList<Book>) shoppingCartManager.getShoppingCartForCurrentUser(currentLoggedInUser));
        final ListView listView = (ListView) findViewById(R.id.shopping_cart_list);
        listView.setAdapter(shoppingCartArrayAdapter);

        String priceString = shoppingCartManager.getShoppingCartTotal(currentLoggedInUser);
        String cartTotal = "Current Total: CAD$";
        // check half-price day
        TimeProvider timeProvider = new TimeProviderReal();
        if(timeProvider.isHalfPriceDay()) {
            Double price = Double.parseDouble(priceString) / 2;
            cartTotal += price + "  50% off!";
        }
        else {
            cartTotal += priceString;
        }

        TextView item = (TextView)findViewById(R.id.cart_total);
        item.setText(cartTotal);

        Button confirm_btn2 = (Button)findViewById(R.id.confirm_btn2);
        Button cancel_btn2 = (Button)findViewById(R.id.cancel_btn2);
        Button clear_btn = (Button)findViewById(R.id.clear_btn);

        confirm_btn2.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                if(!shoppingCartManager.shoppingCartIsEmpty(currentLoggedInUser)){
                    Intent intentPlaceOrder = new Intent( ShoppingCartActivity.this, PlaceOrderActivity.class );
                    startActivity( intentPlaceOrder );
                    finish();
                }
                else{
                    Toast.makeText( ShoppingCartActivity.this,
                            "Your Shopping Cart is empty.",
                            Toast.LENGTH_SHORT ).show();
                }
            }
        });

        cancel_btn2.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                Intent intentBackToAccount = new Intent( ShoppingCartActivity.this, AccountActivity.class );
                startActivity( intentBackToAccount );
                finish();
            }
        });

        clear_btn.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                if(shoppingCartManager.shoppingCartIsEmpty(currentLoggedInUser)){
                    Toast.makeText( ShoppingCartActivity.this,
                            "Your Shopping Cart is empty.",
                            Toast.LENGTH_SHORT ).show();
                }
                else{
                    shoppingCartManager.clearShoppingCart(currentLoggedInUser);
                    Intent refreshCart = new Intent( ShoppingCartActivity.this, ShoppingCartActivity.class );
                    startActivity( refreshCart );
                    finish();

                    Toast.makeText( ShoppingCartActivity.this,
                            "Your Shopping Cart has been cleared.",
                            Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
}
