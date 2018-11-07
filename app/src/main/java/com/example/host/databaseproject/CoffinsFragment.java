package com.example.host.databaseproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.host.databaseproject.OurClasses.Product;
import com.example.host.databaseproject.dummy.DummyContent;
import com.example.host.databaseproject.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;


public class CoffinsFragment extends ListFragment {

    private static final Product[] products = new Product[10];
    // определяем массив типа String
    static {
        products[0] = new Product("400", "Товар");
        products[1] = new Product("400", "Товар");
        products[2] = new Product("400", "Товар");
        products[3] = new Product("400", "Товар");
        products[4] = new Product("400", "Товар");
        products[5] = new Product("400", "Товар");
        products[6] = new Product("400", "Товар");
        products[7] = new Product("400", "Товар");
        products[8] = new Product("400", "Товар");
        products[9] = new Product("400", "Товар");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MyListAdapter myListAdapter = new MyListAdapter(getActivity(),
                R.layout.list_item, products); // разметка одной строки в списке // список объектов
        setListAdapter(myListAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, // добавляет макет с фрагменту
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, null);
    }

    public class MyListAdapter extends ArrayAdapter<Product> {

        private Context mContext;

        public MyListAdapter(Context context, int textViewResourceId,
                             Product[] objects) {
            super(context, textViewResourceId, objects);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // return super.getView(position, convertView, parent);

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.list_item, parent,
                    false);
            TextView catNameTextView = row.findViewById(R.id.title_item);
            catNameTextView.setText(products[position].getName());

            TextView price = row.findViewById(R.id.price);
            price.setText(products[position].getPrice());
            // Присваиваем значок
            //iconImageView.setImageResource(R.drawable.ic_launcher_cat);

            return row;
        }
    }
}
