package com.example.host.databaseproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.host.databaseproject.OurClasses.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class SettingsActivity2 extends AppCompatActivity implements Datable {

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private StorageReference storageReference;
    private DatabaseReference userReference;


    private Button changePasswordButton;
    private ImageButton changeAvatarButton;



    //For avatar change
    private static final int CAMERA_REQUEST = 0;
    static final int GALLERY_REQUEST = 1;
    private ImageView imageAvatar; // фотка
    //

    private String name, surname, fatherName, email;


    //private ImageView avatarView;
    private EditText nameView;
    private EditText surnameView;
    private EditText fatherNameView;
    private EditText emailView;


    //Итак, что тут работает
    //1) Смена аватара
    //2) Смена и вывод ФИО и мыла
    //3) Криво работает смена пароля


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        changeAvatarButton = findViewById(R.id.changeAvatarButton);
        changePasswordButton = findViewById(R.id.changePasswordSettingsButton);



        //avatarView = findViewById(R.id.settingsAvatarView);
        nameView = findViewById(R.id.nameSettingsView);
        surnameView = findViewById(R.id.surnameSettingsView);
        fatherNameView = findViewById(R.id.fatherNameSettingsView);
        emailView = findViewById(R.id.emailSettingsView);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();

        //fix#1
        userReference = database.getReference("Users");
        storageReference = storage.getReference(currentUser.getUid());

        //Смена аватара
        //TODO
        //100% Будут траблы с работой
        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogFragment dialogFragment = new CustomDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "custom");
            }
        });

        //Смена пароля
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity2.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        //
        //Дергаем данные пользователя и выводим их куда-то там
        //
        //Данные-строки
        userReference.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("name").getValue(String.class);
                surname = dataSnapshot.child("surname").getValue(String.class);
                fatherName = dataSnapshot.child("fatherName").getValue(String.class);
                email = dataSnapshot.child("email").getValue(String.class);

                nameView.setText(name);
                surnameView.setText(surname);
                fatherNameView.setText(fatherName);
                emailView.setText(email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SettingsActivity2.this, "Ошибка при считывании данных пользоветеля", Toast.LENGTH_LONG);
            }
        });


        //Аватарка

//        File tmpFile = null;
//        try {
//            tmpFile = File.createTempFile("tmpAvatar", "png");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        storageReference.getFile(tmpFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
//                if(task.isSuccessful()){
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                    Bitmap b = BitmapFactory.decodeFile(tmpFile.getPath().toString(), options);
//
//                    changeAvatarButton.setImageBitmap(b);
//                }
//                else{
//                    Toast.makeText(SettingsActivity2.this, "Ошибка при загрузке изображения из базы", Toast.LENGTH_LONG);
//                }
//            }
//        });
//         storageReference.child("avatar.png").getStream().addOnCompleteListener(new OnCompleteListener<StreamDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<StreamDownloadTask.TaskSnapshot> task) {
//                if(task.isSuccessful()){
//                    Bitmap b;
//                    b = BitmapFactory.decodeStream(task.getResult().getStream());
//                    changeAvatarButton.setImageBitmap(b);
//                }
//                else{
//                    Toast.makeText(SettingsActivity2.this, "Ошибка при загрузке изображения из базы", Toast.LENGTH_LONG);
//                }
//            }
//
//        });

    }

    //Все изменится при закрытии окна настроек
    @Override
    protected void onStop() {
        super.onStop();
        if(!nameView.getText().toString().isEmpty() && !nameView.getText().toString().equals(name)){
            userReference.child(currentUser.getUid()).child("name").setValue(nameView.getText().toString());
        }
        if(!surnameView.getText().toString().isEmpty() && !surnameView.getText().toString().equals(surname)){
            userReference.child(currentUser.getUid()).child("surname").setValue(surnameView.getText().toString());
        }
        if(!fatherNameView.getText().toString().isEmpty() && !fatherNameView.getText().toString().equals(fatherName)){
            userReference.child(currentUser.getUid()).child("fatherName").setValue(fatherNameView.getText().toString());
        }
        if(!emailView.getText().toString().isEmpty() && !emailView.getText().toString().equals(email)){
            userReference.child(currentUser.getUid()).child("email").setValue(emailView.getText().toString());
            currentUser.updateEmail(emailView.getText().toString());
        }
    }
    //


    //Копипаста из ЭДД АВАТАРа
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
            //user.setAvatar(thumbnailBitmap);//Не ебу, что я тут понаделал. Уебет? Не должно
            changeAvatarInStorage(thumbnailBitmap);


            //

            changeAvatarButton.setImageBitmap(thumbnailBitmap);
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
                        changeAvatarInStorage(bitmap);
                        changeAvatarButton.setImageBitmap(bitmap);

                    }
            }
        }
        //addUserToDatabase();//Добавим пользователя в базу

    }

    @Override
    public void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }
    ///////

    void changeAvatarInStorage(Bitmap avatar){
        storageReference.child("avatar.png").delete();
        ByteArrayOutputStream avatarBytesStream = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.PNG, 100, avatarBytesStream);
        byte[] avatarBytes = avatarBytesStream.toByteArray();
        storageReference.child(currentUser.getUid());
        StorageReference newRef = storageReference.child("avatar.png");
        UploadTask uploadTask = newRef.putBytes(avatarBytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SettingsActivity2.this, "Что-то не так с загрузкой нового изображения в базу. Проверьте соединение и повторите попытку.", Toast.LENGTH_LONG);

            }
        });
    }

}
