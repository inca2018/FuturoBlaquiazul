package org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia_Fase_Prueba;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaDiagnosticoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaFisicoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaNutricionalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaPsicologicaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaTacticaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaTecnicaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaTecnica;
import org.futuroblanquiazul.futuroblaquiazul.Entity.TipoPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.R;

public class ListaPruebasDisponiblesActivity extends AppCompatActivity {


    LinearLayout bt1,bt2,bt3,bt4,bt5,bt6;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pruebas_disponibles);

        bt1=findViewById(R.id.lista_prueba_diagnostico);
        bt2=findViewById(R.id.lista_prueba_fisico);
        bt3=findViewById(R.id.lista_prueba_tecnica);
        bt4=findViewById(R.id.lista_prueba_tactica);
        bt5=findViewById(R.id.lista_prueba_psicologica);
        bt6=findViewById(R.id.lista_prueba_nutricional);
        context=this;


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
                    TipoPruebas temp=new TipoPruebas();
                    temp.setId(1);
                    Usuario.SESION_ACTUAL.setTipoPruebas(temp);

                    Intent intent = new Intent(ListaPruebasDisponiblesActivity.this,PruebaDiagnosticoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    ListaPruebasDisponiblesActivity.this.startActivity(intent);
                }

            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TipoPruebas temp=new TipoPruebas();
                temp.setId(2);
                Usuario.SESION_ACTUAL.setTipoPruebas(temp);

                Intent intent = new Intent(ListaPruebasDisponiblesActivity.this,PruebaFisicoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ListaPruebasDisponiblesActivity.this.startActivity(intent);

            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TipoPruebas temp=new TipoPruebas();
                temp.setId(3);
                Usuario.SESION_ACTUAL.setTipoPruebas(temp);

                Intent intent = new Intent(ListaPruebasDisponiblesActivity.this,PruebaTecnicaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ListaPruebasDisponiblesActivity.this.startActivity(intent);

            }
        });


        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TipoPruebas temp=new TipoPruebas();
                temp.setId(4);
                Usuario.SESION_ACTUAL.setTipoPruebas(temp);

                Intent intent = new Intent(ListaPruebasDisponiblesActivity.this,PruebaTacticaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ListaPruebasDisponiblesActivity.this.startActivity(intent);

            }
        });


        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TipoPruebas temp=new TipoPruebas();
                temp.setId(5);
                Usuario.SESION_ACTUAL.setTipoPruebas(temp);

                Intent intent = new Intent(ListaPruebasDisponiblesActivity.this,PruebaPsicologicaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ListaPruebasDisponiblesActivity.this.startActivity(intent);

            }
        });


        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TipoPruebas temp=new TipoPruebas();
                temp.setId(6);
                Usuario.SESION_ACTUAL.setTipoPruebas(temp);

                Intent intent = new Intent(ListaPruebasDisponiblesActivity.this,PruebaNutricionalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ListaPruebasDisponiblesActivity.this.startActivity(intent);
            }
        });
    }

    public void onBackPressed() {

        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
            Intent intent = new Intent(ListaPruebasDisponiblesActivity.this,GestionPersonaFasePruebaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ListaPruebasDisponiblesActivity.this.startActivity(intent);
        }


    }
}
