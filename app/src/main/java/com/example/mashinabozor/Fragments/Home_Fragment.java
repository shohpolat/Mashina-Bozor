package com.example.mashinabozor.Fragments;

import android.appwidget.AppWidgetHost;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mashinabozor.Adapters.Home_Tab_Adapter;
import com.example.mashinabozor.Classes.Post;
import com.example.mashinabozor.R;
import com.example.mashinabozor.Veiws.Main_Page;
import com.example.mashinabozor.Veiws.Post_Home_Page;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Home_Fragment extends Fragment {

    Home_Tab_Adapter adapter;
    boolean isExist;
    Context context;
    View view;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Map<DatabaseReference, Post> stack = new HashMap<>();
    SwipeRefreshLayout swipeRefreshLayout;


    public Home_Fragment() {
        Log.d("TTT", "constructor");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment_layout, container, false);

        recyclerView = view.findViewById(R.id.home_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refReshPosts();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void refReshPosts() {
        adapter.refreshPosts();
    }

    public void scrollToTop() {

        recyclerView.smoothScrollToPosition(0);

    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TTT", "oncreate");

        adapter = new Home_Tab_Adapter();

        adapter.setOnClickListener(new Home_Tab_Adapter.onClickListener() {
            @Override
            public void onclick(final Post post, long postion) {

                String phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                if (!post.getViewed_users().contains(phone)) {
                    String path = "posts" + "/" + phone + "/" + post.getTimeInMillis();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path);
                    post.setViewed_users(phone);
                    post.setViews(post.getViews() + 1);
                    adapter.notifyItemChanged((int) postion, post);
                    stack.put(databaseReference, post);
                }

                Intent intent = new Intent(context, Post_Home_Page.class);
                intent.putExtra("single_post", post);
                startActivity(intent);

            }

            @Override
            public void onBookmarkClick(final Post post) {
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


    }


    private void saveUnsave(DatabaseReference databaseReference, Post post) {

        if (isExist) {
            Toast.makeText(context, "e'lon saqlangan!", Toast.LENGTH_SHORT).show();

        } else {
            Log.d("aaa", isExist + " if li");
            databaseReference.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "e'lon saqlab olindi!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.home_progressBar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);

            }
        }, 5000);

    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d("TTT", "onstop");


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("TTT", "ondestroyview");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TTT", "ondestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d("TTT", "ondetach");
        Set<DatabaseReference> references = stack.keySet();
        for (DatabaseReference reference : references) {

            reference.setValue(stack.get(reference));

        }

        stack.clear();
    }
}
