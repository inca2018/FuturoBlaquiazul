package org.futuroblanquiazul.futuroblaquiazul.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.futuroblanquiazul.futuroblaquiazul.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformacionCaptacionFragment extends Fragment {


    public InformacionCaptacionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_informacion_captacion, container, false);



        return v;
    }

}
