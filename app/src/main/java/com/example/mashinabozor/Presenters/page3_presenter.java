package com.example.mashinabozor.Presenters;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mashinabozor.Classes.Post;
import com.example.mashinabozor.Dialogs.Uploading_Dialog;
import com.example.mashinabozor.Models.Repository;
import com.example.mashinabozor.Veiws.Post_Page2;
import com.example.mashinabozor.Veiws.Post_Page3;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class page3_presenter {

    private Post_Page3 view;
    private Repository repository;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private String phone;
    private ArrayList<Uri> images_uri;
    private ArrayList<String> ids_arraylist;
    Uploading_Dialog dialog;
    private long timeInMillis;
    private float progress = 0;
    private float progress_part = 0;
    private int postId;
    private String image_links = "";


    public page3_presenter(Post_Page3 view) {
        this.view = view;
        repository = new Repository();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("posts");
        mStorageReference = FirebaseStorage.getInstance().getReference("images");
        phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        timeInMillis = System.currentTimeMillis();

        ids_arraylist = new ArrayList<>();

        dialog = new Uploading_Dialog();
        images_uri = view.getIntent().getParcelableArrayListExtra(Post_Page2.IMAGES_URI_EXTRA);
        progress = progress_part = (float) (100 / images_uri.size());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users/posts/");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        Post post = dataSnapshot1.getValue(Post.class);

                        ids_arraylist.add(post.getPostId());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        {


        }
    }


    public void sellClicked() {


        if (view.getSelectedRegion().equals("")) {

            Toast.makeText(view, "Viloyatni kiriting!", Toast.LENGTH_SHORT).show();
            return;

        }

        if (view.getActiveNumber().isEmpty()) {

            view.setRequest("telefon raqamni kiriting!");
            return;

        }

        if (!view.getActiveNumber().startsWith("+998") || view.getActiveNumber().length() != 13) {

            view.setRequest("Noto'g'ri raqam kiritildi!");
            return;

        }


        ArrayList<String> car_data = view.getIntent().getStringArrayListExtra(Post_Page2.REVIEWS_EXTRA);

        do {
            postId = (int) (Math.random() * 100000000);

        } while (checkIdExistance(postId));


        final Post single_post = new Post(car_data.get(0),
                car_data.get(1),
                car_data.get(2),
                car_data.get(3),
                car_data.get(4),
                car_data.get(5),
                car_data.get(6),
                car_data.get(7),
                car_data.get(8),
                car_data.get(9),
                car_data.get(10),
                car_data.get(11),
                car_data.get(12),
                car_data.get(13));

        single_post.setTimeInMillis(String.valueOf(timeInMillis));
        single_post.setPostId(String.valueOf(postId));
        single_post.setRegion(view.getSelectedRegion());
        single_post.setDistrict(view.getSelectedDistrict());
        single_post.setActive_number(view.getActiveNumber());
        single_post.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

        mDatabaseReference.child(phone).child(String.valueOf(timeInMillis)).setValue(single_post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.show(view.getSupportFragmentManager(), "uploading");
                if (task.isSuccessful()) {

                    Log.d("TTT", "1");


                } else {
                    Toast.makeText(view, "hatolik yuz berdi!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        for (int i = 0; i < images_uri.size(); i++) {

            StorageReference storageReference = mStorageReference.child(phone).child(System.currentTimeMillis() + "." + getFileExtension(images_uri.get(i)));
            storageReference.putFile(images_uri.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Log.d("TTT", "2");
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloaduri = uriTask.getResult();

                    image_links += downloaduri.toString() + ",";

                    Log.d("TTT", "progress = " + progress_part);
                    dialog.setProgressBar(progress);

                    if (progress == 100) {

                        single_post.setImages_links(image_links);
                        mDatabaseReference.child(phone).child(String.valueOf(timeInMillis)).setValue(single_post);
                        dialog.uploadFinished(String.valueOf(postId));

                    }

                    progress += progress_part;
                    if (progress > 90 && progress < 100) {
                        progress = 100;
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(view, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }


    }

    private boolean checkIdExistance(int id) {

        Log.d("TTT", id + "???" + ids_arraylist.size());
        for (int i = 0; i < ids_arraylist.size(); i++) {

//            Log.d("TTT", i + "");
            if (ids_arraylist.get(i).equals(String.valueOf(id))) {
                Log.d("TTT", "true");
                return true;
            }

        }

        Log.d("TTT", "false");
        return false;

    }

    private String getFileExtension(Uri uri) {

        ContentResolver contentResolver = view.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    public void setRegionSpinner() {

        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(view, android.R.layout.simple_spinner_dropdown_item, repository.getRegion(view));
        view.setRegionAdapter(regionAdapter);

    }

    public void setDistrictSpinner() {
        if (!view.getSelectedRegion().equals("")) {
            ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(view, android.R.layout.simple_spinner_dropdown_item, repository.getDistricts(view, view.getSelectedRegion()));
            view.setDistrictsAdapter(districtAdapter);
        }
    }

}
