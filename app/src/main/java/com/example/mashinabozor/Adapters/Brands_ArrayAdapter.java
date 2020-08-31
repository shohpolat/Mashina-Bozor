package com.example.mashinabozor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mashinabozor.R;

import java.util.ArrayList;

public class Brands_ArrayAdapter extends ArrayAdapter<String> {

    ArrayList<String> car_brands;
    ArrayList<Integer> car_logos;
    private int lastSelected = -1;

    public Brands_ArrayAdapter(Context context, ArrayList<String> car_brands, ArrayList<Integer> car_logos) {
        super(context, 0, car_brands);

        this.car_brands = car_brands;
        this.car_logos = car_logos;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    public View initView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.car_brands_list, parent, false);

        }

        TextView textView = convertView.findViewById(R.id.car_brand);
        ImageView imageView = convertView.findViewById(R.id.car_logo);
        textView.setText(car_brands.get(position));
        imageView.setImageResource(car_logos.get(position));


        return convertView;

    }

    public void setGreenTick(int position) {

        if (lastSelected != -1) {

            car_logos.set(lastSelected, R.drawable.ic_empty_circle);

        }
        if(position!=0) {
            car_logos.set(position, R.drawable.greentick);
            lastSelected = position;
        }


    }

}
