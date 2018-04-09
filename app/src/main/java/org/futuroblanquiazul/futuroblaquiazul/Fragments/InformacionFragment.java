package org.futuroblanquiazul.futuroblaquiazul.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Informacion.InfoBuscadorActivity;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Info;


public class InformacionFragment extends Fragment {


   Button accion1,accion2,accion3;
   Context context;
    public InformacionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_informacion, container, false);
        accion1=v.findViewById(R.id.accion1_captacion);
        accion2=v.findViewById(R.id.accion2_meto);
        accion3=v.findViewById(R.id.accion_3_estadistico);

        accion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info.GESTOR.setTipo_busqueda(1);
                Intent intent= new Intent(context, InfoBuscadorActivity.class);
                startActivity(intent);
            }
        });

        accion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info.GESTOR.setTipo_busqueda(2);
                Intent intent= new Intent(context, InfoBuscadorActivity.class);
                startActivity(intent);
            }
        });

        accion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info.GESTOR.setTipo_busqueda(3);
                Intent intent= new Intent(context, InfoBuscadorActivity.class);
                startActivity(intent);
            }
        });


        return v;
    }




}
