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
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_tiempos);
        iniciar_boton=findViewById(R.id.iniciar_boton);
        tiempo=findViewById(R.id.tiempo_dado);

        context=this;

         iniciar_boton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                  if(tiempo.getText().toString().length()!=0){
                      int num=Integer.parseInt(tiempo.getText().toString());

                          if(num>45){
                              Toast.makeText(DefinirTiemposActivity.this, "El tiempo Maximo por Fase es de 45 Minutos", Toast.LENGTH_SHORT).show();
                          }else{

                              Estadistico_Gestion.TEMP.setMinutos_x_tiempo(num);
                              Estadistico_Gestion.TEMP.setTiempo_Total(num*2);

                              int Aviso1=Estadistico_Gestion.TEMP.getTiempo_Total()-2;
                              Estadistico_Gestion.TEMP.setPrimer_aviso(Aviso1);
                              int Aviso2=Estadistico_Gestion.TEMP.getTiempo_Total()-1;
                              Estadistico_Gestion.TEMP.setSegundo_aviso(Aviso2);

                              Estadistico_Gestion.TEMP.setTiempo_Adicional(0);
                              Estadistico_Gestion.TEMP.setTiempo_actual(0);

                              Intent intent = new Intent(context, GestionFechaEstadisticoActivity.class);
                              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                              context.startActivity(intent);
                          }


                  }else{
                      Toast.makeText(context, "Ingrese Tiempo", Toast.LENGTH_SHORT).show();
                  }



             }
         });

    }


    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(context,ListaFechasEstadisticosActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);


    }
}
