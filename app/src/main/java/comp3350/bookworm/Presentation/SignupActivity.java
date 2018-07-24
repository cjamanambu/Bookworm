package comp3350.bookworm.Presentation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import comp3350.bookworm.BusinessLogic.AccountManager;
import comp3350.bookworm.Objects.Exceptions.DuplicateUsernameException;
import comp3350.bookworm.Objects.Exceptions.InvalidCredentialException;
import comp3350.bookworm.Objects.Exceptions.InvalidEmailAddressException;
import comp3350.bookworm.Objects.Account;
import comp3350.bookworm.R;

public class SignupActivity extends AppCompatActivity {
    AccountManager accountManager = new AccountManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final Button loginBtn = (Button) findViewById(R.id.btn_signup);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = ((EditText)  findViewById(R.id.signup_username)).getText().toString();
                String password = ((EditText)  findViewById(R.id.signup_password)).getText().toString();
                String email = ((EditText)  findViewById(R.id.signup_email)).getText().toString();
                Account account = new Account(username, password, email);

                try {
                    accountManager.singup(account);
                    accountManager.login(account);

                    startActivity(new Intent(SignupActivity.this, HomePage.class));
                    finish();
                }
                catch (DuplicateUsernameException e) {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, R.string.duplicate_username_found, Toast.LENGTH_LONG);
                    toast.show();
                }
                catch (InvalidEmailAddressException e) {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, R.string.email_validation_failed, Toast.LENGTH_LONG);
                    toast.show();
                }
                catch (InvalidCredentialException e) {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, R.string.login_validation_failed, Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });


        final TextView signupLink = (TextView) findViewById(R.id.link_login);
        signupLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
