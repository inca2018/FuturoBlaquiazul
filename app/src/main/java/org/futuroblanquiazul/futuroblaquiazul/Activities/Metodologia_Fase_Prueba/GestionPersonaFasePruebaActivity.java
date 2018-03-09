package org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia_Fase_Prueba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterEtapaPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EtapaPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.TipoPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBarrios;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarEtapaFasePruebas;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GestionPersonaFasePruebaActivity extends AppCompatActivity {

    TextView nombres_persona;
    TextView fecha_persona;
    Button nueva_prueba;
    Context context;

    RecyclerView recyclerView;
    private LinearLayoutManager linearLayout;
    private AdapterEtapaPruebas adapter;
    private List<EtapaPruebas> lista_pruebas;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_persona_fase_prueba);
        nombres_persona=findViewById(R.id.gestion_fase_nombres);
        fecha_persona=findViewById(R.id.gestion_fase_fecha);
        nueva_prueba=findViewById(R.id.gestion_fase_nueva_prueba);
        recyclerView=findViewById(R.id.lista_pruebas_realizadas);
        context=this;
        lista_pruebas=new ArrayList<>();
        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);


        adapter = new AdapterEtapaPruebas(this, lista_pruebas, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);




        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
            nombres_persona.setText(Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getApellidos_Persona());
            fecha_persona.setText(Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getFecha_Ultima_modificacion());

            listar_etapa_pruebas(Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getId(),context);
        }else{
            nombres_persona.setText(" No Disponible");
            fecha_persona.setText(" No Disponible");
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

    private void listar_etapa_pruebas(int id_per,final Context context) {

        String id_persona=String.valueOf(id_per);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Metodologia Fase Prueba:");
        progressDialog.setMessage("Listando Pruebas Realizadas...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray xx=jsonResponse.getJSONArray("etapas_pruebas");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            EtapaPruebas temp=new EtapaPruebas();
                            temp.setNum((i+1));
                            temp.setId(objeto.getInt("ID"));

                            Usuario u=new Usuario();
                            u.setId(objeto.getInt("CODIGO_USUARIO"));
                            u.setUsuario(objeto.getString("USUARIO"));

                            temp.setUser(u);

                            Persona p=new Persona();
                            p.setId(objeto.getInt("ID_PERSONA"));

                            temp.setPersona(p);

                            TipoPruebas t=new TipoPruebas();
                            t.setId(objeto.getInt("ID_PRUEBA"));
                            t.setDescripcion(objeto.getString("DESCRIPCION"));

                            temp.setTipoPruebas(t);

                            temp.setId_evaluacion(objeto.getInt("ID_EVALUACION"));
                            temp.setFecha_Registro(objeto.getString("FECHA_REGISTRO"));


                            lista_pruebas.add(temp);


                        }

                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO FASE PRUEBAS");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Listado de Fase Pruebas Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar FASE PRUEBAS :"+e);
                }
            }
        };

        RecuperarEtapaFasePruebas xx = new RecuperarEtapaFasePruebas(id_persona,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    public void onBackPressed() {

        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
            Intent intent = new Intent(GestionPersonaFasePruebaActivity.this,ListaPersonasFasePruebaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            GestionPersonaFasePruebaActivity.this.startActivity(intent);
        }


    }
}
