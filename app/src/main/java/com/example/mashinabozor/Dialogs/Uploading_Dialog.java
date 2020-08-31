package com.example.mashinabozor.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mashinabozor.R;

public class Uploading_Dialog extends AppCompatDialogFragment {

    View view;
    ProgressBar progressBar;
    TextView progressText;
    TextView progress_dialog_text;
    Button back_to_main;
    backToMainListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        return dialog;

    }

    public void setProgressBar(float progress) {

        progressBar.setProgress((int) progress);
        progressText.setText(((int) progress) + "%");
    }

    public void uploadFinished(String postId) {

        progress_dialog_text.setText("mashina sotuvga qo'yildi!\ne'lon raqami: " + postId);
        back_to_main.setVisibility(View.VISIBLE);

        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.backToMain();
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.upload_dialog_layout, null);
        progressBar = view.findViewById(R.id.upload_progressBar);
        progressText = view.findViewById(R.id.progress_text);
        progress_dialog_text = view.findViewById(R.id.progress_dialog_text);
        back_to_main = view.findViewById(R.id.back_to_main_btn);


        try {
            this.listener = (backToMainListener) context;
        } catch (Exception e) {
            Log.d("TTT", e.getMessage());
        }

    }

    public interface backToMainListener {

        public void backToMain();

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {

        Log.d("TTT", "dismissed!");
    }
}
