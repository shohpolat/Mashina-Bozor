package com.example.mashinabozor.Login_SignUp_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mashinabozor.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class RecoverPassword extends AppCompatActivity {

    TextInputEditText phone_number, new_password, new_password_code;
    Button next;
    String button_text = "keyingi";
    private boolean isNumberOk;
    private String code_by_system;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        phone_number = findViewById(R.id.new_password_phone_edit_text);
        new_password = findViewById(R.id.new_password_edit_text);
        new_password_code = findViewById(R.id.new_password_code_edit_text);
        next = findViewById(R.id.recover_next_btn);
        progressBar = findViewById(R.id.recover_progressbar);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TTT","bosildi ");
                final String phone_number_string = phone_number.getText().toString().trim();
                if (next.getText().toString().equals("keyingi")) {
                    if (!new_password.getText().toString().isEmpty()) {

                        Log.d("TTT","password bor");
                        if (phone_number_string.length() != 13 || !phone_number_string.startsWith("+998")) {
                            if (phone_number_string.isEmpty()) {
                                phone_number.setError("Telefon raqamni kiriting!");
                            } else {
                                phone_number.setError("Noto'g'ri raqam kiritildi!");
                            }
                            phone_number.requestFocus();
                        } else {
                            phone_number.setError(null);
                        }

                        final Query checkUser = FirebaseDatabase.getInstance().getReference("users").orderByChild("phone_number").equalTo(phone_number_string);

                        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()) {

                                    Log.d("TTT","number ok");
                                    new_password_code.setVisibility(View.VISIBLE);
                                    next.setText("tasdiqlash");
                                    sendVerificationCode(phone_number_string);

                                } else {

                                    phone_number.setError("Ro'yhatda bunday raqam mavjud emas!");
                                    phone_number.requestFocus();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {


                            }
                        });


                    } else {
                        if (new_password.getText().toString().isEmpty()) {

                            new_password.setError("yangi parolni kiriting!");
                            new_password.requestFocus();
                        }
                        if (phone_number.getText().toString().isEmpty()) {
                            phone_number.setError("telefon raqamni kiriting!");
                            phone_number.requestFocus();
                        }
                    }
                } else {

                    Log.d("TTT","bosildi 2");
                    if (verifyCode(new_password_code.getText().toString())){
                        changepassword();
                    }

                }

            }
        });

    }

    private boolean checkPhoneNumber(final String phone_number_string) {




        return isNumberOk;

    }

    private void sendVerificationCode(String phone_number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone_number,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            code_by_system = s;

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String entered_code_by_user = phoneAuthCredential.getSmsCode();
            if (entered_code_by_user != null) {
                progressBar.setVisibility(View.VISIBLE);

                    changepassword();

            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(RecoverPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("TTT", e.getMessage());

        }
    };

    private boolean verifyCode(String code_by_user) {
        boolean isCodeSame;
        if (code_by_user.equals(code_by_system)) {
            isCodeSame = true;
        } else {
            isCodeSame = false;
        }
        return isCodeSame;
    }

    private void changepassword(){

        Log.d("TTT","change password");
        String new_passwrord_string = new_password.getText().toString().trim();
        String phone_number_string = phone_number.getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.child(phone_number_string).child("password").setValue(new_passwrord_string).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RecoverPassword.this);
                builder.setTitle("Parol muvafffaqiyatli yangilandi!")
                        .setPositiveButton("yaxshi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        return i == keyEvent.KEYCODE_BACK;
                    }
                });

                dialog.show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TTT",e.getMessage());
            }
        });

    }

}