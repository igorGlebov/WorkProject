package com.example.host.databaseproject;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogueFragment extends Fragment {


    public CatalogueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogue, container, false);

//        final Button mainButton = (Button) rootView.findViewById(R.id.main_button);
//
//        mainButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mainButton.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
//            }
//        });
        ListView listView = rootView.findViewById(R.id.list);
        String[] strings = new String[3];
        strings[0] = "Товар 1";
        strings[1] = "Товар 2";
        strings[2] = "Товар 3";
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_multiple_choice, strings);

        listView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return rootView;
    }


}
