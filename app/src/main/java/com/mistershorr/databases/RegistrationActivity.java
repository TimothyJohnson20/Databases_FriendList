package com.mistershorr.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class RegistrationActivity extends AppCompatActivity {

    public static final String TAG = RegistrationActivity.class.getSimpleName();
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_PASSWORD = "password";

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show the back button

        wireWidgets();
        setListeners();

        //  Preload the username    (get the username from the intent)
        String username = getIntent().getStringExtra(LoginActivity.EXTRA_USERNAME);
        editTextUsername.setText(username);
    }

    private void setListeners() {
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBackendlessAccount();
            }
        });
    }

    private void createBackendlessAccount() {
        // TODO update to make this work with startActivityForResult

        // create account on backendless

        // finish the activity

        // do not forget to call Backendless.initApp when your app initializes

        BackendlessUser user = new BackendlessUser();
        user.setProperty( "email", editTextEmail.getText().toString());
        user.setProperty("name", editTextName.getText().toString());
        user.setProperty("username", editTextUsername.getText().toString());
        user.setPassword(editTextPassword.getText().toString());

        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String email = editTextEmail.getText().toString();
        String name = editTextName.getText().toString();
        String confirmPass = editTextConfirmPassword.getText().toString();

        if(!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !name.isEmpty()) {
            if (!confirmPass.isEmpty()) {
                if (confirmPass.equals(password)) {
                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        public void handleResponse(BackendlessUser registeredUser) {
                            String username = editTextUsername.getText().toString();
                            String password = editTextPassword.getText().toString();
                            Intent registrationCreateIntent = new Intent();
                            registrationCreateIntent.putExtra(EXTRA_USERNAME, username);
                            registrationCreateIntent.putExtra(EXTRA_PASSWORD, password);
                            setResult(RESULT_OK, registrationCreateIntent);
                            finish();
                        }

                        public void handleFault(BackendlessFault fault) {
                            // an error has occurred, the error code can be retrieved with fault.getCode()

                            Toast.makeText(RegistrationActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else{
                    Toast.makeText(this, "Passwords are not the same", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Please enter a Name, Email, Username, and Password", Toast.LENGTH_SHORT).show();
        }
    }



    private void wireWidgets() {
        editTextUsername = findViewById(R.id.edit_text_create_account_username);
        editTextPassword = findViewById(R.id.edit_text_create_account_password);
        editTextName = findViewById(R.id.edit_text_create_account_name);
        editTextEmail = findViewById(R.id.edit_text_create_account_email);
        editTextConfirmPassword = findViewById(R.id.edit_text_create_account_confirm_password);
        buttonCreateAccount = findViewById(R.id.button_create_account);
    }
}
