package com.developer.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginOrSignUp extends AppCompatActivity {
    Button SignUp;
    TextView LogIn;
    TextView App;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_signup);
        SignUp=findViewById(R.id.SignUpBT);
        LogIn=findViewById(R.id.LoginTv);
        App=findViewById(R.id.App);
    }
    public void login(View view){
        Intent startActivity = new Intent(LoginOrSignUp.this, LogIn.class);
        Pair[] pair1=new Pair[2];
        pair1[0]=new Pair<View,String>(LogIn,"trans3");
        pair1[1]= new Pair<View,String>(App, "trans1");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(LoginOrSignUp.this,pair1);
        startActivity(startActivity,options.toBundle());
    }
    public void signup(View view){
        Intent startActivity = new Intent(LoginOrSignUp.this, SignUp.class);
        Pair[] pair=new Pair[2];
        pair[0]=new Pair<View,String>(SignUp,"trans2");
        pair[1]= new Pair<View,String>(App, "trans1");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(LoginOrSignUp.this,pair);
        startActivity(startActivity,options.toBundle());
    }
    public void onBackPressed(){
        finish();
    }
}
