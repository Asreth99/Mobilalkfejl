package com.example.balatontour_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    private static final String LOG_TAG = RegistrationActivity.class.getName();
    private EditText usernameEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            Log.d(LOG_TAG, "Authenticated User!");
        }else {
            Log.d(LOG_TAG, "Unauthenticated User!");
            finish();
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener((BottomNavigationView.OnItemSelectedListener) item -> {
            switch (item.getItemId()) {
                case R.id.tours:
                    startActivity(new Intent(getApplicationContext(), Tours.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.profile:
                    return true;


            }

            return false;
        });
        mAuth = FirebaseAuth.getInstance();
    }

    public void logout(View view) {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoggedInProfile.class));
        finish();
    }
}