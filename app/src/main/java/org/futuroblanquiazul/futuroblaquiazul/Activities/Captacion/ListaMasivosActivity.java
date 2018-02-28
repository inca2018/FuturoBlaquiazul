package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterMasivo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Masivo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarMasivos;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaMasivosActivity extends AppCompatActivity {
    private RecyclerView recycler_masivo;
    private LinearLayoutManager linearLayout;
    private AdapterMasivo adapter;
    private List<Masivo> lista_masivos;
    Context context;
    ProgressDialog progressDialog;

    CardView nuevo_masivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masivo_creacion);
        nuevo_masivo=findViewById(R.id.nuevo_masivo);
               context=this;
        recycler_masivo=findViewById(R.id.Recylcer_Masivos);


        lista_masivos=new ArrayList<>();
        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        Listar_Masivos(context);

        adapter = new AdapterMasivo(this, lista_masivos, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

                Usuario.SESION_ACTUAL.setId_masivo(lista_masivos.get(position).getCodigo());

                Intent intent=new Intent(ListaMasivosActivity.this,ListaPersonaMasivoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ListaMasivosActivity.this.startActivity(intent);
            }
        });

        recycler_masivo.setAdapter(adapter);
        recycler_masivo.setLayoutManager(linearLayout);




        nuevo_masivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaMasivosActivity.this, MasivoNuevoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ListaMasivosActivity.this.startActivity(intent);

            }
        });
    }

    private void Listar_Masivos(final Context context) {


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Captación Masiva:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("masivos");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Masivo temp=new Masivo();
                            temp.setCodigo(objeto.getInt("ID"));
                            temp.setNombre_Masivo(objeto.getString("NOMBRE_MASIVO"));
                            Unidad_Territorial Departamento=new Unidad_Territorial();
                            Unidad_Territorial Provincia=new Unidad_Territorial();
                            Unidad_Territorial Distrito=new Unidad_Territorial();
                            Departamento.setCodigo(objeto.getInt("DEPARTAMENTO_ID"));
                            Departamento.setDescripcion(objeto.getString("DEPARTAMENTO_NOM"));
                            Provincia.setCodigo(objeto.getInt("PROVINCIA_ID"));
                            Provincia.setDescripcion(objeto.getString("PROVINCIA_NOM"));
                            Distrito.setCodigo(objeto.getInt("DISTRITO_ID"));
                            Distrito.setDescripcion(objeto.getString("DISTRITO_NOM"));
                            temp.setDepartamento(Departamento);
                            temp.setProvincia(Provincia);
                            temp.setDistrito(Distrito);

                            temp.setFecha_Creacion(objeto.getString("FECHA_CREACION"));
                            temp.setId_Usuario(objeto.getInt("SCOUT_ID"));
                            temp.setUsuario_Creador(objeto.getString("CREADO_NOM"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            temp.setTotal_postulantes(objeto.getInt("TOTAL"));

                            lista_masivos.add(temp);


                        }

                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE MASIVOS");
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

        RecuperarMasivos xx = new RecuperarMasivos(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }


    public void onBackPressed() {

        Intent intent = new Intent(ListaMasivosActivity.this,PrincipalActivity.class);
        intent.putExtra("o","o1");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ListaMasivosActivity.this.startActivity(intent);
        finish();


    }
}
