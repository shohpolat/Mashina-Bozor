package com.example.mashinabozor.Veiws;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mashinabozor.Adapters.Upload_Images_Adapter;
import com.example.mashinabozor.Presenters.page2_presenter;
import com.example.mashinabozor.R;
import com.example.mashinabozor.Dialogs.Review_dialog;

import java.util.ArrayList;

public class Post_Page2 extends AppCompatActivity implements Review_dialog.dialogInterface {

    public static final String REVIEWS_EXTRA = "reviews_extra";
    public static final String IMAGES_URI_EXTRA = "images_uri_extra";

    Button select_images_btn, finish_Btn, review_Btn;
    RecyclerView recyclerView;
    ImageButton back_icon;
    Toolbar toolbar;

    page2_presenter presenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page2);

        hookAllViews();

        presenter = new page2_presenter(this);


        presenter.createAdapter();

        select_images_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          presenter.pick_Images();


            }
        });

        finish_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.finishBtnClick();

            }
        });

        review_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.reviewBtnClicked();

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
        select_images_btn = findViewById(R.id.select_photos_btn);
        finish_Btn = findViewById(R.id.finish_Btn);
        recyclerView = findViewById(R.id.select_photos_recyclerview);
        review_Btn = findViewById(R.id.review_btn);

        toolbar = findViewById(R.id.post2_toolbar);
        back_icon = findViewById(R.id.post2_back_icon);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                if (data.getClipData() != null) {

                    presenter.showSelectedImages(data);

                }else {
                    Toast.makeText(this, "Rasmlar soni kamida 3 ta bo'lishi kerak!", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    public void setAdapterToRecyclerview(Upload_Images_Adapter adapter){

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    private String showReviewView(ArrayList<String> reviews) {

        String review_string = "turi : " + reviews.get(0) + "\n" +
                "markasi : " + reviews.get(1) + "\n" +
                "modeli : " + reviews.get(2) + "\n" +
                "yili : " + reviews.get(3) + "\n" +
                "kuzovi : " + reviews.get(4) + "\n" +
                "dvigateli : " + reviews.get(5) + "\n" +
                "uzatish : " + reviews.get(6) + "\n" +
                "tortish : " + reviews.get(7) + "\n" +
                "yoqilg'i : " + reviews.get(8) + "\n" +
                "yurgan masofasi : " + reviews.get(9) + "\n" +
                "narxi : " + reviews.get(10) + "\n" +
                "birligi : " + reviews.get(11) + "\n" +
                "kami bor : " + reviews.get(12) + "\n\n" +
                "qo'shimcha ma'lumot : \n" + reviews.get(13);

        return review_string;

    }

    @Override
    public String getReviewText() {
        return showReviewView(presenter.getInfosFromIntent());
    }

    @Override
    public void changeClicked() {
        finish();
    }

    public void goToPost_Page3(ArrayList<String> reviews, ArrayList<Uri> images_uri) {

        Intent intent = new Intent(Post_Page2.this,Post_Page3.class);
        intent.putStringArrayListExtra(REVIEWS_EXTRA,reviews);
        intent.putExtra(IMAGES_URI_EXTRA,images_uri);
        startActivity(intent);

    }
}