package org.pltw.examples.triptracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    private EditText mNameEdit;
    private EditText mEmailEdit;
    private EditText mPasswordEdit;
    private Button mSignUpButton;
    private Button mLoginButton;
    private TextView mSignUpTextView;
    private final String be_app_id = "1D3518A2-AD14-EA8C-FFE3-767E44663300";
    private final String be_android_api_key = "47444F30-4D48-0B5E-FFF5-872A9965FB00";
    private final String TAG = this.getClass().getName();

    public void warnUser(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(message);
        builder.setTitle(R.string.authentication_error_title);
        builder.setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mNameEdit = (EditText) findViewById(R.id.enter_name);
        mEmailEdit = (EditText) findViewById(R.id.enter_email);
        mPasswordEdit = (EditText) findViewById(R.id.enter_password);
        mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        SignUpButtonOnClick signUpButtonOnClick = new SignUpButtonOnClick();
        mSignUpButton.setOnClickListener(signUpButtonOnClick);
        mLoginButton = (Button) findViewById(R.id.login_button);
        LoginButtonOnClick loginButtonOnClick = new LoginButtonOnClick();
        mLoginButton.setOnClickListener(loginButtonOnClick);
        mSignUpTextView = (TextView) findViewById(R.id.sign_up_text);
        SignUpTextOnClick signUpTextOnClick = new SignUpTextOnClick();
        mSignUpTextView.setOnClickListener(signUpTextOnClick);
        Backendless.initApp(this, be_app_id, be_android_api_key);
    }


    @Override
    public void onBackPressed(){
        mSignUpButton.setVisibility(View.GONE);
        mNameEdit.setVisibility(View.GONE);
        mLoginButton.setVisibility(View.VISIBLE);
        mSignUpTextView.setVisibility(View.VISIBLE);
    }
    private class SignUpTextOnClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mSignUpButton.setVisibility(View.VISIBLE);
            mNameEdit.setVisibility(View.VISIBLE);
            mLoginButton.setVisibility(View.GONE);
            mSignUpTextView.setVisibility(View.GONE);
        }
    }


    private class LoginButtonOnClick implements View.OnClickListener{
        @Override
        public void onClick(final View view){
            String email = mEmailEdit.getText().toString();
            String password = mPasswordEdit.getText().toString();
            email = email.trim();
            password = password.trim();
            if (!email.isEmpty() && !password.isEmpty()){
                final ProgressDialog pDialog = ProgressDialog.show(LoginActivity.this,
                        "Please Wait!",
                        "Logging in...",
                        true);
                Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>(){
                    @Override
                    public void handleResponse (BackendlessUser response){
                        Toast.makeText(view.getContext(), response.getProperty("name") + " logged in successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, TripListActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void handleFault(BackendlessFault fault){
                        warnUser(fault.getMessage());
                        pDialog.dismiss();

                    }
                });
            }
            else{
                warnUser(getString(R.string.empty_field_signup_error));
            }
        }
    }

    private class SignUpButtonOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String userEmail = mEmailEdit.getText().toString();
            String password = mPasswordEdit.getText().toString();
            String name = mNameEdit.getText().toString();

            userEmail = userEmail.trim();
            password = password.trim();
            name = name.trim();

            if (!userEmail.isEmpty() &&!password.isEmpty() && !name.isEmpty()) {

             /* register the user in Backendless */
                BackendlessUser newUser = new BackendlessUser();
                newUser.setEmail(userEmail);
                newUser.setPassword(password);
                newUser.setProperty("name", name);
                final ProgressDialog pDialog = ProgressDialog.show(LoginActivity.this,
                        "Please Wait!",
                        "Creating a new account...",
                        true);

                Backendless.UserService.register(newUser,
                        new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse( BackendlessUser backendlessUser ) {
                                Log.i(TAG, "Registration successful for " + backendlessUser.getEmail());
                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            @Override
                            public void handleFault( BackendlessFault backendlessFault ) {
                                pDialog.dismiss();
                                warnUser(backendlessFault.getMessage());
                            }
                        } );

            }
            else {
                warnUser(getString(R.string.empty_field_signup_error));

            }
        }
    }

}


