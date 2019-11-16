package codepath.com.myinstegram;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    private static String TAG = "SignUpActivity";
    private EditText etEmail;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (email.length()== 0 || username.length() == 0 || password.length() == 0){
                    Toast.makeText(SignUpActivity.this, "Please enter above information", Toast.LENGTH_LONG).show();
                    return;
                }

                SignUp(email, username,password);

                Toast.makeText(SignUpActivity.this, "You sign Up successfully", Toast.LENGTH_LONG).show();

            }
        });


    }

    private void SignUp(String email, String username, String password) {

        ParseUser user = new ParseUser();

        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
            }
        );
                etEmail.setText("");
                etUsername.setText("");
                etPassword.setText("");
            }
    }

