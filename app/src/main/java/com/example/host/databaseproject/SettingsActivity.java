package com.example.host.databaseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

//    private FirebaseUser currentUser;
//    private FirebaseAuth mAuth;
//    private FirebaseDatabase database;
//
//    private Button changeEmailButton;
//    private Button changePasswordButton;
//    private Button changeNameButton;
//
//    private ImageView avatarView;
//    private TextView nameView;
//    private TextView emailView;
//    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        changeEmailButton = findViewById(R.id.changeEmailSettingsButton);
//        changePasswordButton = findViewById(R.id.changePasswordSettingsButton);
//        changeNameButton = findViewById(R.id.changeNameSettingsButton);
//
//        avatarView = findViewById(R.id.settingsAvatarView);
//        nameView = findViewById(R.id.nameSettingsView);
//        emailView = findViewById(R.id.emailSettingsView);
//
//        database = FirebaseDatabase.getInstance();
//        mAuth = FirebaseAuth.getInstance();
//        currentUser = mAuth.getCurrentUser();
//
//        DatabaseReference userReference = database.getReference("Users").child(currentUser.getUid().toString());
//
//        //
//        //Дергаем данные пользователя и выводим их куда-то там
//        //ГЫ))))00)
//        //Так, у нас тут серьезный проект ебана, собрались
//        userReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                user = (User)dataSnapshot.getValue();
//                nameView.setText(user.getSurname() + " " + user.getName() + " " + user.getFatherName());
//                emailView.setText(user.getEmail());
//                avatarView.setImageBitmap(user.getAvatar());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(SettingsActivity.this, "Ошибка в загрузке данных пользователя", Toast.LENGTH_LONG);
//                return;
//            }
//        });
//
//


    }
}
