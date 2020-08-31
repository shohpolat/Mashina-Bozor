package com.example.mashinabozor.Models;

import android.content.Context;
import android.renderscript.ScriptGroup;
import android.widget.ArrayAdapter;

import com.example.mashinabozor.R;
import com.example.mashinabozor.Veiws.Post_Page1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Repository {

    private ArrayList<String> years = new ArrayList<>();
    private String [] price_unit = new String[]{"so'm","$"};
    private ArrayList<Integer> car_logo = new ArrayList<>();

    private int brands_counter;

    public Repository(){


    }

    public ArrayList<Integer> getCar_logo() {

        car_logo.add(R.drawable.ic_white_circle);

        for (int i = 0; i <brands_counter; i++) {

            car_logo.add(R.drawable.ic_empty_circle);

        }

        return car_logo;
    }

    public ArrayList<String> getYears() {

        years.add("");

        for (int i = 2020; i >1950 ; i--) {

            years.add(String.valueOf(i));

        }

        return years;
    }

    public String[] getPrice_unit() {
        return price_unit;
    }

    public ArrayList<String> getDataFromJson(Context context, String data_type){

        ArrayList<String> datas = new ArrayList<>();
        datas.add("");
        String jsonfile;

        try {
            InputStream jsondata = context.getAssets().open("car-details.json");

            int size = jsondata.available();

            byte[] buffer = new byte[size];

            jsondata.read(buffer);
            jsondata.close();

            jsonfile = new String(buffer,"UTF-8");

             JSONObject jsonObject = new JSONObject(jsonfile);
             JSONArray jsonArray = jsonObject.getJSONArray(data_type);

            for (int i = 0;i<jsonArray.length(); i++) {

                datas.add(jsonArray.getString(i));

            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        brands_counter = datas.size();
        return datas;

    }

    public ArrayList<String> getRegion(Context context){
        ArrayList<String> regions_list = new ArrayList<>();
        regions_list.add("");
        String jsonfile;

        try {
            InputStream inputStream = context.getAssets().open("regions_and_districts.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            jsonfile = new String(buffer,"UTF-8");
            JSONObject jsonObject = new JSONObject(jsonfile);

            Iterator<String> iterator = jsonObject.keys();
            while(iterator.hasNext()){

                regions_list.add(iterator.next());

            }



        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return regions_list;
    }

    public ArrayList<String> getDistricts(Context context, String region){

        ArrayList<String> districts = new ArrayList<>();
        String jsonfile;

        try {
            InputStream inputStream = context.getAssets().open("regions_and_districts.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            jsonfile = new String(buffer);

            JSONObject jsonObject = new JSONObject(jsonfile);
            JSONArray jsonArray = jsonObject.getJSONArray(region);

            for (int i = 0; i <jsonArray.length() ; i++) {

                districts.add(jsonArray.getString(i));

            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return districts;
    }

}
