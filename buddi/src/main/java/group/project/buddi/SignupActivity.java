package group.project.buddi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
 * Class to handle signup screen
 *
 * @author Team Buddi
 * @version 1.0
 */
public class SignupActivity extends AppCompatActivity {

    // Initialize variables
    private static final String TAG = "SignupActivity";
    private boolean signupSuccess = false;

    // Bind text fields and textviews to layout items
    @Bind(R.id.input_name)
    EditText _nameText;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_username)
    EditText _usernameText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;

    Context context = SignupActivity.this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Hide toolbar
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        // Set listeners for login and signup buttons
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    /**
     * Method to handle signing up
     */
    public void signup() {
        // Log line
        Log.d(TAG, "Signup");

        // Call signup failed method if signup not validated
        if (!validate()) {
            onSignupFailed();
            return;
        }

        // Set login button to enabled
        _signupButton.setEnabled(false);

        // Set up authenticating progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String username = _usernameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // Create new user on database through Ion
        Ion.with(context)
                .load("http://ec2-52-91-255-81.compute-1.amazonaws.com/api/v1/users")
                .setBodyParameter("name", name)
                .setBodyParameter("email", email)
                .setBodyParameter("username", username)
                .setBodyParameter("password", password)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // If an error occurred during request, print error
                        if (result.get("message").getAsString() != "Malformed request.") {

                            Toast.makeText(context, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            signupSuccess = true;
                            // If user by same credentials already exists
                        } else {
                            Toast.makeText(context, "User exists already.", Toast.LENGTH_SHORT).show();
                            signupSuccess = false;
                        }
                    }
                });

        // Delay for ensuring authentication
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    /**
     * Take user back to login screen if signup was successful
     */
    public void onSignupSuccess() {
        if (signupSuccess) {
            _signupButton.setEnabled(true);
            setResult(RESULT_OK, null);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Set signup button to true if signup failed
     */
    public void onSignupFailed() {
        _signupButton.setEnabled(true);
    }

    /**
     * Validation method
     *
     * @return boolean  whether text inputs are valid or not
     */
    public boolean validate() {
        boolean valid = true;

        // Initialize variables
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        // Check if name, username, email and password are valid
        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("At least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        // String pattern for username
        Pattern p = Pattern.compile("^[a-zA-Z0-9-_]+$");
        Matcher m = p.matcher(username);

        // Check fields
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
