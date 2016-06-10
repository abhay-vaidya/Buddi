package group.project.buddi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Class to handle login screen
 *
 * @author Team Buddi
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {

    // Initialize variables
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    public boolean isLoggedin = false;
    public boolean canProceed = false;
    public String auth_token;

    // Bind text fields and textviews to layout items
    @Bind(R.id.input_username)
    EditText _usernameText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Hide toolbar
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // Set listeners for login and signup buttons
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);

            }
        });
    }

    /**
     * Method to handle logging in
     */
    public void login() {
        // Log line
        Log.d(TAG, "Login");

        // Call login failed method if login not validated
        if (!validate()) {
            onLoginFailed();
            return;
        }

        // Set login button to enabled
        _loginButton.setEnabled(false);

        // Set up authenticating progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // Store in shared preferences
        Context context = LoginActivity.this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.oauth), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("grant_type", "password");
        editor.putString("client_id", "f3d259ddd3ed8ff3843839b");
        editor.putString("client_secret", "4c7f6f8fa93d59c45502c0ae8c4a95b");
        editor.putString("username", _usernameText.getText().toString());
        editor.putString("password", _passwordText.getText().toString());
        editor.commit();

        // Authenticate via API
        Ion.with(context)
                .load("http://ec2-52-91-255-81.compute-1.amazonaws.com/oauth/access_token")
                .setBodyParameter("grant_type", sharedPref.getString("grant_type", null))
                .setBodyParameter("client_id", sharedPref.getString("client_id", null))
                .setBodyParameter("client_secret", sharedPref.getString("client_secret", null))
                .setBodyParameter("username", sharedPref.getString("username", null))
                .setBodyParameter("password", sharedPref.getString("password", null))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            auth_token = result.get("access_token").getAsString();

                            // Store in shared preferences
                            Context context = LoginActivity.this;
                            SharedPreferences sharedPref = context.getSharedPreferences(
                                    getString(R.string.oauth), Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("auth_token", auth_token);
                            editor.commit();

                            // Store user info for later
                            getInfo();

                            canProceed = true;

                        } catch (Exception x) {
                            Toast.makeText(LoginActivity.this, "Not logging in.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        // Delay for ensuring authentication
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if (canProceed) {
                            onLoginSuccess();
                        } else onLoginFailed();

                        progressDialog.dismiss();
                    }
                }, 1500);
    }

    /**
     * Method to get user information
     */
    private void getInfo() {

        Context context = LoginActivity.this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.oauth), Context.MODE_PRIVATE);

        // Get user information
        Ion.with(LoginActivity.this)
                .load("http://ec2-52-91-255-81.compute-1.amazonaws.com/api/v1/account?access_token=" + sharedPref.getString("auth_token", "broke"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // Set user information
                        try {
                            String name = result.get("name").getAsString();
                            String email = result.get("email").getAsString();
                            String code = null;
                            if (!result.get("code").isJsonNull()) {
                                code = result.get("code").getAsString();
                            }

                            Context context = LoginActivity.this;
                            SharedPreferences sharedPref = context.getSharedPreferences(
                                    getString(R.string.oauth), Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("email", email);
                            editor.putString("name", name);
                            if (code != null) {
                                editor.putString("code", code);
                            }
                            editor.commit();

                        } catch (Exception x) {
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // By default we just finish the Activity and take them back to login screen
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the HomeActivity
        moveTaskToBack(true);
    }

    /**
     * Method to handle when login is successful
     */
    public void onLoginSuccess() {

        // Set login button and boolean to true
        _loginButton.setEnabled(true);
        isLoggedin = true;

        // Store in shared preferences that user has logged in
        Context context = LoginActivity.this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.oauth), Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("is_logged_in", isLoggedin);

        // Store user information
        editor.putString("grant_type", "password");
        editor.putString("client_id", "f3d259ddd3ed8ff3843839b");
        editor.putString("client_secret", "4c7f6f8fa93d59c45502c0ae8c4a95b");
        editor.putString("username", _usernameText.getText().toString());
        editor.putString("password", _passwordText.getText().toString());

        editor.commit();

        // Check to see if code is set already
        String code = sharedPref.getString("code", null);

        Intent intent = null;

        // If user has a matching code already take them to home screen, otherwise take them to quiz
        if (code == null) {
            intent = new Intent(this, QuizActivity.class);
        } else {
            intent = new Intent(this, HomeActivity.class);
        }

        startActivity(intent);
        finish();
    }

    /**
     * Method to handle when login fails
     */
    public void onLoginFailed() {
        _loginButton.setEnabled(true);
        // Make toast telling user login has failed
        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Validation method
     *
     * @return boolean  whether text inputs are valid or not
     */
    public boolean validate() {

        // Initialize variables
        boolean valid = true;
        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        // String pattern for username
        Pattern p = Pattern.compile("^[a-zA-Z0-9-_]+$");
        Matcher m = p.matcher(username);

        // Check if username and password are valid
        if (username.isEmpty() || !m.matches()) {
            _usernameText.setError("Enter a valid username");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError("Greater than 4 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }
}