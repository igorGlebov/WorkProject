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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class RitualAccessoriesFragment extends Fragment {

    ImageButton flowersButton;
    ImageButton buttonCoffins;

    private GestureDetectorCompat lSwipeDetector;

    private RelativeLayout layout;

    private static final int SWIPE_MIN_DISTANCE = 130;
    private static final int SWIPE_MAX_DISTANCE = 300;
    private static final int SWIPE_MIN_VELOCITY = 200;

    public RitualAccessoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ritual_accessories, container, false);

        flowersButton = view.findViewById(R.id.ritualAccessoriesFlowersButton);
        buttonCoffins = view.findViewById(R.id.coffinsButton);

        flowersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowersProductsFragment fragment = new FlowersProductsFragment();
                replaceFragment(fragment);
            }
        });

        buttonCoffins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoffinsFragment fragment = new CoffinsFragment();
                replaceFragment(fragment);
            }
        });


        layout = (RelativeLayout) view.findViewById(R.id.ritualAccessoriesLayout);

        lSwipeDetector = new GestureDetectorCompat(getContext(), new RitualAccessoriesFragment.MyGestureListener());

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return lSwipeDetector.onTouchEvent(event);
            }
        });

        // Inflate the layout for this fragment
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


            }
//            else if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY){
//                // Toast.makeText(getActivity(), "Слева направо.", Toast.LENGTH_LONG).show();
//                FlowersProductsFragment fragment = new FlowersProductsFragment();
//                replaceFragment(fragment);
//            }
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
