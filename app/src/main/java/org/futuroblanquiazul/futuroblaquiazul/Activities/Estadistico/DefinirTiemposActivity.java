package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.futuroblanquiazul.futuroblaquiazul.Entity.FechaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Estadistico_Gestion;

public class DefinirTiemposActivity extends AppCompatActivity {


    Button iniciar_boton;
    EditText tiempo;
    RadioGroup grupo_tiempos;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_tiempos);
        iniciar_boton=findViewById(R.id.iniciar_boton);
        tiempo=findViewById(R.id.tiempo_dado);
        grupo_tiempos=findViewById(R.id.grupo_tiempos);
        context=this;

         iniciar_boton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                  if(tiempo.getText().toString().length()!=0){
                      int num=Integer.parseInt(tiempo.getText().toString());
                      if(num>20){
                          Toast.makeText(context, "El Tiempo Minimo por Fase es de 20 Minutos", Toast.LENGTH_SHORT).show();
                      }else{
                          if(num>45){
                              Toast.makeText(DefinirTiemposActivity.this, "El tiempo Maximo por Fase es de 45 Minutos", Toast.LENGTH_SHORT).show();
                          }else{
                              Estadistico_Gestion.TEMP.setMinutos_x_tiempo(num);
                              int Aviso1=num-5;
                              Estadistico_Gestion.TEMP.setPrimer_aviso(Aviso1);
                              int Aviso2=num-1;
                              Estadistico_Gestion.TEMP.setSegundo_aviso(Aviso2);
                              Estadistico_Gestion.TEMP.setTiempo_Adicional1(0);
                              Estadistico_Gestion.TEMP.setTiempo_Adicional2(0);

                              Intent intent = new Intent(context, GestionFechaEstadisticoActivity.class);
                              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                              context.startActivity(intent);
                          }
                      }

                  }else{
                      Toast.makeText(context, "Ingrese Tiempo", Toast.LENGTH_SHORT).show();
                  }



             }
         });

        grupo_tiempos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.solo_uno_tiempo){
                    Estadistico_Gestion.TEMP.setCantidad_tiempos(1);
                }else if(checkedId==R.id.dos_tiempos){
                    Estadistico_Gestion.TEMP.setCantidad_tiempos(2);
                }
            }
        });

    }
}
