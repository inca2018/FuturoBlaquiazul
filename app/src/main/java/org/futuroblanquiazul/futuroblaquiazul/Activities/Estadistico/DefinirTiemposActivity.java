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
                              Estadistico_Gestion.TEMP.setContador(0);
                              Estadistico_Gestion.TEMP.setTotal_primer_tiempo(num);
                              Estadistico_Gestion.TEMP.setTotal_segundo_tiempo(num*2);

                              Estadistico_Gestion.TEMP.setMinutos_x_tiempo(num);
                              Estadistico_Gestion.TEMP.setBloque_tiempo(num);
                              Estadistico_Gestion.TEMP.setTiempo_Total(num);
                              Estadistico_Gestion.TEMP.setTiempo_Total2(num);

                              int Aviso1=Estadistico_Gestion.TEMP.getTotal_primer_tiempo()-3;
                              Estadistico_Gestion.TEMP.setPrimer_aviso(Aviso1);
                              int Aviso2=Estadistico_Gestion.TEMP.getTotal_primer_tiempo()-1;
                              Estadistico_Gestion.TEMP.setSegundo_aviso(Aviso2);


                              int Aviso3=Estadistico_Gestion.TEMP.getTotal_segundo_tiempo()-3;
                              Estadistico_Gestion.TEMP.setTercer_aviso(Aviso3);
                              int Aviso4=Estadistico_Gestion.TEMP.getTotal_segundo_tiempo()-1;
                              Estadistico_Gestion.TEMP.setCuarto_aviso(Aviso4);



                              Estadistico_Gestion.TEMP.setTiempo_Adicional(0);
                              Estadistico_Gestion.TEMP.setTiempo_actual(0);
                              Estadistico_Gestion.TEMP.setTiempo_Jugado(0);

                              Estadistico_Gestion.TEMP.setZPG1(0);
                              Estadistico_Gestion.TEMP.setZF1(0);
                              Estadistico_Gestion.TEMP.setZR1(0);
                              Estadistico_Gestion.TEMP.setZPG12(0);

                              Estadistico_Gestion.TEMP.setZPG2(0);
                              Estadistico_Gestion.TEMP.setZF2(0);
                              Estadistico_Gestion.TEMP.setZR2(0);
                              Estadistico_Gestion.TEMP.setZPG22(0);


                              Estadistico_Gestion.TEMP.setOF_IZQ_DR(0);
                              Estadistico_Gestion.TEMP.setOF_IZQ_PG(0);
                              Estadistico_Gestion.TEMP.setOF_CEN_OG(0);
                              Estadistico_Gestion.TEMP.setOF_CEN_R(0);
                              Estadistico_Gestion.TEMP.setOF_ZF_OG(0);
                              Estadistico_Gestion.TEMP.setOF_ZF_R(0);
                              Estadistico_Gestion.TEMP.setOF_DER_DR(0);
                              Estadistico_Gestion.TEMP.setOF_DER_PG(0);


                              Estadistico_Gestion.TEMP.setOF_IZQ_DR2(0);
                              Estadistico_Gestion.TEMP.setOF_IZQ_PG2(0);
                              Estadistico_Gestion.TEMP.setOF_CEN_OG2(0);
                              Estadistico_Gestion.TEMP.setOF_CEN_R2(0);
                              Estadistico_Gestion.TEMP.setOF_ZF_OG2(0);
                              Estadistico_Gestion.TEMP.setOF_ZF_R2(0);
                              Estadistico_Gestion.TEMP.setOF_DER_DR2(0);
                              Estadistico_Gestion.TEMP.setOF_DER_PG2(0);




                              Estadistico_Gestion.TEMP.setEstado_partido(1);

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
