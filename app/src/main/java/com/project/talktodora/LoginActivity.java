package com.project.talktodora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity{
    TextInputEditText numberEt,countryCodeEt;
    ImageButton goIb;
    FirebaseAuth firebaseAuth;
    String codesent;
    String codeEnteredByUser;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        numberEt=findViewById(R.id.numbertv);
        countryCodeEt=findViewById(R.id.codeTv);
        goIb=findViewById(R.id.button2);
        goIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
                startDialog();

            }
        });
    }

    private void startDialog() {
        final MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(this);
        final EditText editText=new EditText(this);
        builder.setView(editText);
        builder.setMessage("Enter the code you received");
        builder.setCancelable(false);
        builder.setPositiveButton("verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                codeEnteredByUser=editText.getText().toString().trim();
                verifysignincode();
            }
        });
        builder.show();
    }

    private void sendVerificationCode() {
        if(numberEt.getText()!=null) {
            String number = numberEt.getText().toString().trim();
            if ( number.length() ==10) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91"+number,
                        120,
                        TimeUnit.SECONDS
                        , this
                        , callbacks
                );
            }
            else{
                Toast.makeText(this, "please enter a 10 digits no", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "please enter a 10 digits no", Toast.LENGTH_SHORT).show();
        }
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            //Toast.makeText(LoginActivity.this, "failed "+e, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onVerificationFailed: ",e );
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            codesent=s;
            //Toast.makeText(LoginActivity.this, "code sent is"+s, Toast.LENGTH_SHORT).show();
            super.onCodeSent(s, forceResendingToken);

        }
    };
    public void verifysignincode(){
        if(codesent!=null) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesent, codeEnteredByUser);
            signInWithPhoneAuthCredential(credential);
        }
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            startActivity(new Intent(LoginActivity.this,welcome.class));
                            Toast.makeText(LoginActivity.this, "login Successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}