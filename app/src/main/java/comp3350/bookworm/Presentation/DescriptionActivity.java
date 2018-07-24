
package comp3350.bookworm.Presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.BusinessLogic.BookManager;
import comp3350.bookworm.BusinessLogic.ReviewManager;
import comp3350.bookworm.BusinessLogic.ShoppingCartManager;
import comp3350.bookworm.BusinessLogic.Time.Real.TimeProviderReal;
import comp3350.bookworm.BusinessLogic.Time.TimeProvider;
import comp3350.bookworm.Objects.Book;
import comp3350.bookworm.Objects.Exceptions.BookNotFoundException;
import comp3350.bookworm.Objects.Exceptions.InvalidAccountException;
import comp3350.bookworm.Objects.Exceptions.InvalidReviewException;
import comp3350.bookworm.Objects.Exceptions.UserNotSignedInException;
import comp3350.bookworm.Objects.Review;
import comp3350.bookworm.R;


public class DescriptionActivity extends AppCompatActivity {
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        final ReviewManager reviewManager = new ReviewManager();

        final BookManager bookManager = new BookManager();
        final ShoppingCartManager shoppingCartManager = new ShoppingCartManager();
        AccountManager accountManager = new AccountManager();

        try {
            Intent mIntent = getIntent();
            String bookName = mIntent.getStringExtra("BookName");

            final Book book = bookManager.searchBook(bookName);
            final String currentLoggedInUser = accountManager.getLoggedInUsername();

            final TextView textView_title = (TextView) findViewById( R.id.textView_title );
            textView_title.setText(book.getBookName());

            final TextView textView_author = (TextView) findViewById( R.id.textView_authorName );
            textView_author.setText(book.getAuthorName());

            final TextView textView_rating = (TextView) findViewById( R.id.textView_rating );
            textView_rating.setText( "Rating: " + String.valueOf( reviewManager.getBookRating(book.getBookName()) ) );

            final TextView textView_price = (TextView) findViewById( R.id.textView_price );
            // check half-price day
            TimeProvider timeProvider = new TimeProviderReal();
            if(timeProvider.isHalfPriceDay()) {
                textView_price.setTextColor(Color.RED);
                textView_price.setText("CAD$ " + Double.toString(book.getHalfBookPrice()) + "  50% off!");
            }
            else
                textView_price.setText("CAD$ " + Double.toString(book.getBookPrice()));

            final Button buttonBuy = (Button) findViewById( R.id.btn_buy );
            buttonBuy.setOnClickListener( new View.OnClickListener() {
                public void onClick( View v ) {

                    try{
                        shoppingCartManager.addBookToUsersCart(currentLoggedInUser, book);
                        AlertDialog.Builder dialog_buy = new AlertDialog.Builder( DescriptionActivity.this );
                        View mView = getLayoutInflater().inflate( R.layout.dialog_add_to_cart, null );
                        Button btn_back_to_list = (Button) mView.findViewById( R.id.btn_back_to_list );
                        Button btn_check_cart = (Button) mView.findViewById( R.id.btn_check_cart );

                        dialog_buy.setView(mView);
                        final AlertDialog dialog = dialog_buy.create();
                        dialog.show();


                        btn_back_to_list.setOnClickListener( new View.OnClickListener() {
                            public void onClick( View v ) {
                                Intent intentBackToHome = new Intent( DescriptionActivity.this, HomePage.class );
                                startActivity( intentBackToHome );
                                dialog.dismiss();
                                finish();
                            }
                        });

                        btn_check_cart.setOnClickListener( new View.OnClickListener() {
                            public void onClick( View v ) {
                                Intent intentCart = new Intent( DescriptionActivity.this, ShoppingCartActivity.class );
                                startActivity( intentCart );
                            }
                        });
                    }catch (UserNotSignedInException | InvalidAccountException i){
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.login_prompt, Toast.LENGTH_SHORT);
                        toast.show();
                    }



                }
            });

            final Button buttonWishlist = (Button) findViewById( R.id.btn_add_wishlist );
            buttonWishlist.setOnClickListener( new View.OnClickListener() {
                public void onClick( View v ) {
                    List<Book> toAdd = new ArrayList<Book>();
                    toAdd.add(book);

                    bookManager.addBookToWishlist(currentLoggedInUser, toAdd);
                    buttonWishlist.setEnabled(false);

                }
            });

            final Button buttonPreview = (Button) findViewById( R.id.btn_preview );
            buttonPreview.setOnClickListener( new View.OnClickListener() {
                public void onClick( View v ) {
                    Intent intentPreview = new Intent( DescriptionActivity.this, BookPreviewActivity.class );
                    intentPreview.putExtra( "PREVIEW_CONTENT", book.getPreview() );
                    startActivity( intentPreview );

                }
            });

            final ReviewArrayAdapter reviewArrayAdapter = new ReviewArrayAdapter( this, (ArrayList<Review>) reviewManager.getReviewList( book.getBookName() ) );
            final ListView listView = (ListView) findViewById( R.id.list_reviews );
            listView.setAdapter( reviewArrayAdapter );

            final EditText reviewWriter = (EditText) findViewById( R.id.editText_accName );
            final EditText reviewRating = (EditText) findViewById( R.id.editText_rating );
            final EditText reviewContent = (EditText) findViewById( R.id.editText_content );

            final Button reviewSubmit = (Button) findViewById( R.id.btn_reviewSubmit );
            reviewSubmit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View view ) {
                    if ( (!reviewWriter.getText().toString().isEmpty()) && (!reviewContent.getText().toString().isEmpty()) ) {

                        String rating = reviewRating.getText().toString();
                        if(rating.isEmpty())
                            rating = "0";
                        final Review newReview = new Review( (reviewWriter.getText()).toString(),
                                                             Integer.parseInt(rating),
                                                             (reviewContent.getText()).toString() );

                        try {
                            reviewManager.addReview( book, newReview );
                            reviewArrayAdapter.notifyDataSetChanged();
                            restartActivity();

                        } catch ( InvalidReviewException e ) {
                            Toast.makeText(mContext, "Invalid Review!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText(mContext, "Invalid Review!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        catch (BookNotFoundException e) {
            Toast.makeText( DescriptionActivity.this,
                    R.string.book_not_found,
                    Toast.LENGTH_SHORT ).show();
            Intent intentBackToHome = new Intent( DescriptionActivity.this, HomePage.class );
            startActivity( intentBackToHome );
            finish();
        }


    }

    public void restartActivity(){
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }
}
