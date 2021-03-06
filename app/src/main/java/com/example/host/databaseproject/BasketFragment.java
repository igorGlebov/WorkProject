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
public class BasketFragment extends Fragment {

    public static final String EXTRA_KEY = "key";

    public BasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_basket, container, false);

        ListView listView = rootView.findViewById(R.id.listViewBasket);
        Bundle bundle = this.getArguments();
        String[] strings = null;
        if(bundle != null){
            strings = bundle.getStringArray(EXTRA_KEY);
        }
        if(strings != null){
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(this.getContext(),
                    android.R.layout.simple_list_item_multiple_choice, strings);

            listView.setAdapter(adapter);
        }

        return rootView;
    }


}
