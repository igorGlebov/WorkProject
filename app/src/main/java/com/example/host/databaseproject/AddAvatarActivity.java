package com.example.host.databaseproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class AddAvatarActivity extends AppCompatActivity implements Datable {

    private Button addAvatarButton;
    private Button skipAvatarButton;
    private static final int CAMERA_REQUEST = 0;
    static final int GALLERY_REQUEST = 1;
    private ImageView imageAvatar; // фотка
    Intent intent;//чтобы забрать экземпляр пользователя
    FirebaseDatabase database;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_avatar);

        addAvatarButton = findViewById(R.id.addAvatarButton);
        skipAvatarButton = findViewById(R.id.skipAvatarButton);
        imageAvatar = findViewById(R.id.imageView);

        //Подпихнули пользователя
        database = FirebaseDatabase.getInstance();
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        //


        addAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogFragment dialogFragment = new CustomDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "custom");
                //addUserToDatabase();//Добавим пользователя в базу

            }
        });


        //Вызовется в случае нажатия "Пропустить". Как ава прикрутится параша по умолчанию (Наверное))0))
        skipAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = ((BitmapDrawable)imageAvatar.getDrawable()).getBitmap();
                user.setAvatar(bitmap);
                //addUserToDatabase();//Добавим пользователя в базу

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
            user.setAvatar(thumbnailBitmap);//Не ебу, что я тут понаделал. Уебет? Не должно
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
                        user.setAvatar(bitmap);//Не ебу, что я тут понаделал. Уебет? Не должно - 2
                    }
            }
        }
        addUserToDatabase();//Добавим пользователя в базу

    }

    @Override
    public void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }


    private void addUserToDatabase(){ //TODO
        DatabaseReference userRef = database.getReference("Users").child(user.getUserID().toString());

        userRef.setValue(user);

    }
}
