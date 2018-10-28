package com.example.host.databaseproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment   {


    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private StorageReference storageReference;
    private DatabaseReference userReference;


    Button changePasswordButton;
    private ImageButton changeAvatarButton;



    //For avatar change
    private CustomDialogFragment customDialogFragment;
    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    private ImageView imageAvatar; // фотка
    //

    private String name, surname, fatherName, email;


    //private ImageView avatarView;
    private EditText nameView;
    private EditText surnameView;
    private EditText fatherNameView;
    private EditText emailView;


    public SettingsFragment()  {
        // Required empty public constructor



    }

    public void changeAvatar(Bitmap bitmap){ // метод для изменения аватара в меню настроек
        changeAvatarButton.setImageBitmap(bitmap);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings,
                container, false);


        changeAvatarButton = view.findViewById(R.id.changeAvatarButton);
        changePasswordButton = view.findViewById(R.id.changePasswordSettingsButton);

        //avatarView = findViewById(R.id.settingsAvatarView);
        nameView = view.findViewById(R.id.nameSettingsView);
        surnameView = view.findViewById(R.id.surnameSettingsView);
        fatherNameView = view.findViewById(R.id.fatherNameSettingsView);
        emailView = view.findViewById(R.id.emailSettingsView);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();

        //fix#1
        userReference = database.getReference("Users");
        storageReference = storage.getReference(currentUser.getUid());

        //Смена аватара
        //100% Будут траблы с работой
        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialogFragment.show(getSupportFragmentManager(), "custom");
                //dialogFragment.show(getChildFragmentManager(), "custom");

                //FragmentManager fm = getActivity().();

                CustomDialogFragment dialogFragment = new CustomDialogFragment();
                //dialogFragment.setTargetFragment(SettingsFragment.this, 1);
                dialogFragment.show(getFragmentManager(), "custom");






            }
        });

        //Смена пароля
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
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
                Toast.makeText(getActivity(), "Ошибка при считывании данных пользоветеля", Toast.LENGTH_LONG).show();
            }
        });


        //Аватарка

        //Glide.with(this).load(storageReference.child("avatar.png").getDownloadUrl()).into(changeAvatarButton);
        storageReference.child("avatar.png").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Glide.with(SettingsFragment.this).load(task.getResult()).into(changeAvatarButton);
                }
                else{
                    Toast.makeText(getActivity(), "Ошибка при загрузке аватара из базы. Проверьте соединение.", Toast.LENGTH_LONG).show();

                }
            }
        });







        // Inflate the layout for this fragment

        //return inflater.inflate(R.layout.fragment_settings, container, false);


        //storageReference.child("avatar.png").

        return view;



    }




    @Override
    public void onStop() {
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
            mAuth.getCurrentUser().updateEmail(emailView.getText().toString());

        }
    }

    //Копипаста из ЭДД АВАТАРа
//    @Override
//    public void openCamera() {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);
//    }
//
//        @Override
//    public void openGallery() {
//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) { // не удалять, для снимка
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
////            // Фотка сделана, извлекаем картинку
////            Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
////            changeAvatarInStorage(thumbnailBitmap);
////
////            changeAvatarButton.setImageBitmap(thumbnailBitmap);
////        }
////        else{
////            Bitmap bitmap = null;
////
////            switch(requestCode) {
////                case GALLERY_REQUEST:
////                    if(resultCode == getActivity().RESULT_OK){
////                        Uri selectedImage = data.getData();
////                        try {
////                            //ALERT CAN BE AN OSHIBKE HERE
////                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                        changeAvatarInStorage(bitmap);
////                        changeAvatarButton.setImageBitmap(bitmap);
////
////                    }
////            }
////        }

        storageReference.child("avatar.png").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Glide.with(SettingsFragment.this).load(task.getResult()).into(changeAvatarButton);
                }
                else{
                    Toast.makeText(getActivity(), "Ошибка при загрузке аватара из базы. Проверьте соединение.", Toast.LENGTH_LONG).show();

                }
            }
        });

    }


//    ///////
//



}
