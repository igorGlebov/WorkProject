package com.example.host.databaseproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private User user;

    Button buttonRegister;

    EditText nameTextRegister;
    EditText secondNameTextRegister;
    EditText fatherNameTextRegister;


    EditText emailTextRegister;
    EditText passwordTextRegister;
    EditText passwordTextRegister2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        buttonRegister = findViewById(R.id.buttonRegister);

        nameTextRegister = findViewById(R.id.nameText);
        secondNameTextRegister = findViewById(R.id.secondNameText);
        fatherNameTextRegister = findViewById(R.id.fatherNameText);

        emailTextRegister = findViewById(R.id.emailTextRegister);
        passwordTextRegister = findViewById(R.id.passwordTextRegister);
        passwordTextRegister2 = findViewById(R.id.passwordTextRegister2);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUp();
            }
        });
    }

    private void startSignUp() {
        final String email = emailTextRegister.getText().toString();
        String password = passwordTextRegister.getText().toString();
        final String name = nameTextRegister.getText().toString();
        final String surname = secondNameTextRegister.getText().toString();
        final String fatherName = fatherNameTextRegister.getText().toString();

        if(!Check.checkEmail(email)){
            Toast.makeText(RegisterActivity.this, "Неверная структура e-mail!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!Check.comparePassword(password, passwordTextRegister2.getText().toString())){
            Toast.makeText(RegisterActivity.this, "Пароли не совпадают!", Toast.LENGTH_LONG).show();
            return;
        }

        if(!Check.checkPassword(password)){
            Toast.makeText(RegisterActivity.this, "Усложните пароль! Он обязательно должен содержать символы высокого и низкого регистра и цифры", Toast.LENGTH_LONG).show();
            return;
        }

        if (!Check.checkName(name)){
            Toast.makeText(RegisterActivity.this, "Проверьте имя!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!Check.checkName(surname)){
            Toast.makeText(RegisterActivity.this, "Проверьте фамилию!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!Check.checkName(fatherName)){
            Toast.makeText(RegisterActivity.this, "Проверьте отчество!", Toast.LENGTH_LONG).show();
            return;
        }

        //user = new User(email, name, surname, fatherName);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Ошибка при регистрации! Что-то пошло не так!", Toast.LENGTH_LONG).show();
                }
                else{
                    FirebaseUser user = mAuth.getCurrentUser();
                    //user.sendEmailVerification(); // отправления письма на почту
                    //firebaseAnalytics.setUserProperty("Name", nameTextRegister.getText().toString());
                    Intent intent = new Intent(RegisterActivity.this, AddAvatarActivity.class);
                    intent.putExtra("user", new User(email,name, surname,fatherName, mAuth.getUid().toString()));

                    startActivity(intent);
                }
            }
        });

        //addUserToDatabase(new User(email, name, surname, fatherName, mAuth.getCurrentUser().getUid()));

    }

    private void addUserToDatabase(User user){
        DatabaseReference userRef = database.getReference("Users").child(user.getUserID().toString());
        userRef.setValue(user);

    }



}



