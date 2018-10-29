package com.example.host.databaseproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class FlowersProductsFragment extends Fragment {

    private GestureDetectorCompat lSwipeDetector;

    private RelativeLayout layout;

    private static final int SWIPE_MIN_DISTANCE = 130;
    private static final int SWIPE_MAX_DISTANCE = 300;
    private static final int SWIPE_MIN_VELOCITY = 200;


    public FlowersProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flowers_products, container, false);

        //listView = rootView.findViewById(R.id.list);

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

//        BottomNavigationView navigation = rootView.findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        layout = (RelativeLayout) view.findViewById(R.id.flowersProductsLayout);

        lSwipeDetector = new GestureDetectorCompat(getContext(), new FlowersProductsFragment.MyGestureListener());

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return lSwipeDetector.onTouchEvent(event);
            }
        });


        return view;

    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
//            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_DISTANCE)
//                return false;
            if(e2.getY() - e1.getY() < -SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY){
                CatalogueGoodsFragment fragment = new CatalogueGoodsFragment();
                replaceFragment(fragment);
            }

            if (e2.getX() - e1.getX() < -SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY) {
                //Подпихнуть переход вот сюда
                //Toast.makeText(getActivity(), "Справа налево.", Toast.LENGTH_LONG).show();
                FlowersServiceFragment fragment = new FlowersServiceFragment();
                replaceFragment(fragment);

            }
            else if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY){
                // Toast.makeText(getActivity(), "Слева направо.", Toast.LENGTH_LONG).show();

            }
            return false;
        }
    }

    public void replaceFragment(android.support.v4.app.Fragment fragment){
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction(); // начало транзакции фрагмента
        fragmentTransaction.replace(R.id.fragment_container, fragment); // замена фрагмента, содержащегося в контейнере
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); // анимация перехода
        fragmentTransaction.addToBackStack(null); // помещение транзакции в стек возврата
        fragmentTransaction.commit(); // закрепляет изменения
    }

}
