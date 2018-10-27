package com.example.host.databaseproject;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogueFragment extends Fragment {

    private OnFragmentInteractionListener mListener; // для обращения к активити
    ListView listView;
    String[] strings;
    static int[] checked;

    public CatalogueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogue, container, false);

        //listView = rootView.findViewById(R.id.list);
        strings = new String[15];
        for(int i = 0; i < 15;i++){
            strings[i] = "Товар " + (i + 1);
        }


//        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this.getContext(),
//                android.R.layout.simple_list_item_1, strings);
//
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                updateDetail();
//            }
//        });

        return rootView;
    }

    interface OnFragmentInteractionListener {
        void onFragmentInteraction(String[] array);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }


    public void updateDetail() {
        // генерируем некоторые данные
        int count = listView.getCount();
        int checkedCount = 0;
        SparseBooleanArray checkedItemPosition = listView.getCheckedItemPositions();

        for(int i = 0; i < count;i++){
            if(checkedItemPosition.get(i)){
                checkedCount++;
            }
        }
        checked = new int[checkedCount];
        String[] strs = new String[checkedCount];
        int y = 0;
        for(int i = 0; i < count;i++){
            if(checkedItemPosition.get(i)){
                strs[y] = strings[i];
                checked[y] = i;
                y++;
            }
        }
        // Посылаем данные Activity
        mListener.onFragmentInteraction(strs);
    }
}
