package comp3350.bookworm.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.Objects.Exceptions.InvalidAddressException;
import comp3350.bookworm.R;

public class ModifyAddressActivity extends AppCompatActivity {

    String selectedAddressProvince;
    AccountManager accountManager = new AccountManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_address);

        provinceDropdownList();
        buttonHandler();

    }

    private void provinceDropdownList(){
        Spinner provinceSpinner = (Spinner)findViewById(R.id.province_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> provinceArrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.provinces_array, R.layout.support_simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        provinceArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        provinceSpinner.setAdapter(provinceArrayAdapter);

        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAddressProvince = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void buttonHandler(){
        Button cancelAddrButton = (Button) findViewById(R.id.cancel_addr_btn);
        cancelAddrButton.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                Intent mIntent = getIntent();
                String previousActivity = mIntent.getStringExtra("From_Activity");
                if(previousActivity.equals("PlaceOrder")) {
                    startActivity(new Intent(ModifyAddressActivity.this, PlaceOrderActivity.class));
                }
                finish();
            }
        });

        Button confirmAddrButton = (Button) findViewById(R.id.confirm_addr_btn);
        confirmAddrButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String addr_line_1 = ((EditText)  findViewById(R.id.addr_1)).getText().toString();
                String addr_line_2 = ((EditText)  findViewById(R.id.addr_2)).getText().toString();
                String city = ((EditText)  findViewById(R.id.addr_city)).getText().toString();
                String postCode = ((EditText)  findViewById(R.id.addr_postal_code)).getText().toString();

                try {
                    accountManager.updateAddress(accountManager.getLoggedInUsername(), addr_line_1, addr_line_2, city, selectedAddressProvince, postCode);
                    Intent mIntent = getIntent();
                    String previousActivity = mIntent.getStringExtra("From_Activity");
                    if(previousActivity.equals("PlaceOrder")) {
                        startActivity(new Intent(ModifyAddressActivity.this, PlaceOrderActivity.class));
                    }
                    finish();
                }
                catch (InvalidAddressException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.invalid_address, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
