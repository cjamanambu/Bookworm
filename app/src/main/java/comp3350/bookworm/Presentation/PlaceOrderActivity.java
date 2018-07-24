package comp3350.bookworm.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.BusinessLogic.BookManager;
import comp3350.bookworm.BusinessLogic.ShoppingCartManager;
import comp3350.bookworm.BusinessLogic.Time.Real.TimeProviderReal;
import comp3350.bookworm.BusinessLogic.Validator.PaymentValidator;
import comp3350.bookworm.Objects.Address;
import comp3350.bookworm.Objects.Payment;
import comp3350.bookworm.R;

public class PlaceOrderActivity extends AppCompatActivity {

    String fee = "CAD$ ";
    String setText = "";
    AccountManager accountManager = new AccountManager();
    ShoppingCartManager shoppingCartManager = new ShoppingCartManager();
    BookManager bookManager = new BookManager();
    PaymentValidator paymentValidator = new PaymentValidator();
    boolean addressIsPresent = false;
    boolean paymentIsPresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        // Display the current signed in user
        final String loggedInUser = accountManager.getLoggedInUsername();
        String loggedInUserDisplay = loggedInUser.toUpperCase();
        TextView deliveringToText = (TextView)findViewById(R.id.current_user);
        setText = "Delivering To: "+ loggedInUserDisplay;
        deliveringToText.setText(setText);

        // Display the fees dynamically

        Double itemTotal = shoppingCartManager.getItemTotal(new TimeProviderReal());
        String itemFee = fee + String.format("%5.2f", itemTotal);
        TextView item = (TextView)findViewById(R.id.items_price);
        item.setText(itemFee);

        String deliveryFee = fee + String.format("%5.2f", shoppingCartManager.getDeliveryFee());
        TextView delivery = (TextView)findViewById(R.id.delivery_price);
        delivery.setText(deliveryFee);

        String totalFeeB4Tax = fee + String.format("%5.2f", shoppingCartManager.getTotalBeforeTax(itemTotal));
        TextView total = (TextView)findViewById(R.id.totalb4tax_price);
        total.setText(totalFeeB4Tax);

        String gstFee = fee + String.format("%5.2f", shoppingCartManager.getGST(itemTotal));
        TextView gst = (TextView)findViewById(R.id.gst_price);
        gst.setText(gstFee);

        String pstFee = fee + String.format("%5.2f", shoppingCartManager.getPST(itemTotal));
        TextView pst = (TextView)findViewById(R.id.pst_price);
        pst.setText(pstFee);

        String orderTotalFee = fee + Double.toString(shoppingCartManager.getOrderTotal(itemTotal));
        TextView orderTotal = (TextView)findViewById(R.id.ordertotal_price);
        orderTotal.setText(orderTotalFee);

        // Display the Address of the Current User
        Address loggedInAddress = accountManager.getLoggedInAddress();
        TextView shippingToText = (TextView)findViewById(R.id.shipping_to);
        setText = "Shipping To: "+ loggedInUserDisplay;
        shippingToText.setText(setText);

        TextView shippingAddress = (TextView)findViewById(R.id.address);
        if(loggedInAddress != null && loggedInAddress.isSet()){
            setText = "";
            setText += loggedInAddress.getAddressLine1() + "\n";
            if(!loggedInAddress.getAddressLine2().isEmpty())
                setText += loggedInAddress.getAddressLine2() + "\n";
            setText += loggedInAddress.getCity() + ", " + loggedInAddress.getProvince() + "\n";
            setText += loggedInAddress.getPostalCode();
            addressIsPresent = true;
        }
        else
            setText = "Please Modify Address to add a new shipping address.";
        shippingAddress.setText(setText);


        // Display the Payment Option of the Current User

        TextView billingToText = (TextView)findViewById(R.id.billing_to);
        setText = "Billing To: "+ loggedInUserDisplay;
        billingToText.setText(setText);

        final Payment loggedInPaymentOption = accountManager.getLoggedInPaymentInfo();

        TextView paymentInfo = (TextView)findViewById(R.id.pmt_info);
        if(loggedInPaymentOption != null && paymentValidator.isValidPmtOption(loggedInPaymentOption)){
            setText = "Charging " + loggedInPaymentOption.getIssuingNetwork() + " Credit Card with Card Number ending ";
            setText += loggedInPaymentOption.truncateCardNumber();
            paymentIsPresent = true;
        }
        else{
            setText = "Please Modify Payment Option to add a new payment option.";
        }
        paymentInfo.setText(setText);

        // Confirm, Delete, Address, Payment  Buttons Actions
        Button confirm_btn = (Button)findViewById(R.id.confirm_btn);
        Button cancel_btn = (Button)findViewById(R.id.cancel_btn);
        Button address_btn = (Button)findViewById(R.id.address_btn);
        Button pmt_btn = (Button)findViewById(R.id.pmt_btn);

        confirm_btn.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {

                if(!addressIsPresent){

                    Toast.makeText( PlaceOrderActivity.this, "Please enter an address!",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!paymentIsPresent){
                    Toast.makeText( PlaceOrderActivity.this, "Please enter payment information!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    bookManager.addBookToOrderHistory(loggedInUser,
                            shoppingCartManager.getShoppingCartForCurrentUser(loggedInUser));

                    shoppingCartManager.clearShoppingCart(loggedInUser);

                    Intent intentBackToOrderHistory = new Intent( PlaceOrderActivity.this, HomePage.class );
                    startActivity( intentBackToOrderHistory );
                    finish();

                    Toast.makeText( PlaceOrderActivity.this,"Thank you. Your order has been placed!",
                            Toast.LENGTH_LONG ).show();
                }

            }
        });

        cancel_btn.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                Intent intentBackToCart = new Intent( PlaceOrderActivity.this, ShoppingCartActivity.class );
                startActivity( intentBackToCart );
                finish();
            }
        });

        address_btn.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                Intent modifyAddress = new Intent( PlaceOrderActivity.this, ModifyAddressActivity.class );
                modifyAddress.putExtra("From_Activity", "PlaceOrder");
                startActivity( modifyAddress );
                finish();
            }
        });

        pmt_btn.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                Intent intentCreditCardPmt = new Intent( PlaceOrderActivity.this, CreditCardPaymentActivity.class );
                intentCreditCardPmt.putExtra("From_Activity", "PlaceOrder");
                startActivity( intentCreditCardPmt );
                finish();

            }
        });

    }
}
