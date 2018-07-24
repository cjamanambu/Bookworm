package comp3350.bookworm.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.BusinessLogic.Validator.PaymentValidator;
import comp3350.bookworm.Objects.Exceptions.InvalidPmtInfoException;
import comp3350.bookworm.R;

public class CreditCardPaymentActivity extends AppCompatActivity {

    AccountManager accountManager = new AccountManager();
    PaymentValidator paymentValidator = new PaymentValidator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_payment);

        Button cancelAddrButton = (Button) findViewById(R.id.cancel_pmt_btn);
        cancelAddrButton.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                Intent intentGoBack = new Intent( CreditCardPaymentActivity.this, PlaceOrderActivity.class );
                startActivity( intentGoBack );
                finish();
            }
        });

        Button confirmAddrButton = (Button) findViewById(R.id.confirm_pmt_btn);
        confirmAddrButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String issue_network = ((EditText)  findViewById(R.id.issue_net)).getText().toString();
                String card_number = ((EditText)  findViewById(R.id.card_no)).getText().toString();
                String cvv = ((EditText)  findViewById(R.id.cvv)).getText().toString();
                String expiry = ((EditText)  findViewById(R.id.expiry)).getText().toString();

                try {
                    accountManager.updatePmtOption(accountManager.getLoggedInUsername(), issue_network, card_number, cvv, expiry);
                    Intent intentGoBack = new Intent( CreditCardPaymentActivity.this, PlaceOrderActivity.class );
                    startActivity( intentGoBack );
                    finish();
                }
                catch (InvalidPmtInfoException e) {

                    if(paymentValidator.isValidCardNumber(card_number))
                        Toast.makeText( CreditCardPaymentActivity.this,"Invalid Card Number",
                                Toast.LENGTH_SHORT ).show();
                    else if(paymentValidator.isValidCvv(cvv))
                        Toast.makeText( CreditCardPaymentActivity.this,"Invalid CVV",
                                Toast.LENGTH_SHORT ).show();
                    else if(paymentValidator.isValidExpiry(expiry))
                        Toast.makeText( CreditCardPaymentActivity.this,"Invalid Expiry",
                                Toast.LENGTH_SHORT ).show();
                    else
                        Toast.makeText( CreditCardPaymentActivity.this,"Invalid Payment Information",
                                Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
}
