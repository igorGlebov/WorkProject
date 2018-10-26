package com.example.host.databaseproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CustomDialogFragment extends DialogFragment {


    private static final int CAMERA_REQUEST = 0;
    static final int GALLERY_REQUEST = 1;
    private Datable datable;
    private StorageReference storageReference;
    private FirebaseUser currentUser;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        datable = (Datable) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference().child(currentUser.getUid()).child("avatar.png");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] actions = {"Сделать фото", "Выбрать фото из галереи"};
        return builder
                .setTitle("Выберите метод добавления аватара:")
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
    }
}
