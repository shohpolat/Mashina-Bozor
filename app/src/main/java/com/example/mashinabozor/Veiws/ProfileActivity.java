package com.example.mashinabozor.Veiws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mashinabozor.Classes.User;
import com.example.mashinabozor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {


    EditText full_name;
    Button save_btn;
    ImageButton back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        full_name = findViewById(R.id.edit_name_text);
        save_btn = findViewById(R.id.profile_save_btn);
        back_icon = findViewById(R.id.profile_back_icon);

        String path = "users" + "/" + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                full_name.setText(user.getFull_name());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!full_name.getText().toString().isEmpty()){
                    databaseReference.child("full_name").setValue(full_name.getText().toString());
                }
            }
        });

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}