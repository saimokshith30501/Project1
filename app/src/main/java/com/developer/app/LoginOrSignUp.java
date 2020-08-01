package com.developer.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginOrSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_signup);
    }
    public void login(View view){
        Intent startActivity = new Intent(LoginOrSignUp.this, LogIn.class);
        startActivity(startActivity);
    }
    public void signup(View view){
        Intent startActivity = new Intent(LoginOrSignUp.this, SignUp.class);
        startActivity(startActivity);
    }
}
