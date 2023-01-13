package com.example.datingapp.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.datingapp.MainActivity;
import com.example.datingapp.R;
import com.example.datingapp.databinding.ActivityLoginBinding;
import com.example.datingapp.utils.Dialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private String VerificationId=null;
     private Dialog dialog;

//    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());







        binding.sendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.userNumber.getText().toString().isEmpty())
                    binding.userNumber.setError("Please enter your number");
                else
                {
                   sendOpt(binding.userNumber.getText().toString());


                }

            }


        });

        binding.verifyOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.userOtp.getText().toString().isEmpty())
                    binding.userOtp.setError("Please enter your OTP");
                else
                {
                    verifyOtp(binding.userOtp.getText().toString());


                }

            }


        });

    }

    private void verifyOtp(String otp) {
//binding.sendOtpBtn.showLoadingButton();
         dialog =new Dialog(LoginActivity.this);


        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationId, otp);
                   signInWithPhoneAuthCredential(credential);

    }


    private void sendOpt(String number) {


        dialog =new Dialog(LoginActivity.this);
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                  signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {


                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {

                VerificationId = verificationId;
              //  PhoneAuthProvider.ForceResendingToken mResendToken = token;

              //  binding.sendOtpBtn.showNormalButton();
                dialog.Dismiss();

                binding.numberLayout.setVisibility(View.GONE);
                binding.otpLayout.setVisibility(View.VISIBLE);


            }
        };


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+91"+number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            checkUserExits(binding.userNumber.getText().toString());
                        } else {
                            dialog.Dismiss();

                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


        });
    }

    private void checkUserExits(String number ){

        FirebaseDatabase ref=FirebaseDatabase.getInstance();


        ref.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    dialog.Dismiss();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
                else
                {     dialog.Dismiss();
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    finish();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}