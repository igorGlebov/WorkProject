package com.example.host.databaseproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class AddAvatarActivity extends AppCompatActivity implements Datable {

    private Button addAvatarButton;
    private static final int CAMERA_REQUEST = 0;
    static final int GALLERY_REQUEST = 1;
    private ImageView imageAvatar; // фотка

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_avatar);

        addAvatarButton = findViewById(R.id.addAvatarButton);
        imageAvatar = findViewById(R.id.imageView);

        addAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogFragment dialogFragment = new CustomDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "custom");
            }
        });

    }

    @Override
    public void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // не удалять, для снимка
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            // Фотка сделана, извлекаем картинку
            Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
            imageAvatar.setImageBitmap(thumbnailBitmap);
        }
        else{
            Bitmap bitmap = null;

            switch(requestCode) {
                case GALLERY_REQUEST:
                    if(resultCode == RESULT_OK){
                        Uri selectedImage = data.getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageAvatar.setImageBitmap(bitmap);
                    }
            }
        }
    }

    @Override
    public void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }
}
