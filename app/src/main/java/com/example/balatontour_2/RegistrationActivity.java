package com.example.balatontour_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegistrationActivity.class.getName();
    EditText usernameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordConfirmEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordConfirmEditText = findViewById(R.id.passwordConfirmEditText);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnItemSelectedListener((BottomNavigationView.OnItemSelectedListener) item -> {
            switch (item.getItemId()){
                case R.id.tours:
                    startActivity(new Intent(getApplicationContext(), Tours.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.profile:
                    return true;


            }

            return false;
        });
        mAuth = FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordConfirm = passwordConfirmEditText.getText().toString();

        if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
            Log.e(LOG_TAG,"The username, email, password must be given!");
            return;
        }

        if(!password.equals(passwordConfirm)){
            Log.e(LOG_TAG,"The two given password are not equal.");
            return;
        }


        //startActivity(new Intent(getApplicationContext(), LoggedInProfile.class));
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Log.i(LOG_TAG, " Created Username: "+username+", email: "+email);
                startActivity(new Intent(getApplicationContext(), LoggedInProfile.class));
            }else{
                Log.d(LOG_TAG, "User already exists!");
            }
        });

    }

    public void cancel(View view) {
        finish();
    }


}