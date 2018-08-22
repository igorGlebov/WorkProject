package com.example.host.databaseproject;

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

        //DatabaseReference userReference = database.getReference("Users").child(currentUser.getUid().toString());
        final DatabaseReference userReference = database.getReference("Users");

        //Смена аватара
        //TODO
        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        //
        //Дергаем данные пользователя и выводим их куда-то там
        //

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

//         userReference.child("Users").child(mAuth.getUid()).child("avatar").addListenerForSingleValueEvent(new ValueEventListener() {
//             @Override
//             public void onDataChange(DataSnapshot dataSnapshot) {
//                 Bitmap ava = dataSnapshot.getValue(Bitmap.class);
////                 user = dataSnapshot.getValue(User.class);
////                 nameView.setText(user.getSurname() + " " + user.getName() + " " + user.getFatherName());
////                 emailView.setText(user.getEmail());
////                 avatarView.setImageBitmap(user.getAvatar());
//             }
//
//             @Override
//             public void onCancelled(DatabaseError databaseError) {
//                 Toast.makeText(SettingsActivity2.this, "Ошибка в загрузке данных пользователя", Toast.LENGTH_LONG);
//                 return;
//             }
//         });

//        userReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    user = new User(null, null, null, null, null, null);
//                    user = dataSnapshot.getValue(User.class);
//                    //user = (User)dataSnapshot.getValue();
//                    nameView.setText(user.getSurname() + " " + user.getName() + " " + user.getFatherName());
//                    emailView.setText(user.getEmail());
//                    avatarView.setImageBitmap(user.getAvatar());
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(SettingsActivity2.this, "Ошибка в загрузке данных пользователя", Toast.LENGTH_LONG);
//                return;
//            }
//        });


    }
}
