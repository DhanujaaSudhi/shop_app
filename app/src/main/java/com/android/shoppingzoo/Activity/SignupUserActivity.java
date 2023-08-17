package com.android.shoppingzoo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.shoppingzoo.Model.User;
import com.android.shoppingzoo.Model.Utils;
import com.android.shoppingzoo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class SignupUserActivity extends AppCompatActivity {
    private static final String TAG = "signupTag";
    User user;
    //Firebase
    FirebaseAuth mAuth;
    DatabaseReference myRootRef;
    String userName, userEmail, userPass,userAddress,userphone;
    private EditText name, email, pass,address,phone;
    private Button SignUPBtn, GoToLoginBtn;
    private ProgressBar progressBar;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private int selectedGender=1;
    RadioGroup radioGroupGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);

        initAll();
        settingUpListners();
    }

    private void settingUpListners() {
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.male_radio:
                        selectedGender=1;
                        break;
                    case R.id.female_radio:
                        selectedGender=0;
                        break;
                }
            }
        });

        GoToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupUserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        SignUPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = name.getText().toString().trim();
                userEmail = email.getText().toString().trim();
                userPass = pass.getText().toString().trim();
                userAddress = address.getText().toString().trim();
                userphone=phone.getText().toString().trim();

                if (TextUtils.isEmpty(userName)) {
                    name.setError("Enter Full name");
                } else if (TextUtils.isEmpty(userEmail)) {
                    email.setError("Enter email");
                }
                else if (!userEmail.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Invalid email address, enter valid email id", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(userPass)) {
                    pass.setError("Enter pass");
                }
                else if (TextUtils.isEmpty(userphone)) {
                    pass.setError("Enter phone number");
                }
                else if (TextUtils.isEmpty(userAddress)) {
                    address.setError("Enter your address");
                }
                else {
                    //signup code goes here
                    RegisterNewAccount();
                }
            }
        });
    }

    private void RegisterNewAccount() {
        //creating new account on firebase for user
        progressBar.setVisibility(View.VISIBLE);
        SignUPBtn.setVisibility(View.GONE);

        user.setName(userName);
        user.setEmail(userEmail);
        user.setPass(userPass);
        user.setAddress(userAddress);
        user.setGender(selectedGender);
        user.setPhone(userphone);
        user.setUserType("user");
        user.setPhotoUrl("");

        //creating account
        mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            user.setUserId(currentUserId);
                            myRootRef.child("Users").child(currentUserId).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //show message
                                    Toast.makeText(SignupUserActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                                    Paper.book().write("user", user);
                                    Paper.book().write("active", "user");

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NotNull Exception e) {
                                    Log.d(TAG, e.toString());
                                }
                            });
                        } else {
                            Toast.makeText(SignupUserActivity.this, "Failed to Create Account..!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        FirebaseAuth auth = FirebaseAuth.getInstance();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91"+userphone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        // Save the verification id somewhere
                        Intent i=new Intent(getApplicationContext(),otp.class);
                              i.putExtra("phone",phone.getText().toString());
                                   i.putExtra("verificationId",verificationId);
                          startActivity(i);

                        // The corresponding whitelisted code above should be used to complete sign-in.
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // Sign in with the credential
                        // ...
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // ...
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private void initAll() {
        //casting
        name = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        pass = findViewById(R.id.signp_pass);
        phone=findViewById(R.id.signup_phone);
        progressBar = findViewById(R.id.signup_progressbar);
        SignUPBtn = findViewById(R.id.signup_btn);
        GoToLoginBtn = findViewById(R.id.already_have_account_btn);
        address = findViewById(R.id.location_et);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroup1);
        //initialize mauth
        mAuth = FirebaseAuth.getInstance();
        //getting path
        myRootRef = FirebaseDatabase.getInstance().getReference();
        //initialize function
        user = new User();

        Utils.statusBarColor(SignupUserActivity.this);
    }
}