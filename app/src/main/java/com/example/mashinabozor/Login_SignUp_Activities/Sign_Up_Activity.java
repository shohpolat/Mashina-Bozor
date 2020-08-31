package com.example.mashinabozor.Login_SignUp_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mashinabozor.R;
import com.google.android.material.textfield.TextInputEditText;

public class Sign_Up_Activity extends AppCompatActivity {

    public static final String NAME_EXTRA = "entered_name";
    public static final String PHONENUM_EXTRA = "entered_phone_number";
    public static final String PASSWORD_EXTRA = "entered_password";
    public static final String ACTIVITY_EXTRA = "activity_extra";

    TextView greeting;
    TextInputEditText name, phone, password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up_);

        name = findViewById(R.id.sign_up_name);
        phone = findViewById(R.id.sign_up_phone);
        password = findViewById(R.id.sign_up_password);
        register = findViewById(R.id.sign_up_register);
        greeting = findViewById(R.id.sign_up_salom);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String entered_name = name.getText().toString();
                String entered_phone = phone.getText().toString().trim();
                String entered_password = password.getText().toString().trim();

                if (entered_name.isEmpty() || entered_name.length()>=15){
                    if (entered_name.isEmpty()) {
                        name.setError("Ism kiritilmadi!");
                    }else {
                        name.setError("Ism juda ham uzun!");
                    }
                    name.requestFocus();
                    return;
                }else {
                    name.setError(null);
                }

                if (!entered_phone.startsWith("+998") || entered_phone.length() != 13){
                    if (entered_phone.isEmpty()){
                        phone.setError("Telefon raqam kiritilmadi!");
                    }else {
                        phone.setError("Noto'g'ri raqam kiritildi!");
                    }
                    phone.requestFocus();
                    return;
                }else {
                    phone.setError(null);
                }

                if (entered_password.isEmpty() || entered_password.length()<5){
                    if (entered_password.isEmpty()){
                        password.setError("Parol kiritilmadi!");
                    }else {
                        password.setError("Parol juda ham qisqa!");
                    }
                    password.requestFocus();
                    return;
                }else {
                    password.setError(null);
                }

                Intent intent = new Intent(Sign_Up_Activity.this, Verifiy_Activity.class);

                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(greeting, "salom");
                pairs[1] = new Pair<View, String>(name, "telefon_raqam");
                pairs[2] = new Pair<View, String>(register, "login_tran");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Sign_Up_Activity.this, pairs);
                    intent.putExtra(NAME_EXTRA,entered_name);
                    intent.putExtra(PHONENUM_EXTRA,entered_phone);
                    intent.putExtra(PASSWORD_EXTRA  ,entered_password);
                    intent.putExtra(ACTIVITY_EXTRA,"S");
                    startActivity(intent,options.toBundle());
                }


            }
        });


    }
}