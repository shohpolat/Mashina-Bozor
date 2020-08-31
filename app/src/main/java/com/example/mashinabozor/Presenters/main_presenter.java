package com.example.mashinabozor.Presenters;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mashinabozor.Adapters.Home_Tab_Adapter;
import com.example.mashinabozor.Adapters.Search_Tab_Adapter;
import com.example.mashinabozor.Classes.Post;
import com.example.mashinabozor.Classes.User;
import com.example.mashinabozor.Login_SignUp_Activities.Login_Activity;
import com.example.mashinabozor.Models.Repository;
import com.example.mashinabozor.Veiws.Main_Page;
import com.example.mashinabozor.Fragments.Home_Fragment;
import com.example.mashinabozor.Fragments.Message_Fragment;
import com.example.mashinabozor.Fragments.Page_Adapter;
import com.example.mashinabozor.Fragments.Search_Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class main_presenter {

    private Repository repository;
    private Main_Page view;
    Page_Adapter adapter;

    Home_Fragment home_fragment;
    Search_Fragment search_fragment;
    Message_Fragment message_fragment;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private ArrayList<Fragment> fragments;

    public main_presenter(Repository repository, final Main_Page view) {
        this.repository = repository;
        this.view = view;

        firebaseAuth = FirebaseAuth.getInstance();
        fragments = new ArrayList<>();

    }

    public void checkCurrentUser() {

        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            Intent intent = new Intent(view, Login_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            view.startActivity(intent);
        } else {

            String phone = firebaseUser.getPhoneNumber();
            Log.d("TTT","logged in with" + phone);
            setNames();

        }
    }

    public void createTabs() {

        home_fragment = new Home_Fragment();
        search_fragment = new Search_Fragment();
        message_fragment = new Message_Fragment();


        fragments.add(home_fragment);
        fragments.add(search_fragment);
        fragments.add(message_fragment);

        adapter = new Page_Adapter(view.getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments);
        view.setAdapter(adapter);
        view.setIconsToTabs();

    }

    public void refresh() {
        adapter.notifyDataSetChanged();
    }

    public void Log_out() {

        firebaseAuth.signOut();
        Intent intent = new Intent(view, Login_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        view.startActivity(intent);

    }

    public void scrollToTop() {

        home_fragment.scrollToTop();

    }

    public int getSelectedCategory() {
        return search_fragment.getSelectedCategory();
    }

    public void showSearchedPosts(int category, String searched_text) {

        search_fragment.showProgressBar();
        search_fragment.makeCatsGone();
        Search_Tab_Adapter adapter = new Search_Tab_Adapter(category, searched_text);
        search_fragment.setAdapter(adapter);


    }

    public void refReshPosts(){
        home_fragment.refReshPosts();
    }

    public void showCategories() {
        search_fragment.makeCatsVisible();
    }

    public void setNames() {

        String path = "users" + "/" + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                view.setNames(user.getFull_name());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
