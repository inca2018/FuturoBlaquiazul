package org.futuroblanquiazul.futuroblaquiazul.Activities.Informacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.BarrioIntimoJugadoresActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoBarrioIntimoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.PerfilPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBusquedaPersona;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPersona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Info;

import java.util.ArrayList;
import java.util.List;

public class InfoBuscadorActivity extends AppCompatActivity {

    LinearLayout linea1,linea2,linea3;
    RecyclerView recyclerView1,recyclerView2,recyclerView3;
    AdapterBusquedaPersona adapterBusquedaPersona1,adapterBusquedaPersona2,adapterBusquedaPersona3;
    List<Persona> lista_persona_captacion,lista_persona_metodologia,lista_persona_estadistico;
    LinearLayoutManager linearLayoutManager1,linearLayoutManager2,linearLayoutManager3;
    Button info_buscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_buscador);
        linea1=findViewById(R.id.area_captacion);
        linea2=findViewById(R.id.area_metodologia);
        linea3=findViewById(R.id.area_estadistico);
        recyclerView1=findViewById(R.id.lista_captacion);
        recyclerView2=findViewById(R.id.lista_metodologia);
        recyclerView3=findViewById(R.id.lista_estadistico);

        info_buscar=findViewById(R.id.info_buscar);

        lista_persona_captacion=new ArrayList<>();
        lista_persona_metodologia=new ArrayList<>();
        lista_persona_estadistico=new ArrayList<>();

        linearLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapterBusquedaPersona1 = new AdapterBusquedaPersona(this, lista_persona_captacion, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        recyclerView1.setAdapter(adapterBusquedaPersona1);
        recyclerView1.setLayoutManager(linearLayoutManager1);

        linearLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapterBusquedaPersona2 = new AdapterBusquedaPersona(this, lista_persona_metodologia, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        recyclerView2.setAdapter(adapterBusquedaPersona2);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        linearLayoutManager3 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapterBusquedaPersona3 = new AdapterBusquedaPersona(this, lista_persona_estadistico, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        recyclerView3.setAdapter(adapterBusquedaPersona3);
        recyclerView3.setLayoutManager(linearLayoutManager3);
        Verificar_ventanas();

        info_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InfoBuscadorActivity.this,PerfilPersonaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                InfoBuscadorActivity.this.startActivity(intent);
            }
        });
    }

    private void Verificar_ventanas() {
        if(Info.GESTOR.getTipo_busqueda()==1){
            linea1.setVisibility(View.VISIBLE);
            linea2.setVisibility(View.GONE);
            linea3.setVisibility(View.GONE);
        }else if(Info.GESTOR.getTipo_busqueda()==2){
            linea1.setVisibility(View.GONE);
            linea2.setVisibility(View.VISIBLE);
            linea3.setVisibility(View.GONE);
        }else if(Info.GESTOR.getTipo_busqueda()==3){
            linea1.setVisibility(View.GONE);
            linea2.setVisibility(View.GONE);
            linea3.setVisibility(View.VISIBLE);

        }
    }

}
