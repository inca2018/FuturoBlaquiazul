package org.futuroblanquiazul.futuroblaquiazul.Fragments;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoBarrioIntimoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoCategoriasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoEquipoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoGrupoPruebaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoOponentesActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoPosicionesActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoUsuarioActivity;
import org.futuroblanquiazul.futuroblaquiazul.R;

public class MantenimientoFragment extends Fragment {

    Button accion1,accion2,accion3,accion4,accion5,accion6,accion7,accion8;
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
          accion4=v.findViewById(R.id.accion_4_mantenimiento);
          accion5=v.findViewById(R.id.accion_5_mantenimiento);
          accion6=v.findViewById(R.id.accion_6_mantenimiento);
          accion7=v.findViewById(R.id.accion_7_mantenimiento);
        accion8=v.findViewById(R.id.accion_8_mantenimiento);

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
                  Intent intent= new Intent(context, MantenimientoPersonaActivity.class);
                  startActivity(intent);
              }
          });

        accion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MantenimientoOponentesActivity.class);
                startActivity(intent);
            }
        });
        accion5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MantenimientoBarrioIntimoActivity.class);
                startActivity(intent);
            }
        });

        accion6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MantenimientoCategoriasActivity.class);
                startActivity(intent);
            }
        });

        accion7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MantenimientoEquipoActivity.class);
                startActivity(intent);
            }
        });
        accion8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MantenimientoGrupoPruebaActivity.class);
                startActivity(intent);
            }
        });

        return  v;
    }

}
