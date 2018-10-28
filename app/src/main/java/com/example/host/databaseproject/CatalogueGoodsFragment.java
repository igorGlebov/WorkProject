package com.example.host.databaseproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogueGoodsFragment extends Fragment {

    private OnFragmentInteractionListener mListener; // для обращения к активити

    private GestureDetectorCompat lSwipeDetector;

    ListView listView;
    String[] strings;
    static int[] checked;
    ImageButton ritualButton;
    ImageButton nichesButton;
    ImageButton flowersButton;
    private RelativeLayout layout;

    private static final int SWIPE_MIN_DISTANCE = 130;
    private static final int SWIPE_MAX_DISTANCE = 300;
    private static final int SWIPE_MIN_VELOCITY = 200;



    public CatalogueGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalogue_goods, container, false);

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
        ritualButton = (ImageButton) view.findViewById(R.id.ritualButton);
        flowersButton = (ImageButton) view.findViewById(R.id.flowersButton);
        nichesButton = (ImageButton) view.findViewById(R.id.nichesButton);
        layout = (RelativeLayout) view.findViewById(R.id.frameLayout);

        lSwipeDetector = new GestureDetectorCompat(getContext(), new MyGestureListener());

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return lSwipeDetector.onTouchEvent(event);
            }
        });


        return view;
    }



    interface OnFragmentInteractionListener {
        void onFragmentInteraction(String[] array);
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_goods: // при нажатии на товары
//                    GoodsFragment fragment = new GoodsFragment();
//                    android.support.v4.app.FragmentTransaction fragmentTransaction =
//                            getActivity().getSupportFragmentManager().beginTransaction(); // начало транзакции фрагмента
//                    fragmentTransaction.replace(R.id.fragment_container, fragment); // замена фрагмента, содержащегося в контейнере
//                    //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); // анимация перехода
//                    fragmentTransaction.addToBackStack(null); // помещение транзакции в стек возврата
//                    fragmentTransaction.commit(); // закрепляет изменения
//                    return true;
//                case R.id.navigation_amenities: // при нажатии на услуги
//                    return true;
//            }
//            return false;
//        }
//    };

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

        for (int i = 0; i < count; i++) {
            if (checkedItemPosition.get(i)) {
                checkedCount++;
            }
        }
        checked = new int[checkedCount];
        String[] strs = new String[checkedCount];
        int y = 0;
        for (int i = 0; i < count; i++) {
            if (checkedItemPosition.get(i)) {
                strs[y] = strings[i];
                checked[y] = i;
                y++;
            }
        }
        // Посылаем данные Activity
        mListener.onFragmentInteraction(strs);
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
                Toast.makeText(getActivity(), "Справа налево.", Toast.LENGTH_LONG).show();

            }
            else if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY){
                Toast.makeText(getActivity(), "Слева направо.", Toast.LENGTH_LONG).show();

            }
            return false;
        }
    }

}

