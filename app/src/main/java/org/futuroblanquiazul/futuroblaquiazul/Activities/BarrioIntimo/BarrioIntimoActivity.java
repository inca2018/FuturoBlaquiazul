package org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterSeguimientoPersona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBarrios;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BarrioIntimoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView recycler_barrio;
    private LinearLayoutManager linearLayout;
    private AdapterBarrio adapter;
    private List<BarrioIntimo> lista_barrio_intimo;
    Context context;
    ProgressDialog progressDialog;
    SearchView buscador_barrio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrio_intimo);
        recycler_barrio=findViewById(R.id.recycler_barrio_intimo);
        buscador_barrio=findViewById(R.id.buscador_barrio);
        buscador_barrio.setOnQueryTextListener(this);
        context=this;


        lista_barrio_intimo=new ArrayList<>();
        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        Listar_barrio_intimo(context);

        adapter = new AdapterBarrio(this, lista_barrio_intimo, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
                Usuario.SESION_ACTUAL.setId_barrio_intimo(lista_barrio_intimo.get(position).getId());
                Usuario.SESION_ACTUAL.setBarrio_datos(lista_barrio_intimo.get(position));

                Intent intent=new Intent(BarrioIntimoActivity.this,BarrioIntimoPersonaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                BarrioIntimoActivity.this.startActivity(intent);
            }
        });

        recycler_barrio.setAdapter(adapter);
        recycler_barrio.setLayoutManager(linearLayout);

    }

    private void Listar_barrio_intimo(final Context context) {


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Barrio Intimo:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("barrios");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            BarrioIntimo temp=new BarrioIntimo();
                            temp.setId(objeto.getInt("ID"));

                            temp.setNombreEvento(objeto.getString("NOMBRE_BARRIO"));

                            Unidad_Territorial Departamento=new Unidad_Territorial();
                            Unidad_Territorial Provincia=new Unidad_Territorial();
                            Unidad_Territorial Distrito=new Unidad_Territorial();

                            Departamento.setCodigo(objeto.getInt("ID_DEPARTAMENTO"));
                            Departamento.setDescripcion(objeto.getString("DEPARTAMENTO_NOM"));
                            Provincia.setCodigo(objeto.getInt("ID_PROVINCIA"));
                            Provincia.setDescripcion(objeto.getString("PROVINCIA_NOM"));
                            Distrito.setCodigo(objeto.getInt("ID_DISTRITO"));
                            Distrito.setDescripcion(objeto.getString("DISTRITO_NOM"));
                            temp.setDepartamento(Departamento);
                            temp.setProvincia(Provincia);
                            temp.setDistrito(Distrito);

                            temp.setDescripcion_ubigeo(Departamento.getDescripcion()+"/"+Provincia.getDescripcion()+"/"+Distrito.getDescripcion());
                            temp.setFechaRealizacion(objeto.getString("FECHA_REALIZAR"));

                            Usuario user=new Usuario();
                            user.setId(objeto.getInt("ID_USER"));
                            user.setNombres(objeto.getString("CREADO_NOM"));
                            user.setApellidos(objeto.getString("APELLIDOS"));
                            temp.setUsuario(user);


                            temp.setEstado(objeto.getInt("ESTADO"));
                            temp.setDescripcion(objeto.getString("DESCRIPCION"));


                            temp.setCantidad_Postulantes(objeto.getInt("TOTAL"));
                            temp.setAno(objeto.getInt("ANO"));
                            temp.setMes(objeto.getInt("MES"));
                            temp.setDia(objeto.getInt("DIA"));

                            lista_barrio_intimo.add(temp);


                        }

                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE BARRIO");
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

        RecuperarBarrios xx = new RecuperarBarrios(responseListener);
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

        List<BarrioIntimo> filteredValues = new ArrayList<BarrioIntimo>(lista_barrio_intimo);
        for (BarrioIntimo value : lista_barrio_intimo) {
            if (!(value.getNombreEvento().toLowerCase()).contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        linearLayout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterBarrio(context,filteredValues, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        });
        recycler_barrio.setAdapter(adapter);
        recycler_barrio.setLayoutManager(linearLayout);

        //Opciones();
        return false;
    }
    public void resetSearch() {
        linearLayout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterBarrio(context,lista_barrio_intimo, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        recycler_barrio.setAdapter(adapter);
        recycler_barrio.setLayoutManager(linearLayout);

        //Opciones();
    }



    public void onBackPressed() {
        Usuario.SESION_ACTUAL.setBarrio_datos(null);
        Intent intent=new Intent(BarrioIntimoActivity.this,PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("o","o1");
        BarrioIntimoActivity.this.startActivity(intent);
    }

}
