package com.example.datingapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.datingapp.R;

public class Dialog {

    private Activity activity;
    private AlertDialog dialog;

    public Dialog(Activity activity) {

        this.activity = activity;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_layout,null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    public void Dismiss()
    {
        dialog.dismiss();
    }
}
