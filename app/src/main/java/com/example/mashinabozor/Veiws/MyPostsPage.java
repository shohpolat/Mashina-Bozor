package com.example.mashinabozor.Veiws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mashinabozor.Adapters.MyPostsAdapter;
import com.example.mashinabozor.Classes.Post;
import com.example.mashinabozor.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyPostsPage extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton back_icon;
    MyPostsAdapter adapter;
    private boolean isExist;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts_page);

        recyclerView = findViewById(R.id.my_posts_recyclerview);
        back_icon = findViewById(R.id.my_posts_back_icon);
        toolbar = findViewById(R.id.my_posts_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        adapter = new MyPostsAdapter();
        adapter.setOnClickListener(new MyPostsAdapter.onClickListener() {
            @Override
            public void onclick(Post post, long position) {
                Intent intent = new Intent(MyPostsPage.this, Post_Home_Page.class);
                intent.putExtra("single_post", post);
                startActivity(intent);
            }

            @Override
            public void onBookmarkClick(final Post post) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MyPostsPage.this);
                builder.setTitle("Ushbu e'lonni o'chirmoqchimisiz!")
                        .setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String path = "posts" + "/" + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() + "/" + post.getTimeInMillis();
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path);
                                databaseReference.removeValue();
                                deleteImages(post.getImages_links());
                                Toast.makeText(MyPostsPage.this, "e'lon o'chirildi!", Toast.LENGTH_SHORT).show();
                                adapter.refResh();
                            }
                        })
                        .setNegativeButton("Yo'q", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                builder.create().show();
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

    private void deleteImages(String imageLinks) {

        String[] links = imageLinks.split(",");

        for (int i = 0; i < links.length; i++) {

            StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(links[i]);
            reference.delete();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_posts_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.new_post) {

            startActivity(new Intent(this, Post_Page1.class));

        }

        return true;
    }
}