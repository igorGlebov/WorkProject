package com.example.host.databaseproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.host.databaseproject.OurClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SettingsActivity2 extends AppCompatActivity {

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private Button changePasswordButton;
    private ImageButton changeAvatarButton;



    //private ImageView avatarView;
    private EditText nameView;
    private EditText emailView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        changeAvatarButton = findViewById(R.id.changeAvatarButton);
        changePasswordButton = findViewById(R.id.changePasswordSettingsButton);



        //avatarView = findViewById(R.id.settingsAvatarView);
        nameView = findViewById(R.id.nameSettingsView);
        emailView = findViewById(R.id.emailSettingsView);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        final DatabaseReference userReference = database.getReference("Users");

        //Смена аватара
        //TODO
        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                String name, surname, fatherName, email;
                name = dataSnapshot.child("name").getValue(String.class);
                surname = dataSnapshot.child("surname").getValue(String.class);
                fatherName = dataSnapshot.child("fatherName").getValue(String.class);
                email = dataSnapshot.child("email").getValue(String.class);


                nameView.setText(surname + " " + name + " " + fatherName);
                emailView.setText(email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SettingsActivity2.this, "Ошибка при считывании данных пользоветеля", Toast.LENGTH_LONG);
            }
        });



    }
}
