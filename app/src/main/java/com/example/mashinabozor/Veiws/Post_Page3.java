package com.example.mashinabozor.Veiws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mashinabozor.Dialogs.Uploading_Dialog;
import com.example.mashinabozor.Presenters.page3_presenter;
import com.example.mashinabozor.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Post_Page3 extends AppCompatActivity implements Uploading_Dialog.backToMainListener {

    Spinner regions_spinner, districts_spinner;
    TextInputEditText active_phone_number;
    Button sell_Btn;
    page3_presenter presenter;
    boolean isSellClicked = false;
    Toolbar toolbar;
    ImageButton back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page3);


        hookAllViews();


        presenter = new page3_presenter(Post_Page3.this);
        presenter.setRegionSpinner();

        regions_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                presenter.setDistrictSpinner();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sell_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOnline()) {

                    isSellClicked = true;
                    presenter.sellClicked();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Post_Page3.this).create();
                    alertDialog.setTitle("Diqqat!");
                    alertDialog.setMessage("internet bilan bog'liqlik uzilgan");
                    alertDialog.setIcon(R.drawable.disconnection);
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "tushunarli", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });


                    alertDialog.show();

                }


            }
        });

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void hookAllViews(){

        regions_spinner = findViewById(R.id.regions_spinner);
        districts_spinner = findViewById(R.id.districts_spinner);
        active_phone_number = findViewById(R.id.active_number_editText);
        sell_Btn = findViewById(R.id.sell_Btn);

        toolbar = findViewById(R.id.post3_toolbar);
        back_icon = findViewById(R.id.post3_back_icon);

    }

    public void setRegionAdapter(ArrayAdapter<String> regionAdapter) {

        regions_spinner.setAdapter(regionAdapter);

    }

    public String getSelectedRegion() {

        return regions_spinner.getSelectedItem().toString();

    }

    public void setDistrictsAdapter(ArrayAdapter<String> districtAdapter) {

        districts_spinner.setAdapter(districtAdapter);

    }

    public String getSelectedDistrict() {

        return districts_spinner.getSelectedItem().toString();

    }

    public String getActiveNumber() {

        return active_phone_number.getText().toString();

    }

    public void setRequest(String s) {

        active_phone_number.setError(s);
        active_phone_number.requestFocus();

    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected() && netInfo.isAvailable();
    }

    @Override
    public void backToMain() {

        Intent intent = new Intent(this, Main_Page.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        if (!isSellClicked) {
            super.onBackPressed();
        }else {
            Log.d("TTT",isSellClicked+"");
        }
    }
}