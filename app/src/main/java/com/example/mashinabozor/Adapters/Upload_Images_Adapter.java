package com.example.mashinabozor.Adapters;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashinabozor.R;

import java.util.ArrayList;

public class Upload_Images_Adapter extends RecyclerView.Adapter<Upload_Images_Adapter.ViewHolder> {

    private ArrayList<Uri> image_uris;
    private ArrayList<String> image_names;

    private OnClickListener listener;


    public Upload_Images_Adapter(ArrayList<Uri> uris, ArrayList<String> names){

        image_uris = uris;
        image_names = names;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView car_image, remove_icon;
        TextView image_name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            car_image = itemView.findViewById(R.id.single_photo);
            image_name = itemView.findViewById(R.id.single_photo_name);
            remove_icon = itemView.findViewById(R.id.remove_single_photo);


        }

        public void bind(final int position){

            remove_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    listener.onclick(position);

                }
            });
        }


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_selected_photo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d("TTT","position="+position);
        holder.car_image.setImageURI(image_uris.get(position));
        holder.image_name.setText(image_names.get(position));


        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return image_names.size();
    }


    public interface OnClickListener{

        void onclick(int position);

    }

    public void setOnCLickListener(OnClickListener lickListener){

        this.listener = lickListener;

    }

}
