package com.developer.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView AppName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppName=findViewById(R.id.App);
        Animation appName= AnimationUtils.loadAnimation(this,R.anim.animation_app_name);
        AppName.setAnimation(appName);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent startActivity = new Intent(MainActivity.this, LoginOrSignUp.class);
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,AppName,"trans1");
                startActivity(startActivity,options.toBundle());
                finish();
            }
        },2000);
    }
}
