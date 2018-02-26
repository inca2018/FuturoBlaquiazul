package org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CpuUsageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaMasivosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaPersonaMasivoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBarrioPersona;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterMasivoPersona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonas;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasBarrio;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BarrioIntimoPersonaActivity extends AppCompatActivity {

    private RecyclerView recycler_barrio_personas;
    private LinearLayoutManager linearLayout;
    private AdapterBarrioPersona adapter;
    private List<Persona> lista_personas_barrio;
    Context context;
    ProgressDialog progressDialog;

    TextView campo1,campo2,campo3,campo4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrio_intimo_persona);
        context=this;
        campo1=findViewById(R.id.titulo_barrio_intimo);
        campo2=findViewById(R.id.ubicion_barrio_intimo);
        campo3=findViewById(R.id.fecha_barrio_intimo);
        campo4=findViewById(R.id.Descripción_barrio_intimo);

        recycler_barrio_personas=findViewById(R.id.recycler_barrio_personas2);

        lista_personas_barrio=new ArrayList<>();

        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        listar_personas_barrio_intimo(context);

        adapter = new AdapterBarrioPersona(this, lista_personas_barrio, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
                //Toast.makeText(context, "CLick id:"+lista_personas.get(position).getId(), Toast.LENGTH_SHORT).show();

            }
        });

        recycler_barrio_personas.setAdapter(adapter);
        recycler_barrio_personas.setLayoutManager(linearLayout);



        if(Usuario.SESION_ACTUAL.getBarrio_datos()!=null){
            campo1.setText(Usuario.SESION_ACTUAL.getBarrio_datos().getNombreEvento());
            campo2.setText(Usuario.SESION_ACTUAL.getBarrio_datos().getDescripcion_ubigeo());
            campo3.setText(Usuario.SESION_ACTUAL.getBarrio_datos().getFechaRealizacion());
            campo4.setText(Usuario.SESION_ACTUAL.getBarrio_datos().getDescripcion());
        }else{

            campo1.setText("NO DISPONIBLE");
            campo2.setText("NO DISPONIBLE");
            campo3.setText("NO DISPONIBLE");
            campo4.setText("NO DISPONIBLE");
        }



    }

    private void listar_personas_barrio_intimo(final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Postulantes Barrio Intimo:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();

        String id_barrio_intimo=String.valueOf(Usuario.SESION_ACTUAL.getId_barrio_intimo());
        System.out.println("Id_ masivo :"+id_barrio_intimo);
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray masivo=jsonResponse.getJSONArray("personas_barrio");
                        for(int i=0;i<masivo.length();i++){
                            JSONObject objeto= masivo.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            temp.setDisponible(objeto.getInt("DISPONIBLE"));
                            temp.setBarrio_diagnostico(objeto.getInt("BARRIO_DIAGNOSTICO"));
                            temp.setBarrio_fisica(objeto.getInt("BARRIO_FISICA"));
                            temp.setBarrio_tecnica(objeto.getInt("BARRIO_TECNICA"));
                            temp.setTotal_diagnostico(objeto.getInt("TOTAL_DIAG"));
                            temp.setTotal_fisica(objeto.getInt("TOTAL_FISICA"));
                            temp.setTotal_tecnica(objeto.getInt("TOTAL_TECNICA"));

                            temp.setNum_Camiseta(0);

                            lista_personas_barrio.add(temp);

                        }

                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE PERSONAS BARRIO ");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };
        RecuperarPersonasBarrio xx = new RecuperarPersonasBarrio(id_barrio_intimo,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);



    }

    public void onBackPressed() {

        Intent intent = new Intent(BarrioIntimoPersonaActivity.this,BarrioIntimoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        BarrioIntimoPersonaActivity.this.startActivity(intent);
        finish();


    }


}
