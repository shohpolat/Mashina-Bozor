package com.example.mashinabozor.Presenters;

import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mashinabozor.Adapters.Brands_ArrayAdapter;
import com.example.mashinabozor.Models.Repository;
import com.example.mashinabozor.R;
import com.example.mashinabozor.Veiws.Post_Page1;

public class page1_presenter {

    private Repository repository;
    private Post_Page1 view;

    public page1_presenter(Repository repository, Post_Page1 view) {
        this.repository = repository;
        this.view = view;
    }

    public void setAdapters(){

        Brands_ArrayAdapter brands_arrayAdapter = new Brands_ArrayAdapter(view,repository.getDataFromJson(view,"car_brands"),repository.getCar_logo());
        ArrayAdapter<String> years_adapter = new ArrayAdapter<String >(view, android.R.layout.simple_spinner_dropdown_item, repository.getYears());
        ArrayAdapter<String> body_adapter = new ArrayAdapter<String >(view, android.R.layout.simple_spinner_dropdown_item, repository.getDataFromJson(view,"car_body_types"));
        ArrayAdapter<String> gear_adapter = new ArrayAdapter<String >(view, android.R.layout.simple_spinner_dropdown_item, repository.getDataFromJson(view,"gear_types"));
        ArrayAdapter<String> driving_adapter = new ArrayAdapter<String >(view, android.R.layout.simple_spinner_dropdown_item, repository.getDataFromJson(view,"driving_types"));
        ArrayAdapter<String> fuel_adapter = new ArrayAdapter<String >(view, android.R.layout.simple_spinner_dropdown_item, repository.getDataFromJson(view,"fuel_types"));
        ArrayAdapter<String> price_unit_adapter = new ArrayAdapter<String >(view, android.R.layout.simple_spinner_dropdown_item, repository.getPrice_unit());

        view.setAdapters(brands_arrayAdapter,years_adapter,body_adapter,gear_adapter,driving_adapter,fuel_adapter,price_unit_adapter);

    }


    public void nextClicked(){

        String car_type, car_brand, car_model, car_year, car_body, car_engine, car_gear, car_driving, car_fuel, car_distance, car_price, car_price_unit, extra_info;
        boolean has_less;

        if (!view.getCheckedRadioButton()){
            Log.d("TTT",view.getCheckedRadioButton()+"");
            view.setRequestRadioGroup();
            return;
        }else {
            car_type = view.getCarType(view.getCheckedRadioButtonId());
        }

        if (view.getCar_brand().equals("")){

            view.setRequestBrand();
            return;

        }else {
            car_brand = view.getCar_brand();
        }

        if (view.getCarModel().equals("")){

            view.setRequestModel();
            return;

        }else {
            car_model = view.getCarModel();
        }

        if (view.getCarYear().equals("")){

            view.setRequestYear();
            return;

        }else {
            car_year = view.getCarYear();
        }

        if (view.getCarBody().equals("")){
            view.setRequestBody();
            return;
        }else {
            car_body = view.getCarBody();
        }

        if (view.getCarEngine().equals("")){

            view.setEngineRequest();
            return;

        }else {
            car_engine = view.getCarEngine();
        }

        if (view.getCarDistance().equals("")) {
            view.setRequestDistance();
            return;
        }else {
            car_distance = view.getCarDistance();
        }

        if (view.getCarPrice().equals("")) {
            view.setRequestPrice();
            return;
        }else {
            car_price = view.getCarPrice();
        }

        car_price_unit = view.getPriceUnit();

        car_gear = view.getCarGear();
        car_driving = view.getCarDriving();
        car_fuel = view.getCarFuel();
        has_less = view.getCarHassLess();
        extra_info = view.getExtraInfo();


        view.goToPage2(car_type,car_brand,car_model,car_year,car_body,car_engine,car_gear,car_driving,car_fuel,car_distance,car_price,car_price_unit,has_less,extra_info);


    }

}
