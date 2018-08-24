package com.example.host.databaseproject;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.host.databaseproject.OurClasses.Check;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPasswordText.getText().toString());
                user.reauthenticate(credential).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChangePasswordActivity.this, "Старый пароль введен неверно", Toast.LENGTH_LONG);
                        //startActivity(new Intent(ChangePasswordActivity.this, SettingsActivity2.class));

                        return;
                    }
                });

                if(!Check.comparePassword(newPasswordText.getText().toString(), newPasswordText2.getText().toString())){
                    //startActivity(new Intent(ChangePasswordActivity.this, AddAvatarActivity.class));
                    Toast.makeText(ChangePasswordActivity.this, "Новые пароли не совпадают", Toast.LENGTH_LONG);
                    return;

                }

                if(!Check.checkPassword(newPasswordText.getText().toString())) {
                    //startActivity(new Intent(ChangePasswordActivity.this, CatalogueActivity_V2.class));

                    Toast.makeText(ChangePasswordActivity.this, "Пароль слишком простой", Toast.LENGTH_LONG);
                    return;
                }


//                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            if(!Check.comparePassword(newPasswordText.getText().toString(), newPasswordText2.getText().toString())){
//                                Toast.makeText(ChangePasswordActivity.this, "Новые пароли не совпадают", Toast.LENGTH_LONG);
//
//                            }
//
//                            if(!Check.checkPassword(newPasswordText.getText().toString())) {
//                                Toast.makeText(ChangePasswordActivity.this, "Пароль слишком простой", Toast.LENGTH_LONG);
//                                return;
//                            }
//
//
//                        }
//                        else {
//                            Toast.makeText(ChangePasswordActivity.this, "Старый пароль введен неверно", Toast.LENGTH_LONG);
//
//                        }
//                    }
//                });


                user.updatePassword(newPasswordText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ChangePasswordActivity.this, "Пароль был изменен успешно!", Toast.LENGTH_LONG);
                            startActivity(new Intent(ChangePasswordActivity.this, SettingsActivity2.class));

                        }
                        else{
                            Toast.makeText(ChangePasswordActivity.this, "Не удалось сменить пароль. Проверьте соединение и повторите попытку", Toast.LENGTH_LONG);

                        }
                    }
                });

            }
        });




    }



}


