package com.example.mashinabozor.Veiws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mashinabozor.Adapters.Brands_ArrayAdapter;
import com.example.mashinabozor.Models.Repository;
import com.example.mashinabozor.Presenters.page1_presenter;
import com.example.mashinabozor.R;

public class Post_Page1 extends AppCompatActivity {

    public static final String CAR_TYPE_EXTRA = "car_type";
    public static final String CAR_BRAND_EXTRA = "car_brand_extra";
    public static final String CAR_MODEL_EXTRA = "car_model_extra";
    public static final String CAR_YEAR_EXTRA = "car_year_extra";
    public static final String CAR_BODY_EXTRA = "car_body_extra";
    public static final String CAR_ENGINE_EXTRA = "car_engine_extra";
    public static final String CAR_GEAR_EXTRA = "car_gear_extra";
    public static final String CAR_DRIVING_EXTRA = "car_driving_extra";
    public static final String CAR_FUEL_EXTRA = "car_fuel_extra";
    public static final String CAR_DISTANCE_EXTRA = "car_distance_extra";
    public static final String CAR_PRICE_EXTRA = "car_price_extra";
    public static final String CAR_PRICE_UNIT_EXTRA = "car_price_unit_extra";
    public static final String CAR_HAS_LESS_EXTRA = "car_has_less_extra";
    public static final String EXTRA_INFO_EXTRA = "extra_info_extra";


