package br.com.stanzione.nuvemshoptest.doglist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.stanzione.nuvemshoptest.R;

public class DogListFragment extends Fragment {


    public DogListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dog_list, container, false);
    }

}
