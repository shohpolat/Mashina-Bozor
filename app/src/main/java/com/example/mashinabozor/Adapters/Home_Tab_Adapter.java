package com.example.mashinabozor.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashinabozor.Classes.Post;
import com.example.mashinabozor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Home_Tab_Adapter extends RecyclerView.Adapter<Home_Tab_Adapter.ViewHolder> {

    private ArrayList<Post> posts;
    DatabaseReference databaseReference;
    onClickListener listener;

    public Home_Tab_Adapter() {

        posts = new ArrayList<>();
        Log.d("TTT", "Adapter created = " + posts.size());
       refreshPosts();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView car_brand, car_model, car_price, car_details, post_id, views_count, date;
        TextView has_less_view;
        ImageView img1, img2, img3;
        Button img_count;
        View view;
        ImageView bookmark;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            car_brand = itemView.findViewById(R.id.home_car_brand);
            car_model = itemView.findViewById(R.id.home_car_model);
            car_price = itemView.findViewById(R.id.home_car_price);
            car_details = itemView.findViewById(R.id.home_car_details);
            post_id = itemView.findViewById(R.id.home_post_Id);
            views_count = itemView.findViewById(R.id.home_views_count);
            has_less_view = itemView.findViewById(R.id.home_has_less_view);
            date = itemView.findViewById(R.id.post_date);

            img1 = itemView.findViewById(R.id.home_img1);
            img2 = itemView.findViewById(R.id.home_img2);
            img3 = itemView.findViewById(R.id.home_img3);

            img_count = itemView.findViewById(R.id.images_count);

            view = itemView.findViewById(R.id.post_click);

            bookmark = itemView.findViewById(R.id.bookmark);

            Log.d("TTT", "viewholder constructore");

        }

        void bind(final Post post, final long position) {


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onclick(post, position);

                }
            });

            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onBookmarkClick(post);
                }
            });

        }

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_single_post, parent, false);

        Log.d("TTT", "oncreateViewholder");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        long posts_counter = posts.size() - 1;
        Log.d("TTT", "onBindViewholder");
        Post post = posts.get((int) (posts_counter - position));

        String car_brand = post.getCar_brand();
        String car_model = post.getCar_model();
        String car_price = post.getCar_price() + " " + post.getCar_price_unit();
        String car_details = post.getCar_year() + "  " + post.getCar_type() + "  " + post.getCar_body() + "  " + post.getCar_engine() + " dvigatel  " + post.getCar_distance() + " KM  " + post.getCar_gear() +
                "  " + post.getCar_driving() + "  " + post.getCar_fuel();

        String has_less = post.getHas_less();
        String post_id = post.getPostId();
        String datesDifference = getDatesDifference(post.getDate());
        String views_count = String.valueOf(post.getViews());

        holder.car_model.setText(car_model);
        holder.car_brand.setText(car_brand);
        holder.car_price.setText(car_price);
        holder.car_details.setText(car_details);
        holder.post_id.setText(post_id);
        holder.has_less_view.setText(has_less);

        String[] images = post.getImages_links().split(",");
        Log.d("TTT", "images number = " + images.length);

        Picasso.get().load(images[0]).placeholder(R.drawable.malibu_front).into(holder.img1);
        Picasso.get().load(images[1]).placeholder(R.drawable.malibu_front).into(holder.img2);
        Picasso.get().load(images[2]).placeholder(R.drawable.malibu_front).into(holder.img3);

        holder.img_count.setText("+" + (images.length-3));
        holder.date.setText(datesDifference);
        holder.views_count.setText(views_count);

        holder.bind(post, posts_counter - position);


    }


    @Override
    public int getItemCount() {

        Log.d("TTT", "OngetItemCount, postsize = " + posts.size());
        return posts.size();
    }

    public void refreshPosts(){

        posts.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        Post post = dataSnapshot1.getValue(Post.class);
                        posts.add(post);

                    }
                }

                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.d("TTT", error.getMessage());

            }
        });

    }

    public interface onClickListener {

        void onclick(Post post, long position);

        void onBookmarkClick(Post post);
    }

    public void setOnClickListener(onClickListener listener) {

        this.listener = listener;

    }

    private String getDatesDifference(String dbdate){
        String datesDifference = "";
        String sysdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (dbdate.equals(sysdate)){
            datesDifference = "bugun";
        }else {
            String[] dbarray = dbdate.split("-");
            String[] sysarray = sysdate.split("-");
            int yearDifference = Integer.parseInt(sysarray[0])-Integer.parseInt(dbarray[0]);
            int monthDifference = Integer.parseInt(sysarray[1])-Integer.parseInt(dbarray[1]);
            int dayDifference = Integer.parseInt(sysarray[2])-Integer.parseInt(dbarray[2]);

            if (dayDifference!=0){
                datesDifference = dayDifference + " kun oldin";
            }
            if (monthDifference!=0){
                datesDifference = monthDifference + " oy oldin";
            }
            if (yearDifference!=0){
                datesDifference = yearDifference + " yil oldin";
            }
        }

        return datesDifference;
    }

}
