package org.futuroblanquiazul.futuroblaquiazul.Activities.MetodologiaTiempo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.futuroblanquiazul.futuroblaquiazul.R;

public class ListaPruebasTiempoActivity extends AppCompatActivity {


    RecyclerView lista_tiempos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pruebas_tiempo);

        lista_tiempos=findViewById(R.id.lista_tiempos);



    }
}
