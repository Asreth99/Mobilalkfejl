package com.example.balatontour_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedInProfile extends AppCompatActivity {

    private static final String LOG_TAG = RegistrationActivity.class.getName();
    private EditText usernameEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            Log.d(LOG_TAG, "Authenticated User!");
        }else {
            Log.d(LOG_TAG, "Unauthenticated User!");
        }

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        mAuth = FirebaseAuth.getInstance();
    }

    public void accountCreate(View view) {
        startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
        overridePendingTransition(0, 0);
    }

    public void login(View view) {
        String userEmail = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mAuth.signInWithEmailAndPassword(userEmail, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(LOG_TAG, "Successfully logged in!");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                Log.i(LOG_TAG, "You failed to login, please check if you write the email, and password correct.");
            }
        });
    }
}