package org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;

public class MantenimientoPersonaActivity extends AppCompatActivity {


    Button p_boton_nuevo_persona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_persona);
        p_boton_nuevo_persona=findViewById(R.id.p_boton_nuevo_persona);


        p_boton_nuevo_persona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MantenimientoPersonaActivity.this,EdicionPersonaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MantenimientoPersonaActivity.this.startActivity(intent);
            }
        });

    }

    public void onBackPressed() {

        Intent intent=new Intent(MantenimientoPersonaActivity.this,PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("o","o5");
        MantenimientoPersonaActivity.this.startActivity(intent);

    }
}
