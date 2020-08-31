package com.example.mashinabozor.Presenters;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.widget.Toast;

import com.example.mashinabozor.Adapters.Upload_Images_Adapter;
import com.example.mashinabozor.Veiws.Post_Page1;
import com.example.mashinabozor.Veiws.Post_Page2;
import com.example.mashinabozor.Dialogs.Review_dialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class page2_presenter {

    private Post_Page2 view;

    private ArrayList<Uri> images_uri;
    private ArrayList<String> images_names;
    private ArrayList<String> reviews;

    private Upload_Images_Adapter adapter;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    public page2_presenter(Post_Page2 view) {

        this.view = view;
        images_uri = new ArrayList<>();
        images_names = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    public void createAdapter() {

        adapter = new Upload_Images_Adapter(images_uri, images_names);
        view.setAdapterToRecyclerview(adapter);

        adapter.setOnCLickListener(new Upload_Images_Adapter.OnClickListener() {
            @Override
            public void onclick(int position) {

                images_uri.remove(position);
                images_names.remove(position);
                adapter.notifyDataSetChanged();

            }
        });

    }

    public void pick_Images() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        view.startActivityForResult(Intent.createChooser(intent, "Rasmlarni tanlang"), 1);

    }

    public void showSelectedImages(Intent data) {

        int images_count = data.getClipData().getItemCount();

        for (int i = 0; i < images_count; i++) {

            Uri image_uri = data.getClipData().getItemAt(i).getUri();
            String image_name = getFileName(image_uri);

            images_uri.add(image_uri);
            images_names.add(image_name);

        }

        adapter.notifyDataSetChanged();

    }

    public String getFileName(Uri uri) {

        String fileName = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = view.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }

            } finally {
                cursor.close();
            }

        }

        if (fileName != null) {
            fileName = uri.getPath();
            int cut = fileName.lastIndexOf("/");
            if (cut != -1) {
                fileName = fileName.substring(cut + 1);
            }
        }

        return fileName;

    }

    public void finishBtnClick() {


        if (images_uri.size() >= 3 && images_uri.size() <= 10) {
            getInfosFromIntent();
            view.goToPost_Page3(reviews, images_uri);
        } else if (images_uri.size() < 3) {
            Toast.makeText(view, "Rasmlar soni kamida 3 ta bo'lishi kerak!", Toast.LENGTH_SHORT).show();
        } else if (images_uri.size() > 10) {
            Toast.makeText(view, "10 tadan ko'p rasm mumkin emas!", Toast.LENGTH_SHORT).show();
        } else if (images_uri.size()==0){
            Toast.makeText(view, "Mashinaning kamida 3 ta rasmini kiriting", Toast.LENGTH_SHORT).show();
        }

    }


    public void reviewBtnClicked() {


        getInfosFromIntent();
        Review_dialog dialog = new Review_dialog();
        view.getReviewText();
        dialog.show(view.getSupportFragmentManager(), "review");

    }

    public ArrayList<String> getInfosFromIntent() {

        Intent intent = view.getIntent();
        reviews.add(intent.getStringExtra(Post_Page1.CAR_TYPE_EXTRA));
        reviews.add(intent.getStringExtra(Post_Page1.CAR_BRAND_EXTRA));
        reviews.add(intent.getStringExtra(Post_Page1.CAR_MODEL_EXTRA));
        reviews.add(intent.getStringExtra(Post_Page1.CAR_YEAR_EXTRA));
        reviews.add(intent.getStringExtra(Post_Page1.CAR_BODY_EXTRA));
        reviews.add(intent.getStringExtra(Post_Page1.CAR_ENGINE_EXTRA));
        reviews.add(intent.getStringExtra(Post_Page1.CAR_GEAR_EXTRA));
        reviews.add(intent.getStringExtra(Post_Page1.CAR_DRIVING_EXTRA));
        reviews.add(intent.getStringExtra(Post_Page1.CAR_FUEL_EXTRA));
        reviews.add(intent.getStringExtra(Post_Page1.CAR_DISTANCE_EXTRA));
        reviews.add(intent.getStringExtra(Post_Page1.CAR_PRICE_EXTRA));
        reviews.add(intent.getStringExtra(Post_Page1.CAR_PRICE_UNIT_EXTRA));
        if (intent.getBooleanExtra(Post_Page1.CAR_HAS_LESS_EXTRA, false)) {
            reviews.add("kami bor");
        } else {
            reviews.add("");
        }
        reviews.add(intent.getStringExtra(Post_Page1.EXTRA_INFO_EXTRA));

        return reviews;

    }
}


