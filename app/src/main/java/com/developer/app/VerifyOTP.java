package com.developer.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;

public class VerifyOTP extends AppCompatActivity {
    String fname,email,phone,password;
    PinView pinFromUser;
    ImageButton Backtosignup;
    MaterialButton VerifyOtp;
    TextView otp,verification,detecting;
    GifImageView phoneVerification;
    LottieAnimationView phoneVerified;
    String codeBySystem;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyotp);
        fname = getIntent().getStringExtra("FNAME");
        email = getIntent().getStringExtra("EMAIL");
        phone = getIntent().getStringExtra("PHONE");
        password = getIntent().getStringExtra("PASSWORD");
        phoneVerified = findViewById(R.id.phone_verified);
        phoneVerification = findViewById(R.id.phone_verificaction);
        sendVerificationCodeToUser(phone);
        pinFromUser = findViewById(R.id.pin_view);
        Backtosignup = findViewById(R.id.close_verify);
        VerifyOtp = findViewById(R.id.verifyOtpButton);
        otp = findViewById(R.id.verify_otp);
        verification = findViewById(R.id.verify_verification);
        detecting = findViewById(R.id.verify_detect);
        Backtosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivity = new Intent(VerifyOTP.this, SignUp.class);
                Pair[] pair = new Pair[2];
                pair[0] = new Pair<View, String>(Backtosignup, "backTr");
                pair[1] = new Pair<View, String>(VerifyOtp, "button_trans");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(VerifyOTP.this, pair);
                startActivity(startActivity, options.toBundle());
            }
        });
        VerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = pinFromUser.getText().toString();
                if (!code.isEmpty()) {
                    verifyCode(code);
                } else {
                    pinFromUser.setError("INCORRECT OTP");
                }
            }
        });
    }

    private void sendVerificationCodeToUser(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if (code!=null){
                pinFromUser.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(VerifyOTP.this, "SENT", Toast.LENGTH_LONG).show();
            codeBySystem=s;
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codeBySystem,code);
        signInUsingCredential(credential);
    }

    private void signInUsingCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyOTP.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            otp.setVisibility(View.GONE);
                            detecting.setVisibility(View.GONE);
                            verification.setVisibility(View.GONE);
                            phoneVerification.setVisibility(View.GONE);
                            VerifyOtp.setVisibility(View.GONE);
                            pinFromUser.setVisibility(View.GONE);
                            Backtosignup.setVisibility(View.GONE);
                            phoneVerified.setVisibility(View.VISIBLE);
                            regUser();
                        }
                        else {
                            Toast.makeText(VerifyOTP.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }
    private void regUser() {
        mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user=mAuth.getCurrentUser();
                            String uid=user.getUid();
                            final String email=user.getEmail();
                            HashMap<Object,String> hashMap= new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("fullname",fname);
                            hashMap.put("phone",phone);
                            reference=firebaseDatabase.getReference("Users");
                            reference.child(uid).setValue(hashMap);
                            new Handler().postDelayed(new Runnable(){
                                @Override
                                public void run(){
                                    Toast.makeText(VerifyOTP.this, "Signed in as "+email,Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(), Profile.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },3000);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(VerifyOTP.this, "Authentication failed."+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VerifyOTP.this, "Failed to create"+e.getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
