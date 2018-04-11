package org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPosiciones;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Entity.GrupoPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarGrupos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarGrupos2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPlantel_Todo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPos1;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPos2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarGrupoPrueba;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPosicion1;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPosicion2;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MantenimientoGrupoPruebaActivity extends AppCompatActivity {

    EditText Posicion1;
    Button Agregar1;
    RecyclerView recyclerView1;
    LinearLayoutManager linearLayoutManager1;
    Context context;
    ProgressDialog progressDialog;
    List<Plantel> ListaPlantel;
    List<GrupoPruebas> ListaGrupo;
    AdapterPruebas adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_grupo_prueba);
        context=this;
        Posicion1=findViewById(R.id.mant_grupo_nuevo);
        Agregar1=findViewById(R.id.mant_grupo_agregar1);
        recyclerView1=findViewById(R.id.mant_grupo_recycler1);
        ListaPlantel=new ArrayList<>();
        ListaGrupo=new ArrayList<>();
        linearLayoutManager1=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterPruebas(context,ListaGrupo, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        });
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        Lista_grupos_pruebas(context);
        Agregar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Posicion1.getText().length()!=0){
                    Agregar_grupo_plantel();
                }else{
                    Toast.makeText(context, "Ingrese Grupo de Prueba", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void Agregar_grupo_plantel() {

       for(int i=0;i<ListaPlantel.size();i++){
           Agregar_Posicion1(Posicion1.getText().toString(),ListaPlantel.get(i).getId(),context);
       }

    }
    private void Agregar_Posicion1(String gru, int id, final Context context) {
        debug("AGREGAR GRUPO 1");
        String id_user=String.valueOf(Usuario.SESION_ACTUAL.getId());
        String grupo=String.valueOf(gru);
        String Observaciones="Grupo de Pruebas Creado en Todas las Categorias - "+grupo;
        String id_plantel=String.valueOf(id);
        String estado=String.valueOf(1);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(context, "Grupo Agregado Correctamente!", Toast.LENGTH_SHORT).show();
                        Posicion1.setText("");

                        ListaGrupo.clear();

                        Lista_grupos_pruebas(context);
                    }else {

                        progressDialog.dismiss();
                        String error=jsonResponse.getString("validar");
                        if(error.length()!=0){
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Error de conexion al Recuperar Grupo", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        RegistrarGrupoPrueba xx = new RegistrarGrupoPrueba(id_user,grupo,Observaciones,id_plantel,estado, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Lista_grupos_pruebas(final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Mantenimiento:");
        progressDialog.setMessage("Listando Grupos de Prueba..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray xx=jsonResponse.getJSONArray("grupos");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            GrupoPruebas temp=new GrupoPruebas();
                            temp.setNum(i+1);
                            temp.setId(objeto.getInt("ID"));
                            temp.setDescripcion(objeto.getString("DESCRIPCION"));
                            temp.setEstado(objeto.getInt("ESTADO"));

                            ListaGrupo.add(temp);

                           debug(" LISTA GRUPOS : "+ListaGrupo.get(i).getDescripcion());
                        }

                        adapter.notifyDataSetChanged();

                        listar_categorias(context);

                        System.out.println("LISTADO COMPLETO DE BARRIO");
                    } else {

                        Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarGrupos2 xx = new RecuperarGrupos2(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);



    }
    private void listar_categorias(final Context context) {

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("plantel_todo");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Plantel temp=new Plantel();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_categoria(objeto.getString("NOMBRE_CATEGORIA"));
                            ListaPlantel.add(temp);
                        }

                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        //Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarPlantel_Todo xx = new RecuperarPlantel_Todo(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    public void onBackPressed() {
        Recursos_Mantenimientos.Posiciones1.clear();

        Recursos_Mantenimientos.ADAPTER1=null;


        Intent intent=new Intent(MantenimientoGrupoPruebaActivity.this,PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("o","o5");
        MantenimientoGrupoPruebaActivity.this.startActivity(intent);

    }
    public void debug(String sm){
        System.out.println(sm);
    }


}
