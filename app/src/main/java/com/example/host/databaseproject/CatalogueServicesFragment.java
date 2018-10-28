package com.example.host.databaseproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;


public class CatalogueServicesFragment extends Fragment {

    private GestureDetectorCompat lSwipeDetector;


    private RelativeLayout layout;

    private static final int SWIPE_MIN_DISTANCE = 130;
    private static final int SWIPE_MAX_DISTANCE = 300;
    private static final int SWIPE_MIN_VELOCITY = 200;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_catalogue_services, container, false);

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

        layout = (RelativeLayout) view.findViewById(R.id.serviceLayout);

        lSwipeDetector = new GestureDetectorCompat(getContext(), new CatalogueServicesFragment.MyGestureListener());

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return lSwipeDetector.onTouchEvent(event);
            }
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);




    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_DISTANCE)
                return false;
            if (e2.getX() - e1.getX() < -SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY) {
                //Подпихнуть переход вот сюда
                //Toast.makeText(getActivity(), "Справа налево2.", Toast.LENGTH_LONG).show();
//                CatalogueGoodsFragment fragment = new CatalogueGoodsFragment();
//                android.support.v4.app.FragmentTransaction fragmentTransaction =
//                        getActivity().getSupportFragmentManager().beginTransaction(); // начало транзакции фрагмента
//                fragmentTransaction.replace(R.id.fragment_container, fragment); // замена фрагмента, содержащегося в контейнере
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); // анимация перехода
//                //fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_in_left);
//                fragmentTransaction.addToBackStack(null); // помещение транзакции в стек возврата
//                fragmentTransaction.commit(); // закрепляет изменения

            }
            else if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY){
               // Toast.makeText(getActivity(), "Слева направо2.", Toast.LENGTH_LONG).show();
                CatalogueGoodsFragment fragment = new CatalogueGoodsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager().beginTransaction(); // начало транзакции фрагмента
                fragmentTransaction.replace(R.id.fragment_container, fragment); // замена фрагмента, содержащегося в контейнере
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); // анимация перехода
                //fragmentTransaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_in_left);
                fragmentTransaction.addToBackStack(null); // помещение транзакции в стек возврата
                fragmentTransaction.commit(); // закрепляет изменения


            }
            return false;
        }
    }

}
