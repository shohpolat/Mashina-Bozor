package com.example.mashinabozor.Presenters;

import android.content.Intent;

import com.example.mashinabozor.Adapters.Post_Home_Adapter;
import com.example.mashinabozor.Classes.Post;
import com.example.mashinabozor.Veiws.Post_Home_Page;

import java.util.ArrayList;


public class post_home_presenter {

    private Post_Home_Page view;

    public post_home_presenter(Post_Home_Page view) {
        this.view = view;
    }

    public void setViews() {

        String[] images=null;
        Post post = view.getIntent().getParcelableExtra("single_post");

        if (post!=null) {
            view.setViews(post.getCar_type(), post.getCar_brand(), post.getCar_model(), post.getCar_year(), post.getCar_body(), post.getCar_engine(),
                    post.getCar_gear(), post.getCar_driving(), post.getCar_fuel(), post.getCar_distance(), post.getCar_price(), post.getCar_price_unit(),
                    post.getExtra_info(), post.getRegion(), post.getDistrict(), post.getActive_number(), post.getPostId());

         images = post.getImages_links().split(",");
        }


        Post_Home_Adapter adapter = new Post_Home_Adapter(images);
        view.setAdapter(adapter);


    }

}