    Spinner car_brand_spinner, car_year_spinner, car_body_spinner, car_gear_spinner, car_driving_spinner, car_fuel_spinner, car_price_unit_spinner;
    EditText car_model, car_engine, car_distance, car_price, extra_info;
    CheckBox checkBox;
    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2,radioButton3;
    Button next_post, cancel_post;
    ImageButton back_icon;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page1);

        hookAllView();
        final page1_presenter presenter = new page1_presenter(new Repository(), this);

        presenter.setAdapters();


        next_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                presenter.nextClicked();


            }
        });

        cancel_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void hookAllView() {

        car_brand_spinner = findViewById(R.id.car_brand_spinner);
        car_year_spinner = findViewById(R.id.car_year_spinner);
        car_body_spinner = findViewById(R.id.car_body_spinner);
        car_gear_spinner = findViewById(R.id.car_gear_spinner);
        car_driving_spinner = findViewById(R.id.car_driving_spinner);
        car_fuel_spinner = findViewById(R.id.car_fuel_spinner);
        car_price_unit_spinner = findViewById(R.id.car_price_unitSpinner);
        car_model = findViewById(R.id.car_model);
        car_engine = findViewById(R.id.car_engine_editText);
        car_distance = findViewById(R.id.car_distance_editText);
        car_price = findViewById(R.id.car_price_editText);
        extra_info = findViewById(R.id.extra_info_editText);
        checkBox = findViewById(R.id.checkbox_);
        next_post = findViewById(R.id.next_post);
        cancel_post = findViewById(R.id.cancel_post);
        radioGroup = findViewById(R.id.radio_group);
        radioButton1 = findViewById(R.id.light_cars);
        radioButton2 = findViewById(R.id.heavy_cars);
        radioButton3 = findViewById(R.id.bus_cars);
        toolbar = findViewById(R.id.post1_toolbar);
        back_icon = findViewById(R.id.post1_back_icon);


    }


    public void setAdapters(final Brands_ArrayAdapter brands_adapter, ArrayAdapter<String> years_adapter, ArrayAdapter<String> body_adapter, ArrayAdapter<String> gear_adapter, ArrayAdapter<String> driving_adapter, ArrayAdapter<String> fuel_adapter, ArrayAdapter<String> price_unit_adapter) {


        car_brand_spinner.setAdapter(brands_adapter);
        car_year_spinner.setAdapter(years_adapter);
        car_body_spinner.setAdapter(body_adapter);
        car_gear_spinner.setAdapter(gear_adapter);
        car_driving_spinner.setAdapter(driving_adapter);
        car_fuel_spinner.setAdapter(fuel_adapter);
        car_price_unit_spinner.setAdapter(price_unit_adapter);


        car_brand_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                brands_adapter.setGreenTick(i);
                ImageView imageView = view.findViewById(R.id.car_logo);
                imageView.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
    }

    public boolean getCheckedRadioButton(){

        boolean ischecked=false;
        if (radioButton1.isChecked()){
            ischecked = true;
        }
        else if (radioButton2.isChecked()){
            ischecked = true;
        }else if(radioButton3.isChecked()){
            ischecked = true;
        }

        return ischecked;
    }


    public void setRequestRadioGroup() {

        Toast.makeText(this, "avtomobil turini belgilang!", Toast.LENGTH_SHORT).show();
        radioGroup.requestFocus();

    }

    public int getCheckedRadioButtonId() {
        return radioGroup.getCheckedRadioButtonId();
    }

    public String getCarType(int checkedRadioButtonId) {

        String car_type=null;

        if (checkedRadioButtonId == R.id.light_cars){
            car_type = radioButton1.getText().toString();
        }else if (checkedRadioButtonId == R.id.heavy_cars){
            car_type = radioButton2.getText().toString();
        }else if (checkedRadioButtonId == R.id.bus_cars){
            car_type = radioButton3.getText().toString();
        }

        return car_type;
    }

    public String getCar_brand() {

        return car_brand_spinner.getSelectedItem().toString();
    }

    public void setRequestBrand() {

        Toast.makeText(this, "avtomobil markasini belgilang!", Toast.LENGTH_SHORT).show();
        car_brand_spinner.requestFocus();

    }

    public String getCarModel() {

        return car_model.getText().toString().trim();

    }

    public void setRequestModel() {

        car_model.setError("avtomobil modelini kiriting!");
        car_model.requestFocus();

    }

    public String getCarYear() {
        return car_year_spinner.getSelectedItem().toString();
    }

    public void setRequestYear() {

        Toast.makeText(this, "ishlab chiqarilgan yilni ko'sating!", Toast.LENGTH_SHORT).show();
        car_year_spinner.requestFocus();

    }

    public String getCarBody() {

        return car_body_spinner.getSelectedItem().toString();

    }

    public void setRequestBody() {
        Toast.makeText(this, "kuzov turini belgilang!", Toast.LENGTH_SHORT).show();
        car_year_spinner.requestFocus();
    }

    public String getCarEngine() {
        return car_engine.getText().toString().trim();
    }

    public void setEngineRequest() {
        car_engine.setError("dvigatel hajmini kiriting!");
        car_engine.requestFocus();
    }

    public String getCarDistance() {
        return car_distance.getText().toString().trim();
    }

    public void setRequestDistance() {
        car_distance.setError("yurgan masofani kiriting!");
        car_distance.requestFocus();
    }

    public String getCarPrice() {
        return car_price.getText().toString().trim();
    }

    public void setRequestPrice() {
        car_price.setError("avtomobil narxini kiriting!");
        car_price.requestFocus();
    }

    public String getCarGear() {

        return car_gear_spinner.getSelectedItem().toString();

    }

    public String getCarDriving() {

        return car_driving_spinner.getSelectedItem().toString();
    }

    public String getCarFuel() {

        return car_fuel_spinner.getSelectedItem().toString();

    }

    public boolean getCarHassLess() {

        return checkBox.isChecked();

    }

    public String getExtraInfo() {

        return extra_info.getText().toString();

    }

    public String getPriceUnit() {

        return car_price_unit_spinner.getSelectedItem().toString();
    }

    public void goToPage2(String car_type, String car_brand, String car_model, String car_year, String car_body, String car_engine, String car_gear, String car_driving, String car_fuel, String car_distance, String car_price, String car_price_unit, boolean has_less, String extra_info) {

        Intent intent = new Intent(Post_Page1.this,Post_Page2.class);
        intent.putExtra(CAR_TYPE_EXTRA,car_type);
        intent.putExtra(CAR_BRAND_EXTRA,car_brand);
        intent.putExtra(CAR_MODEL_EXTRA,car_model);
        intent.putExtra(CAR_YEAR_EXTRA,car_year);
        intent.putExtra(CAR_BODY_EXTRA,car_body);
        intent.putExtra(CAR_ENGINE_EXTRA,car_engine);
        intent.putExtra(CAR_GEAR_EXTRA,car_gear);
        intent.putExtra(CAR_DRIVING_EXTRA,car_driving);
        intent.putExtra(CAR_FUEL_EXTRA,car_fuel);
        intent.putExtra(CAR_DISTANCE_EXTRA,car_distance);
        intent.putExtra(CAR_PRICE_EXTRA,car_price);
        intent.putExtra(CAR_PRICE_UNIT_EXTRA,car_price_unit);
        intent.putExtra(CAR_HAS_LESS_EXTRA,has_less);
        intent.putExtra(EXTRA_INFO_EXTRA,extra_info);
        startActivity(intent);


    }
}