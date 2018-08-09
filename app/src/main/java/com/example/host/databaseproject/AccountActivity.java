package com.example.host.databaseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AccountActivity extends AppCompatActivity {

    ListView listView; // Список товаров
    Button logOutButton;
    FirebaseAuth mAuth;

    private DatabaseReference myRef; // ссылка на данные

    @Override
    protected void onCreate(Bundle savedInstanceState ) { // присоединение к базе данных
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        listView = findViewById(R.id.ListView);
        logOutButton = findViewById(R.id.testLogOutButton);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String string = dataSnapshot.getValue().toString();
                fillView(createGoodsList(string));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AccountActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });
    }

    private String[] createGoodsList (String str){ // Создание списка товаров
        str = str.substring(1, str.length() - 1);

        String[] databaseList;

        databaseList = str.split(",");

        return databaseList;
    }

    private void fillView (String[] list) {
        for(int i = 0; i < list.length; i++){
            list[i] = list[i].replaceAll("=", ", ");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, list);
        listView.setAdapter(adapter);
    }
}
