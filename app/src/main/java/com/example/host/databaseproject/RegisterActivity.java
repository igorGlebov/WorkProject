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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

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
        String password2 = passwordTextRegister2.getText().toString();




        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Ошибка при регистрации", Toast.LENGTH_LONG).show();
                }
                else{
                    startActivity(new Intent(RegisterActivity.this, AddAvatarActivity.class));
                }
            }
        });
    }
}
