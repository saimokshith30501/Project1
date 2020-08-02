package com.developer.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class LogIn extends AppCompatActivity {
    TextInputLayout email,password;
    MaterialButton LogIn;
    ImageButton BackButton;
    TextView BackToSignUp;
    TextView AppName,LoginTv,SloganTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        LogIn=findViewById(R.id.log_loginBt);
        BackToSignUp=findViewById(R.id.log_backToSign);
        AppName=findViewById(R.id.app_name);
        LoginTv=findViewById(R.id.welcome);
        SloganTv=findViewById(R.id.slogan);
        AppName.setGravity(Gravity.CENTER_HORIZONTAL);
        email=findViewById(R.id.log_emailTv);
        password=findViewById(R.id.log_paswordTv);
        BackButton=findViewById(R.id.back_log_login);
        BackToSignUp.setText(Html.fromHtml("New User? <u>SignUp</u>"));
    }
    public void setBackButton(View view){
        Intent startActivity = new Intent(LogIn.this,LoginOrSignUp.class);
        Pair[] pair=new Pair[1];
        pair[0]= new Pair<View,String>(findViewById(R.id.app_name), "trans1");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(LogIn.this,pair);
        startActivity(startActivity,options.toBundle());
    }
    public void callSignUpActivity(View view){
        Intent startActivity = new Intent(LogIn.this,SignUp.class);
        Pair[] pair=new Pair[8];
        pair[0]= new Pair<View,String>(AppName, "trans1");
        pair[1]= new Pair<View,String>(LoginTv, "title1");
        pair[2]= new Pair<View,String>(SloganTv, "title2");
        pair[3]= new Pair<View,String>(email, "fields");
        pair[4]= new Pair<View,String>(password, "pfields");
        pair[5]= new Pair<View,String>(LogIn, "button_trans");
        pair[6]= new Pair<View,String>(BackToSignUp, "title3");
        pair[7]= new Pair<View,String>(BackButton, "backTr");
        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(LogIn.this,pair);
        startActivity(startActivity,options.toBundle());
    }
}
