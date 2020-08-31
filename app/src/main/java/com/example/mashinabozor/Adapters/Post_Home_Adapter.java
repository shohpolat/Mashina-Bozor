package com.example.mashinabozor.Adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashinabozor.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Post_Home_Adapter extends RecyclerView.Adapter<Post_Home_Adapter.Viewholder> {


    private String[] images;

    public Post_Home_Adapter(String[] images){

        this.images = images;

    }


    public class Viewholder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.post_home_image);

        }
    }

    @NonNull
    @Override
    public Post_Home_Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_home_recycler_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Post_Home_Adapter.Viewholder holder, int position) {

        Picasso.get().load(images[position]).placeholder(R.drawable.malibu_front).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }
}
