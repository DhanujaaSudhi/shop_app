package com.android.shoppingzoo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.shoppingzoo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otp extends AppCompatActivity {
    private EditText inputcode1,inputcode2,inputcode3,inputcode4,inputcode5,inputcode6;
    private String verificationId;
    Button verify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        TextView mobile=findViewById(R.id.textMobile);
        mobile.setText(String.format(
                "+91-%s",getIntent().getStringExtra("phone")
        ));
        inputcode1=findViewById(R.id.inputcode1);
        inputcode2=findViewById(R.id.inputcode2);
        inputcode3=findViewById(R.id.inputcode3);
        inputcode4=findViewById(R.id.inputcode4);
        inputcode5=findViewById(R.id.inputcode5);
        inputcode6=findViewById(R.id.inputcode6);
        verify=findViewById(R.id.verify);
        setupOTPInputs();
        verificationId=getIntent().getStringExtra("verificationId");

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputcode1.getText().toString().trim().isEmpty()
                        ||inputcode2.getText().toString().trim().isEmpty()||
                        inputcode3.getText().toString().trim().isEmpty()||
                        inputcode4.getText().toString().trim().isEmpty()||
                        inputcode5.getText().toString().trim().isEmpty()||
                        inputcode6.getText().toString().trim().isEmpty()){
                    Toast.makeText(otp.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    String code=inputcode1.getText().toString()+
                            inputcode2.getText().toString()+
                            inputcode3.getText().toString()+
                            inputcode4.getText().toString()+
                            inputcode5.getText().toString()+
                            inputcode6.getText().toString();
                    if (verificationId !=null){
                        PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(
                                verificationId,
                                code
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Intent i =new Intent(getApplicationContext(),LoginActivity.class);
                                            i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(i);
                                        }else {
                                            Toast.makeText(otp.this, "the verification code is invalid", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }
                }
            }
        });



    }
    private void setupOTPInputs(){
        inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputcode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}