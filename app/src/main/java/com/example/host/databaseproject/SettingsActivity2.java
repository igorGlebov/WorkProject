package com.example.host.databaseproject;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
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

    private Button changeEmailButton;
    private Button changePasswordButton;
    private Button changeNameButton;

    private ImageView avatarView;
    private TextView nameView;
    private TextView emailView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
                changeEmailButton = findViewById(R.id.changeEmailSettingsButton2);
        changePasswordButton = findViewById(R.id.changePasswordSettingsButton2);
        changeNameButton = findViewById(R.id.changeNameSettingsButton2);

        avatarView = findViewById(R.id.settingsAvatarView2);
        nameView = findViewById(R.id.nameSettingsView2);
        emailView = findViewById(R.id.emailSettingsView2);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //DatabaseReference userReference = database.getReference("Users").child(currentUser.getUid().toString());
        DatabaseReference userReference = database.getReference("Users");


        //
        //Дергаем данные пользователя и выводим их куда-то там
        //

         userReference.child("Users").child(mAuth.getUid()).child("avatar").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 Bitmap ava = dataSnapshot.getValue(Bitmap.class);
//                 user = dataSnapshot.getValue(User.class);
//                 nameView.setText(user.getSurname() + " " + user.getName() + " " + user.getFatherName());
//                 emailView.setText(user.getEmail());
//                 avatarView.setImageBitmap(user.getAvatar());
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {
                 Toast.makeText(SettingsActivity2.this, "Ошибка в загрузке данных пользователя", Toast.LENGTH_LONG);
                 return;
             }
         });

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
