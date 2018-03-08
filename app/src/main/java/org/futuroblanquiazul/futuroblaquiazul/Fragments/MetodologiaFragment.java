package org.futuroblanquiazul.futuroblaquiazul.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.GestionEvaluacionActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPlantelActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia_Fase_Prueba.ListaPersonasFasePruebaActivity;
import org.futuroblanquiazul.futuroblaquiazul.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MetodologiaFragment extends Fragment {

     Button accion1,accion2;
    public Context mContext;
    public MetodologiaFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_metodologia, container, false);
            accion1=v.findViewById(R.id.accion_1_metodologia);
            accion2=v.findViewById(R.id.accion_2_metodologia);

            accion1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(mContext,ListaPlantelActivity.class);
                    startActivity(intent);
                }
            });

            accion2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, ListaPersonasFasePruebaActivity.class);
                    startActivity(intent);

                }
            });



        return v;
    }

}
