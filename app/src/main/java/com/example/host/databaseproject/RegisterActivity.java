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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAnalytics firebaseAnalytics;


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

        buttonRegister = findViewById(R.id.buttonRegister);

        nameTextRegister = findViewById(R.id.nameText);
        secondNameTextRegister = findViewById(R.id.secondNameText);
        fatherNameTextRegister = findViewById(R.id.fatherNameText);

        emailTextRegister = findViewById(R.id.emailTextRegister);
        passwordTextRegister = findViewById(R.id.passwordTextRegister);
        passwordTextRegister2 = findViewById(R.id.passwordTextRegister2);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUp();
            }
        });
    }

    private void startSignUp() {
        String email = emailTextRegister.getText().toString();
        String password = passwordTextRegister.getText().toString();

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

        if (!Check.checkName(nameTextRegister.getText().toString())){
            Toast.makeText(RegisterActivity.this, "Проверьте имя!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!Check.checkName(secondNameTextRegister.getText().toString())){
            Toast.makeText(RegisterActivity.this, "Проверьте фамилию!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!Check.checkName(fatherNameTextRegister.getText().toString())){
            Toast.makeText(RegisterActivity.this, "Проверьте отчество!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Ошибка при регистрации! Что-то пошло не так!", Toast.LENGTH_LONG).show();
                }
                else{
                    FirebaseUser user = mAuth.getCurrentUser();
                    //user.sendEmailVerification(); // отправления письма на почту
                    firebaseAnalytics.setUserProperty("Name", nameTextRegister.getText().toString());
                    startActivity(new Intent(RegisterActivity.this, AddAvatarActivity.class));
                }
            }
        });

    }
}
