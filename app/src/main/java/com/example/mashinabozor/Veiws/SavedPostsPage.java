package com.example.mashinabozor.Veiws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mashinabozor.Adapters.Saved_Posts_Adapter;
import com.example.mashinabozor.Classes.Post;
import com.example.mashinabozor.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SavedPostsPage extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton back_icon;
    Saved_Posts_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_posts_page);

        recyclerView = findViewById(R.id.saved_posts_recyclerview);
        back_icon = findViewById(R.id.saved_posts_back_icon);

        adapter = new Saved_Posts_Adapter();

        adapter.setOnClickListener(new Saved_Posts_Adapter.onClickListener() {
            @Override
            public void onclick(Post post, long position) {
                Intent intent = new Intent(SavedPostsPage.this, Post_Home_Page.class);
                intent.putExtra("single_post", post);
                startActivity(intent);
            }

            @Override
            public void onBookmarkClick(final Post post, final long position) {

                String path = "saved_posts" + "/" + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() + "/" + post.getPostId();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path);
                databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SavedPostsPage.this, "e'lon saqlanganlardan olib tashlandi!!", Toast.LENGTH_SHORT).show();
                        adapter.refReshPosts();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TTT", e.getMessage());
                    }
                });


            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}