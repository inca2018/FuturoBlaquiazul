package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterMasivo;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterSeguimientoPersona;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterUsuarios;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Masivo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonas;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasSeguimiento;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ListaPersonaSeguimientoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView  recyclerView;
    private LinearLayoutManager linearLayout;
    private AdapterSeguimientoPersona adapter;
    private List<Persona> lista_seguimiento_persona;
    Context context;
    ProgressDialog progressDialog;
    RelativeLayout lista_vacia;
    SearchView buscador_diganostico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_persona_seguimiento);
        lista_seguimiento_persona=new ArrayList<>();
        buscador_diganostico=findViewById(R.id.buscador_jugadores);
        buscador_diganostico.setOnQueryTextListener(this);
        recyclerView=findViewById(R.id.Recycler_Persona_seg);
        lista_vacia=findViewById(R.id.lista_vacia_seguimiento);
        context=this;
        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);



        adapter = new AdapterSeguimientoPersona(this, lista_seguimiento_persona, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                if(adapter.getItemCount()==0){
                     lista_vacia.setVisibility(View.VISIBLE);
                     recyclerView.setVisibility(View.GONE);
                }else{
                    lista_vacia.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);

        if(Usuario.SESION_ACTUAL!=null){
            lista_Personas_Seguimientos(Usuario.SESION_ACTUAL.getId(),context);
        }else{
            Toast.makeText(context, "Existe Problema de Conexion al recuperar Postulantes", Toast.LENGTH_SHORT).show();
        }
    }
    private void lista_Personas_Seguimientos(int id_us, final Context context) {

        String id_user=String.valueOf(id_us);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Postulantes:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray masivo=jsonResponse.getJSONArray("seguimiento_persona");
                        for(int i=0;i<masivo.length();i++){
                            JSONObject objeto= masivo.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setFecha_registro_Captacion(objeto.getString("FECHA_RE"));
                            temp.setTotales_seguimientos(objeto.getInt("TOTAL_SEGUIMIENTO"));
                            temp.setUbigeo(objeto.getString("UBIGEO"));
                            temp.setFoto(objeto.getString("FOTO"));
                            Usuario u=new Usuario();
                            u.setId(objeto.getInt("ID_USER"));
                            u.setNombres(objeto.getString("NOMBRES_U"));
                            u.setApellidos(objeto.getString("APELLIDOS_U"));
                            temp.setUsuario(u);
                            lista_seguimiento_persona.add(temp);
                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                        System.out.println("LISTADO COMPLETO DE PERSONAS");
                    } else {
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                        //Toast.makeText(context, "Listado de Personas Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }

        };

        RecuperarPersonasSeguimiento xx = new RecuperarPersonasSeguimiento(id_user,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }


    @Override
    public boolean onQueryTextSubmit(String query) {


        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        List<Persona> filteredValues = new ArrayList<Persona>(lista_seguimiento_persona);
        for (Persona value : lista_seguimiento_persona) {
            if (!(value.getNombre_Persona().toLowerCase()+" "+value.getNombre_Persona().toLowerCase()).contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        linearLayout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterSeguimientoPersona(context,filteredValues, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);

        //Opciones();
        return false;
    }
    public void resetSearch() {
        linearLayout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterSeguimientoPersona(context,lista_seguimiento_persona, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);

        //Opciones();
    }


    public void onBackPressed() {

        Intent intent = new Intent(ListaPersonaSeguimientoActivity.this,PrincipalActivity.class);
        intent.putExtra("o","o1");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ListaPersonaSeguimientoActivity.this.startActivity(intent);


    }


}
