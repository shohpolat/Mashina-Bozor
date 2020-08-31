package com.example.mashinabozor.Veiws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mashinabozor.Adapters.Post_Home_Adapter;
import com.example.mashinabozor.Presenters.post_home_presenter;
import com.example.mashinabozor.R;

public class Post_Home_Page extends AppCompatActivity {


    TextView car_brand_n_model,car_type_, car_year, car_body, car_engine, car_gear,car_driving,car_fuel,car_distance,car_price,car_address,car_phone_number,post_home_extra_info;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ImageButton back_icon;
    TextView postIdtext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__home__page);
        hookAllViews();
        post_home_presenter presenter = new post_home_presenter(this);
        presenter.setViews();

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void hookAllViews() {

        car_brand_n_model = findViewById(R.id.car_brand_n_model);
        car_type_ = findViewById(R.id.post_home_car_type);
        car_year = findViewById(R.id.post_home_car_year);
        car_body = findViewById(R.id.post_home_car_body);
        car_engine = findViewById(R.id.post_home_car_engine);
        car_gear = findViewById(R.id.post_home_car_gear);
        car_driving = findViewById(R.id.post_home_car_driving);
        car_fuel = findViewById(R.id.post_home_car_fuel);
        car_distance = findViewById(R.id.post_home_car_distance);
        car_price = findViewById(R.id.post_home_price);
        car_address = findViewById(R.id.post_home_adress);
        car_phone_number = findViewById(R.id.post_home_phone_number);
        post_home_extra_info = findViewById(R.id.post_home_extra_info);
        recyclerView = findViewById(R.id.post_home_recyclerview);

        toolbar = findViewById(R.id.post_home_toolbar);
        back_icon = findViewById(R.id.post_home_back_icon);
        postIdtext = findViewById(R.id.post_home_postId);

    }

    public void setViews(String car_type, String car_brand, String car_model, String year, String body, String engine, String gear, String driving, String fuel, String distance, String price, String price_unit, String extra_info, String region, String district, String active_number, String postId) {

        car_brand_n_model.setText(car_brand + " " + car_model);
        car_type_.setText(car_type);
        car_year.setText(year);
        car_body.setText(body);
        car_engine.setText(engine);
        car_gear.setText(gear);
        car_driving.setText(driving);
        car_fuel.setText(fuel);
        car_distance.setText(distance);
        car_price.setText(price + " " + price_unit);
        car_address.setText(region + ",\n" + district);
        car_phone_number.setText(active_number);
        if (extra_info.length()!=0) {
            post_home_extra_info.setText("qo'shimcha ma'lumot: " + extra_info);
            post_home_extra_info.setVisibility(View.VISIBLE);
        }


        postIdtext.setText(postId);



    }

    public void setAdapter(Post_Home_Adapter adapter) {

        recyclerView.setLayoutManager(new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }
}