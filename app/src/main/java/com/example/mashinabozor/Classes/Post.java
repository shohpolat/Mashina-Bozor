package com.example.mashinabozor.Classes;

import android.os.Parcel;
import android.os.Parcelable;
import android.print.PrinterId;

import java.util.ArrayList;

public class Post implements Parcelable {

    private String car_type;
    private String car_brand;
    private String car_model;
    private String car_year;
    private String car_body;
    private String car_engine;
    private String car_gear;
    private String car_driving;
    private String car_fuel;
    private String car_distance;
    private String car_price;
    private String car_price_unit;
    private String has_less;
    private String extra_info;
    private String postId;
    private String region;
    private String district;
    private String active_number;
    private String images_links;
    private String date;
    private int views=0;
    private String viewed_users="";
    private String timeInMillis;
    private boolean isSaved = false;

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public String getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(String timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getViewed_users() {
        return viewed_users;
    }

    public void setViewed_users(String viewed_users) {
        this.viewed_users += viewed_users;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    protected Post(Parcel in) {
        car_type = in.readString();
        car_brand = in.readString();
        car_model = in.readString();
        car_year = in.readString();
        car_body = in.readString();
        car_engine = in.readString();
        car_gear = in.readString();
        car_driving = in.readString();
        car_fuel = in.readString();
        car_distance = in.readString();
        car_price = in.readString();
        car_price_unit = in.readString();
        has_less = in.readString();
        extra_info = in.readString();
        postId = in.readString();
        region = in.readString();
        district = in.readString();
        active_number = in.readString();
        images_links = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(car_type);
        dest.writeString(car_brand);
        dest.writeString(car_model);
        dest.writeString(car_year);
        dest.writeString(car_body);
        dest.writeString(car_engine);
        dest.writeString(car_gear);
        dest.writeString(car_driving);
        dest.writeString(car_fuel);
        dest.writeString(car_distance);
        dest.writeString(car_price);
        dest.writeString(car_price_unit);
        dest.writeString(has_less);
        dest.writeString(extra_info);
        dest.writeString(postId);
        dest.writeString(region);
        dest.writeString(district);
        dest.writeString(active_number);
        dest.writeString(images_links);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getActive_number() {
        return active_number;
    }

    public void setActive_number(String active_number) {
        this.active_number = active_number;
    }


    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Post(String car_type, String car_brand, String car_model, String car_year, String car_body, String car_engine, String car_gear, String car_driving, String car_fuel, String car_distance, String car_price, String car_price_unit, String has_less, String extra_info) {
        this.car_type = car_type;
        this.car_brand = car_brand;
        this.car_model = car_model;
        this.car_year = car_year;
        this.car_body = car_body;
        this.car_engine = car_engine;
        this.car_gear = car_gear;
        this.car_driving = car_driving;
        this.car_fuel = car_fuel;
        this.car_distance = car_distance;
        this.car_price = car_price;
        this.car_price_unit = car_price_unit;
        this.has_less = has_less;
        this.extra_info = extra_info;

    }

    public Post(){}

    public void setImages_links(String images){

        this.images_links = images;

    }

    public String getImages_links() {
        return images_links;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_year() {
        return car_year;
    }

    public void setCar_year(String car_year) {
        this.car_year = car_year;
    }

    public String getCar_body() {
        return car_body;
    }

    public void setCar_body(String car_body) {
        this.car_body = car_body;
    }

    public String getCar_engine() {
        return car_engine;
    }

    public void setCar_engine(String car_engine) {
        this.car_engine = car_engine;
    }

    public String getCar_gear() {
        return car_gear;
    }

    public void setCar_gear(String car_gear) {
        this.car_gear = car_gear;
    }

    public String getCar_driving() {
        return car_driving;
    }

    public void setCar_driving(String car_driving) {
        this.car_driving = car_driving;
    }

    public String getCar_fuel() {
        return car_fuel;
    }

    public void setCar_fuel(String car_fuel) {
        this.car_fuel = car_fuel;
    }

    public String getCar_distance() {
        return car_distance;
    }

    public void setCar_distance(String car_distance) {
        this.car_distance = car_distance;
    }

    public String getCar_price() {
        return car_price;
    }

    public void setCar_price(String car_price) {
        this.car_price = car_price;
    }

    public String getCar_price_unit() {
        return car_price_unit;
    }

    public void setCar_price_unit(String car_price_unit) {
        this.car_price_unit = car_price_unit;
    }

    public String getHas_less() {
        return has_less;
    }

    public void setHas_less(String has_less) {
        this.has_less = has_less;
    }

    public String getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(String extra_info) {
        this.extra_info = extra_info;
    }
}
