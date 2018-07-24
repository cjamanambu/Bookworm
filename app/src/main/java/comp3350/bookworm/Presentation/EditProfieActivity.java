package comp3350.bookworm.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.Objects.Exceptions.InvalidAccountException;
import comp3350.bookworm.Objects.Exceptions.InvalidEmailAddressException;
import comp3350.bookworm.R;

public class EditProfieActivity extends AppCompatActivity {
    AccountManager accountManager = new AccountManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profie);

        buttonHandler();
    }

    private void buttonHandler(){
        //cancel button
        Button cancelAddrButton = (Button) findViewById(R.id.cancel_edit_btn);
        cancelAddrButton.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v ) {
                Intent intentGoBack = new Intent( EditProfieActivity.this, AccountActivity.class );
                startActivity( intentGoBack );
                finish();
            }
        });

        //confirm button
        Button confirmAddrButton = (Button) findViewById(R.id.confirm_edit_btn);
        confirmAddrButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String newName = ((EditText)  findViewById(R.id.new_name)).getText().toString();
                String newEmail = ((EditText)  findViewById(R.id.new_email_addr)).getText().toString();
                String newPassword =((EditText)  findViewById(R.id.new_password)).getText().toString();

                try {
                    accountManager.updateProfie(accountManager.getLoggedInUsername(), newName, newEmail, newPassword);
                    startActivity(new Intent(EditProfieActivity.this, AccountActivity.class));
                    finish();
                }
                catch(InvalidEmailAddressException e){
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid Email Addresss", Toast.LENGTH_SHORT);
                    toast.show();
                } catch (InvalidAccountException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Empty Username or Password", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        //change address button
        Button modifyAddrButton = (Button) findViewById(R.id.addr_change_btn);
        modifyAddrButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent modifyAddress = new Intent(EditProfieActivity.this, ModifyAddressActivity.class);
                modifyAddress.putExtra("From_Activity", "EditProfie");
                startActivity(modifyAddress);
            }
        });


    }//buttonHandler
}
