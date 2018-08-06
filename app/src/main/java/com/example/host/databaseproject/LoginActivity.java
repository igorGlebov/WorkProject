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

    private Button registerButtonMain;
    private Button signupButtonMain;

    private EditText emailTextRegister;
    private EditText passwordTextRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
                else{
                   startActivity(new Intent(LoginActivity.this, AccountActivity.class));
                }
            }
        });
    }
}
