package com.example.host.databaseproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Coffins extends Fragment {

    private final String[] mCatNames = new String[] { "Рыжик", "Барсик", "Мурзик",
            "Мурка", "Васька", "Томасина", "Кристина", "Пушок", "Дымка",
            "Кузя", "Китти", "Масяня", "Симба" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_coffins, container, false);

        //ScrollView scrollView = rootView.findViewById(R.id.scrollView); // сам скролл

        ListView listView = rootView.findViewById(R.id.listViewCoffins);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this.getContext(),
                R.layout.list_item,
                R.id.text_view_cat_name, mCatNames);

        listView.setAdapter(adapter);

        //scrollView.addView(label);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
