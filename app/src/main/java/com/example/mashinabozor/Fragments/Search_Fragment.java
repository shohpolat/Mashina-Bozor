package com.example.mashinabozor.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashinabozor.Adapters.Home_Tab_Adapter;
import com.example.mashinabozor.Adapters.Search_Tab_Adapter;
import com.example.mashinabozor.Classes.Post;
import com.example.mashinabozor.R;
import com.example.mashinabozor.Veiws.Post_Home_Page;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Search_Fragment extends Fragment {

    ImageButton up_down_button;
    View viewR;
    boolean isShown = true;
    RadioButton by_postId, by_brand, by_model, by_price, by_regions, by_type;
    RadioGroup rg1;
    RecyclerView recyclerView;
    Search_Tab_Adapter adapter;
    private Context context;
    ProgressBar progressBar;
    private boolean isExist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_fragment_layout, container, false);

        up_down_button = view.findViewById(R.id.downBtn);
        viewR = view.findViewById(R.id.relative);
        by_postId = view.findViewById(R.id.by_postId);
        by_brand = view.findViewById(R.id.by_brand);
        by_model = view.findViewById(R.id.by_model);
        by_price = view.findViewById(R.id.by_price);
        by_regions = view.findViewById(R.id.by_regions);
        by_type = view.findViewById(R.id.by_type);
        rg1 = view.findViewById(R.id.search_rg1);
        recyclerView = view.findViewById(R.id.search_tab_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        progressBar = view.findViewById(R.id.searchTab_progressBar);

        up_down_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShown) {
                    viewR.setVisibility(View.GONE);
                    isShown = false;
                    up_down_button.setImageResource(R.drawable.ic_down);
                } else {
                    viewR.setVisibility(View.VISIBLE);
                    isShown = true;
                    up_down_button.setImageResource(R.drawable.ic_up);
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TTT", "oncreate");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;

    }

    public void setAdapter(Search_Tab_Adapter adapter) {


        progressBar.setVisibility(View.VISIBLE);
        this.adapter = adapter;
        this.adapter.setOnClickListener(new Search_Tab_Adapter.onClickListener() {
            @Override
            public void onclick(Post post) {
                Intent intent = new Intent(context, Post_Home_Page.class);
                intent.putExtra("single_post", post);
                startActivity(intent);
            }

            @Override
            public void bookmarkclick(final Post post) {
                String path = "saved_posts" + "/" + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() + "/" + post.getPostId();
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        isExist = snapshot.exists();
                        saveUnsave(databaseReference, post);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Log.d("TTT", error.getMessage());

                    }
                });
            }
        });
        recyclerView.setAdapter(this.adapter);
        adapter.notifyDataSetChanged();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        }, 3000);
    }

    private void saveUnsave(DatabaseReference databaseReference, Post post) {

        if (isExist) {
            Toast.makeText(context, "e'lon saqlangan!", Toast.LENGTH_SHORT).show();

        } else {
            databaseReference.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "e'lon saqlab olindi!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public int getSelectedCategory() {

        return getTag(rg1.getCheckedRadioButtonId());

    }

    public void makeCatsVisible() {
        up_down_button.setImageResource(R.drawable.ic_up);
        viewR.setVisibility(View.VISIBLE);
    }

    public void makeCatsGone() {

        up_down_button.setImageResource(R.drawable.ic_down);
        viewR.setVisibility(View.GONE);
    }

    private int getTag(int id) {

        int category = 0;

        if (id == R.id.by_postId) {
            category = Integer.parseInt((String) by_postId.getTag());
        }
        if (id == R.id.by_brand) {
            category = Integer.parseInt((String) by_brand.getTag());
        }
        if (id == R.id.by_model) {
            category = Integer.parseInt((String) by_model.getTag());
        }
        if (id == R.id.by_price) {
            category = Integer.parseInt((String) by_price.getTag());
        }
        if (id == R.id.by_regions) {
            category = Integer.parseInt((String) by_regions.getTag());
        }
        if (id == R.id.by_type) {
            category = Integer.parseInt((String) by_type.getTag());
        }

        return category;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
}
