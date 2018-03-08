package org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia_Fase_Prueba;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.R;

public class GestionPersonaFasePruebaActivity extends AppCompatActivity {

    TextView nombres_persona;
    TextView fecha_persona;
    Button nueva_prueba;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_persona_fase_prueba);
        nombres_persona=findViewById(R.id.gestion_fase_nombres);
        fecha_persona=findViewById(R.id.gestion_fase_fecha);
        nueva_prueba=findViewById(R.id.gestion_fase_nueva_prueba);
        context=this;

        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
            nombres_persona.setText(Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getApellidos_Persona());
            fecha_persona.setText(Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getFecha_Ultima_modificacion());
        }

        nueva_prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ListaPruebasDisponiblesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            }
        });

    }
}
