package com.example.mashinabozor.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Review_dialog extends AppCompatDialogFragment {

    private dialogInterface listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle("Ma'lumotlarni tekshiring")
                .setPositiveButton("yaxshi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("o'zgartirish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        listener.changeClicked();

                    }
                })
                .setMessage(getInfos());


        AlertDialog dialog1 = dialog.create();
        return dialog1;

    }

    private String getInfos(){


        return listener.getReviewText();

    }

    public interface dialogInterface{

        String getReviewText();
        void changeClicked();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {

        listener = (dialogInterface) context;
        }catch (ClassCastException e){

           throw new ClassCastException("must implement interface!");

        }

    }
}
