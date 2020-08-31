package com.example.mashinabozor.Veiws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mashinabozor.Models.Repository;
import com.example.mashinabozor.Presenters.main_presenter;
import com.example.mashinabozor.R;
import com.example.mashinabozor.Fragments.Page_Adapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class Main_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    TabLayout tabLayout;
    ViewPager viewPager;
    Button sell_btn;
    EditText editText;
    TextView prof_names;

    View headerView;
    ImageButton editNames;


    main_presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_activity);


        Hook_All_Views();


        presenter = new main_presenter(new Repository(), this);

        tabLayout.setupWithViewPager(viewPager);
        presenter.createTabs();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    setTitle("Mashina Bozor");
                    editText.setVisibility(View.GONE);
                }
                if (tab.getPosition() == 1) {

                    setTitle("");
                    editText.setVisibility(View.VISIBLE);


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {

                    presenter.scrollToTop();

                }

            }
        });


        sell_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Page.this, Post_Page1.class);
                startActivity(intent);
            }
        });

        editNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_Page.this, ProfileActivity.class));
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

    }

    private void Hook_All_Views() {

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setTitle("Mashina Bozor");
        navigationView = findViewById(R.id.navigation_view);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        sell_btn = findViewById(R.id.sell_car);
        editText = findViewById(R.id.search_editText);
        headerView = navigationView.getHeaderView(0);
        editNames = headerView.findViewById(R.id.edit_Profile_btn);
        prof_names = headerView.findViewById(R.id.prof_name);
    }


    public void setAdapter(Page_Adapter adapter) {

        viewPager.setAdapter(adapter);

    }

    public void setIconsToTabs() {

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search_service);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_chat);

    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.checkCurrentUser();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.log_out) {
            presenter.Log_out();
        }

        if (item.getItemId() == R.id.search_icon) {
            viewPager.setCurrentItem(1, true);
            int category = presenter.getSelectedCategory();
            String searched_text = editText.getText().toString();
            if (category != 0 && !searched_text.isEmpty()) {

                presenter.showSearchedPosts(category, searched_text);

            } else {
                if (category == 0) {
                    Toast.makeText(this, "izlash kategoriyasini tanlang!", Toast.LENGTH_SHORT).show();
                    presenter.showCategories();

                }
                if (searched_text.isEmpty()) {
                    editText.setError("izlash uchun kalit so'zni kiriting!");
                    editText.requestFocus();
                }
            }
        }

        return true;
    }


    public void setNames(String full_name) {

        full_name = full_name.replace(" ", "\n");
        prof_names.setText(full_name);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.nav_give_post) {

            startActivity(new Intent(Main_Page.this, Post_Page1.class));
            drawerLayout.closeDrawer(GravityCompat.START, false);

        }

        if (id == R.id.my_posts) {

            startActivity(new Intent(Main_Page.this, MyPostsPage.class));
            drawerLayout.closeDrawer(GravityCompat.START, false);

        }
        if (id == R.id.saved_posts) {

            startActivity(new Intent(Main_Page.this, SavedPostsPage.class));
            drawerLayout.closeDrawer(GravityCompat.START, false);

        }
        if (id == R.id.nav_search_btn) {

            viewPager.setCurrentItem(1, true);
            drawerLayout.closeDrawer(GravityCompat.START, false);

        }
        if (id == R.id.nav_refresh) {

            drawerLayout.closeDrawer(GravityCompat.START, false);
            viewPager.setCurrentItem(0, true);
            presenter.refReshPosts();

        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }
}