package com.example.host.databaseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddAvatarActivity extends AppCompatActivity {

    private Button addAvatarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_avatar);

        addAvatarButton = findViewById(R.id.addAvatarButton);

        addAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
