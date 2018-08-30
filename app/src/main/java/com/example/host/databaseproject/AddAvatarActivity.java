package com.example.host.databaseproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.host.databaseproject.OurClasses.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddAvatarActivity extends AppCompatActivity implements Datable {

    private Button addAvatarButton;
    private Button continueAvatarButton;
    private static final int CAMERA_REQUEST = 0;
    static final int GALLERY_REQUEST = 1;
    private ImageView imageAvatar; // фотка
    Intent intent;//чтобы забрать экземпляр пользователя
    FirebaseDatabase database;
    private User user;

    private FirebaseStorage usersStorage;
    private StorageReference storageReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_avatar);

        addAvatarButton = findViewById(R.id.addAvatarButton);
        continueAvatarButton = findViewById(R.id.continueAvatarButton);
        imageAvatar = findViewById(R.id.imageView);

        //Подпихнули пользователя
        database = FirebaseDatabase.getInstance();
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        //

        //Хранилище
        usersStorage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        //


        addAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogFragment dialogFragment = new CustomDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "custom");
                //addUserToDatabase();//Добавим пользователя в базу
                //addUserToDatabase();//Добавим пользователя в базу

                //startActivity(new Intent(AddAvatarActivity.this, LoginActivity.class));


            }
        });


        //Вызовется в случае нажатия "Пропустить". Как ава прикрутится параша по умолчанию (Наверное))0))
        continueAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = ((BitmapDrawable)imageAvatar.getDrawable()).getBitmap();
                user.setAvatar(bitmap);
                addUserToDatabase();//Добавим пользователя в базу

                startActivity(new Intent(AddAvatarActivity.this, LoginActivity.class));


            }
        });



    }

    //@Override
    public void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);


    }

    //@Override
    public void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
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
        //addUserToDatabase();//Добавим пользователя в базу

    }




    private void addUserToDatabase(){
        putAvatarToStorage(((BitmapDrawable)imageAvatar.getDrawable()).getBitmap());

        DatabaseReference userRef = database.getReference("Users").child(user.getUserID().toString());

        userRef.setValue(user);

    }

    void putAvatarToStorage(Bitmap avatar){
        ByteArrayOutputStream avatarBytesStream = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.PNG, 100, avatarBytesStream);
        byte[] avatarBytes = avatarBytesStream.toByteArray();
        storageReference.child(user.getUserID());
        StorageReference newRef = storageReference.child(user.getUserID() + "/avatar.png");
        UploadTask uploadTask = newRef.putBytes(avatarBytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddAvatarActivity.this, "Что-то не так с загрузкой изображения в базу. Проверьте соединение и повторите попытку.", Toast.LENGTH_LONG).show();

            }
        });
    }


}
