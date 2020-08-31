package com.example.mashinabozor.Login_SignUp_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mashinabozor.R;
import com.example.mashinabozor.Classes.User;
import com.example.mashinabozor.Veiws.Main_Page;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Verifiy_Activity extends AppCompatActivity {

    private String code_by_system;
    private String entered_name;
    private String entered_phone;
    private String entered_password;
    private String activity_type;

    TextInputEditText code;
    Button verify;
    ProgressBar progressBar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifiy_);

        code = findViewById(R.id.code_verify);
        verify = findViewById(R.id.verify_code_btn);
        progressBar = findViewById(R.id.progressBar);


        entered_phone = getIntent().getStringExtra(Sign_Up_Activity.PHONENUM_EXTRA);
        activity_type = getIntent().getStringExtra(Sign_Up_Activity.ACTIVITY_EXTRA);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String entered_code_by_user = code.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                verifyCodeByUser(entered_code_by_user);
            }
        });

        sendVerificationCodeToPhone(entered_phone);

    }

    private void sendVerificationCodeToPhone(String entered_phone) {


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                entered_phone,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            code_by_system = s;

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String entered_sms_by_user = phoneAuthCredential.getSmsCode();
            if (entered_sms_by_user != null) {
                progressBar.setVisibility(View.VISIBLE);
                verifyCodeByUser(entered_sms_by_user);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(Verifiy_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("TTT", e.getMessage());

        }
    };

    private void verifyCodeByUser(String entered_sms_by_user) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code_by_system, entered_sms_by_user);
        finishSignUpWithCode(credential);


    }

    private void finishSignUpWithCode(PhoneAuthCredential credential) {


        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    if (activity_type.equals("S")) {
                        entered_name = getIntent().getStringExtra(Sign_Up_Activity.NAME_EXTRA);
                        entered_password = getIntent().getStringExtra(Sign_Up_Activity.PASSWORD_EXTRA);
                        User user = new User(entered_name, entered_phone, entered_password);
                        databaseReference.child(entered_phone).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(Verifiy_Activity.this, Main_Page.class);
                                intent.putExtra(Sign_Up_Activity.PHONENUM_EXTRA, entered_phone);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });
                    } else {

                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(Verifiy_Activity.this, Main_Page.class);
                        intent.putExtra(Sign_Up_Activity.PHONENUM_EXTRA, entered_phone);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(Verifiy_Activity.this, "Noto'g'ri Kod!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

}