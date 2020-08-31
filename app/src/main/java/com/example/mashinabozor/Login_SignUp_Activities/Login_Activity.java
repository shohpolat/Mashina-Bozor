package com.example.mashinabozor.Login_SignUp_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mashinabozor.R;
import com.example.mashinabozor.Veiws.Main_Page;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Inet4Address;

public class Login_Activity extends AppCompatActivity {

    ImageView malibu;
    TextView greeting;
    TextInputEditText phone, password;
    Button login_btn, register_btn, forget_btn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity);


        greeting = findViewById(R.id.salom);
        phone = findViewById(R.id.login_phone);
        password = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_login);
        register_btn = findViewById(R.id.login_register);
        malibu = findViewById(R.id.malibu);
        forget_btn = findViewById(R.id.forget_password);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login_Activity.this, Sign_Up_Activity.class);

                Pair[] pairs = new Pair[5];
                pairs[0] = new Pair<View, String>(greeting, "salom");
                pairs[1] = new Pair<View, String>(phone, "telefon_raqam");
                pairs[2] = new Pair<View, String>(password, "parol");
                pairs[3] = new Pair<View, String>(login_btn, "login_tran");
                pairs[4] = new Pair<View, String>(malibu, "malibu");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login_Activity.this, pairs);
                    startActivity(intent, options.toBundle());
                }


            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String entered_phone = phone.getText().toString().trim();
                final String entered_password = password.getText().toString().trim();

                if (entered_phone.length() != 13 || !entered_phone.startsWith("+998")) {
                    if (entered_phone.isEmpty()) {
                        phone.setError("Telefon raqamni kiriting!");
                    } else {
                        phone.setError("Noto'g'ri raqam kiritildi!");
                    }
                    phone.requestFocus();
                    return;
                } else {
                    phone.setError(null);
                }

                if (entered_password.isEmpty()) {
                    password.setError("parol kiritilmadi!");
                    password.requestFocus();
                } else {
                    password.setError(null);
                }

                final Query checkUser = databaseReference.orderByChild("phone_number").equalTo(entered_phone);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {

                            String passwrodFromDb = dataSnapshot.child(entered_phone).child("password").getValue(String.class);

                            if (entered_password.equals(passwrodFromDb)) {

                                Intent intent = new Intent(Login_Activity.this,Verifiy_Activity.class);
                                intent.putExtra(Sign_Up_Activity.PHONENUM_EXTRA,entered_phone);
                                intent.putExtra(Sign_Up_Activity.ACTIVITY_EXTRA,"L");
                                startActivity(intent);

                            } else {
                                password.setError("parol xato!");
                                password.requestFocus();
                            }

                        } else {
                            phone.setError("Ro'yhatda bunday raqam mavjud emas!");
                            phone.requestFocus();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(Login_Activity.this, "Kirish bekor qilindi!", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        forget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this, RecoverPassword.class));
            }
        });

    }

}