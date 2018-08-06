package com.example.host.databaseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AccountActivity extends AppCompatActivity {

    ListView listView;

    private DatabaseReference myRef; // ссылка на данные

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        listView = findViewById(R.id.ListView);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String string = dataSnapshot.getValue().toString();
                fillView(createGoodsList(string));
            }
                                                                                                                                                                                                                                                        RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AccountActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        }) ;
    }

    private String[] createGoodsList (String str){
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
