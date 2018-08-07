package com.example.host.databaseproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    private Datable datable;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        datable = (Datable) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] actions = {"Сделать фото", "Выбрать фото из галереи"};
        return builder
                .setTitle("Диалоговое окно")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setItems(actions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            datable.openCamera();
                        }
                        else{
                            datable.openGallery();
                        }
                    }
                })
                .create();
        //return super.onCreateDialog(savedInstanceState);
    }


}
