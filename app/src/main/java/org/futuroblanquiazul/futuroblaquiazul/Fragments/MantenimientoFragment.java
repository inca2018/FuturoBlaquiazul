package org.futuroblanquiazul.futuroblaquiazul.Fragments;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoPosicionesActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoUsuarioActivity;
import org.futuroblanquiazul.futuroblaquiazul.R;

public class MantenimientoFragment extends Fragment {

    Button accion1,accion2,accion3;
    Context context;
    public MantenimientoFragment() {
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View v=inflater.inflate(R.layout.fragment_mantenimiento, container, false);
          accion1=v.findViewById(R.id.accion_1_mantenimiento);
          accion2=v.findViewById(R.id.accion_2_mantenimiento);
          accion3=v.findViewById(R.id.accion_3_mantenimiento);

          accion1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent= new Intent(context,MantenimientoUsuarioActivity.class);
                  startActivity(intent);

              }
          });

          accion2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  Intent intent= new Intent(context, MantenimientoPosicionesActivity.class);
                  startActivity(intent);
              }
          });

          accion3.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

              }
          });
        return  v;
    }

}
