package com.example.host.databaseproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.host.databaseproject.OurClasses.Check;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private EditText oldPasswordText;
    private EditText newPasswordText;
    private EditText newPasswordText2;

    private Button applyNewPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPasswordText = findViewById(R.id.changePasswordOldPasswordText);
        newPasswordText = findViewById(R.id.changePasswordNewPasswordText);
        newPasswordText2 = findViewById(R.id.changePasswordNewPasswordText2);
        applyNewPasswordButton = findViewById(R.id.applyNewPasswordButton);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        applyNewPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkOldPassword()){
                    return;
                }
                if(!Check.comparePassword(newPasswordText.getText().toString(), newPasswordText2.getText().toString())){
                    Toast.makeText(ChangePasswordActivity.this, "Новые пароли не совпадают", Toast.LENGTH_LONG);
                    return;
                }
                if(!Check.checkPassword(newPasswordText.getText().toString())){
                    Toast.makeText(ChangePasswordActivity.this, "Пароль слишком простой", Toast.LENGTH_LONG);
                    return;
                }
                user.updatePassword(newPasswordText.getText().toString()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChangePasswordActivity.this, "Не удалось сменить пароль. Проверьте соединение и повторите попытку", Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }

    private boolean checkOldPassword(){
        //хз почему так, по- ругался
        final boolean[] result = {true};
        mAuth.signInWithEmailAndPassword(user.getEmail(), oldPasswordText.getText().toString()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChangePasswordActivity.this, "Старый пароль введен неверно!", Toast.LENGTH_LONG);
                result[0] = false;

            }

        });
        return result[0];

    }
}


