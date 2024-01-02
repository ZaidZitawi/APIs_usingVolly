package comp483.ass2_comp438;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<User> arrayList;
    private EditText email;
    private EditText passL;
    private CheckBox chkBox;
    private TextView SignUpLable;
    private Button btnLogin;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String DATA = "DATA";

    private static final String PREF_NAME = "user_preferences";
    private static final String ARRAY_LIST_KEY = "user_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize views
        email = findViewById(R.id.usernameL);
        passL = findViewById(R.id.passL);
        chkBox = findViewById(R.id.chkBox);
        btnLogin = findViewById(R.id.btnLogin);
        SignUpLable = findViewById(R.id.SignUpLable);

        //Laod the arraylist from the Shared preferences
        loadArrayListFromPrefs();

        Log.d("MainActivity", "Object " + arrayList.get(0).toString());
        Log.d("MainActivity", "Object " + arrayList.get(1).toString());
        Log.d("MainActivity", "Object " + arrayList.get(2).toString());
//                    // Log the values to see if they are correct
//                    Log.d("MainActivity", "Received username: " + name);
//                    Log.d("MainActivity", "Received email: " + email2);
//                    Log.d("MainActivity", "Received password: " + password2);
//                    Log.d("MainActivity", "user Object " + user);
//                    Log.d("MainActivity", "Arraylist " + arrayList.get(0).toString());
//                    Log.d("MainActivity", "Register Value: " + isUserRegistered(arrayList, username, password));

        //load user email and password if he chose remember me
        loadUserCredentials();

        SignUpLable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to the SignUp activity
                Intent intent = new Intent(MainActivity.this, SignUP.class);
                startActivityForResult(intent, 1); // Use requestCode 1
            }
        });


        // Set click listener for the Login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String enteredEmail = email.getText().toString();
                String enteredPassword = passL.getText().toString();

                // Check if the "Remember Me" checkbox is checked
                if (chkBox.isChecked()) {
                    // Save user credentials if "Remember Me" is checked
                    saveUserCredentials(enteredEmail, enteredPassword);
                } else {
                    // Clear saved user credentials if "Remember Me" is unchecked
                    clearUserCredentials();
                }

                // Check if the entered credentials match any user in the arrayList
                if (arrayList != null && !arrayList.isEmpty() && isUserRegistered(arrayList, enteredEmail, enteredPassword)) {
                    // Redirect to the main activity or perform desired action
                    Intent intent = new Intent(MainActivity.this, APIs.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Display an error message or handle unsuccessful login
                    // For example:
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    // Method to check if the entered credentials match any user in the arrayList
    public boolean isUserRegistered(List<User> userList, String enteredUsername, String enteredPassword) {

        for (int i=0;i<userList.size();i++) {
            if (userList == null || enteredUsername == null || enteredPassword == null) {
//                throw new IllegalArgumentException("Invalid input parameters");
                Toast.makeText(MainActivity.this, "Arraylist is Null bitch", Toast.LENGTH_SHORT).show();
            }
            if (userList.get(i).getEmail().equals(enteredUsername) && userList.get(i).getPass().equals(enteredPassword)) {
                return true;
            }
        }
        return false;
    }

    // Method to save the arrayList to SharedPreferences
    private void saveArrayListToPrefs() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Convert the arrayList to a JSON string using Gson
        Gson gson = new Gson();
        String arrayListJson = gson.toJson(arrayList);

        // Save the JSON string in SharedPreferences
        editor.putString(ARRAY_LIST_KEY, arrayListJson);
        editor.apply();
    }

    // Method to load the arrayList from SharedPreferences
    private void loadArrayListFromPrefs() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Retrieve the JSON string from SharedPreferences
        String arrayListJson = preferences.getString(ARRAY_LIST_KEY, "");

        // Convert the JSON string back to an ArrayList using Gson
        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
        arrayList = gson.fromJson(arrayListJson, userListType);

        // Ensure arrayList is not null
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int id=0;

        //======================Important============================================
        //## Check if the requestCode matches the one used to start SignUp activity##
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Retrieve data from the intent
            String username = data.getStringExtra("username");
            String Email = data.getStringExtra("email");
            String password = data.getStringExtra("password");

            User user = new User(++id, username, Email, password);
            arrayList.add(user);

            saveArrayListToPrefs();
            Log.d("MainActivity", "Received username: " + username);
            Log.d("MainActivity", "Received email: " + Email);
            Log.d("MainActivity", "Received password: " + password);
            Log.d("MainActivity", "user Object " + user.toString());
        }
    }
    private void saveUserCredentials(String email, String password) {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("savedEmail", email);
        editor.putString("savedPassword", password);
        editor.apply();
    }

    private void loadUserCredentials() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedEmail = preferences.getString("savedEmail", "");
        String savedPassword = preferences.getString("savedPassword", "");

        // Autofill the email and password fields
        email.setText(savedEmail);
        passL.setText(savedPassword);
        chkBox.setChecked(!savedEmail.isEmpty()); // Check the checkbox if there are saved credentials
    }

    private void clearUserCredentials() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("savedEmail");
        editor.remove("savedPassword");
        editor.apply();
    }


}

//API For countries
//https://testapi.devtoolsdaily.com/countries/


//API for
//https://testapi.devtoolsdaily.com/companies/



//في فكرة حلوة اعتمدها، انو اعرض كل الشركات واقدر اعملهم عرض عن طريق اسم البلد