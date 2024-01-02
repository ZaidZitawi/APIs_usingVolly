package comp483.ass2_comp438;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SignUP extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText passS;
    private Button btnSignUp;
    private boolean flage=false;
    ArrayList<User> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        passS = findViewById(R.id.passS);
        btnSignUp = findViewById(R.id.btnSignUp);
        loadData();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flage=true;
                String username = name.getText().toString();
                String Email = email.getText().toString();
                String password = passS.getText().toString();
                Log.d("email", email + "");

                // Perform input validation
                if (username.isEmpty()) {
                    name.setError("Please enter a username");
                    name.requestFocus();
                    return;
                }

                if (Email.isEmpty()) {
                    email.setError("Please enter an email address");
                    email.requestFocus();
                    return;
                } else {
                    // Validate email format using regex
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (!Email.matches(emailPattern)) {
                        email.setError("Invalid email address");
                        email.requestFocus();
                        return;
                    }
                }

                if (password.isEmpty()) {
                    passS.setError("Please enter a password");
                    passS.requestFocus();
                    return;
                } else {
                    // Validate password length
                    if (password.length() < 5) {
                        passS.setError("Password must be at least 5 characters long");
                        passS.requestFocus();
                        return;
                    }
                }


                Intent intent = new Intent(SignUP.this, MainActivity.class);
                // Pass the data using putExtra

                intent.putExtra("username", username);
                intent.putExtra("email", Email);
                intent.putExtra("password", password);

                // Set the result code and data
                setResult(RESULT_OK, intent);

                // Finish the SignUpActivity
                finish();
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        saveData();
        if (flage)
            clearData();
    }
    private void saveData() {
        // Get the SharedPreferences instance
        SharedPreferences preferences = getSharedPreferences("SignUpData", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Save the entered data
        editor.putString("username", name.getText().toString());
        editor.putString("email", email.getText().toString());
        editor.putString("password", passS.getText().toString());

        // Apply the changes
        editor.apply();
    }
    private void loadData() {
        // Get the SharedPreferences instance
        SharedPreferences preferences = getSharedPreferences("SignUpData", MODE_PRIVATE);

        // Load the data and set it to the corresponding EditText fields
        name.setText(preferences.getString("username", ""));
        email.setText(preferences.getString("email", ""));
        passS.setText(preferences.getString("password", ""));
    }
    private void clearData() {
        // Get the SharedPreferences instance
        SharedPreferences preferences = getSharedPreferences("SignUpData", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Clear the saved data
        editor.remove("username");
        editor.remove("email");
        editor.remove("password");
        editor.clear();
        editor.apply();
    }
}