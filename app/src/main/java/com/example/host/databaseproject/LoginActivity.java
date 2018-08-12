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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private Button registerButtonMain; // кнопки для регистрации в авторизации
    private Button signupButtonMain; // кнопка

    private EditText emailTextRegister;
    private EditText passwordTextRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //startActivity(new Intent(LoginActivity.this, AccountActivity.class));

        mAuth = FirebaseAuth.getInstance();

        registerButtonMain = findViewById(R.id.RegisterButtonMain);
        signupButtonMain = findViewById(R.id.SignInButtonMain);

        emailTextRegister = findViewById(R.id.emailTextRegister);
        passwordTextRegister = findViewById(R.id.passwordTextRegister2);

        registerButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        signupButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });



    }


    private void startSignIn(){
        String email = emailTextRegister.getText().toString();
        String password = passwordTextRegister.getText().toString();

        if(!Check.checkEmail(email)){
            Toast.makeText(LoginActivity.this, "Неверный E-mail", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Ошибка при входе", Toast.LENGTH_LONG).show();
                }
                else{
                   //startActivity(new Intent(LoginActivity.this, AccountActivity.class));
                    //startActivity(new Intent(LoginActivity.this, SettingsActivity2.class));
                    startActivity(new Intent(LoginActivity.this, CatalogueActivity.class));


                }
            }

        });
    }
}
