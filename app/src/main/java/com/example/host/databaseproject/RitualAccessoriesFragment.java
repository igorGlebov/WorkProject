package com.example.host.databaseproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class RitualAccessoriesFragment extends Fragment {

    ImageButton flowersButton;
    public RitualAccessoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ritual_accessories, container, false);

        flowersButton = view.findViewById(R.id.ritualAccessoriesFlowersButton);

        flowersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowersProductsFragment fragment = new FlowersProductsFragment();
                replaceFragment(fragment);
            }
        });

        // Inflate the layout for this fragment
        return view;
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
