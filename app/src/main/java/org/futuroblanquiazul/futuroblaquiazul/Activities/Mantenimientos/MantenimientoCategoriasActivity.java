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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterCategoria;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Grupo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBarrios;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarCategoriasPlantel;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MantenimientoCategoriasActivity extends AppCompatActivity {


    Button nuevo;
    RecyclerView recyclerView;
    AdapterCategoria adapterCategoria;
    List<Plantel> lista_categorias;
    LinearLayoutManager linearLayoutManager;
    Context context;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_categorias);
        recyclerView=findViewById(R.id.mant_lista_categorias);
        nuevo=findViewById(R.id.mant_nueva_categoria);
        lista_categorias=new ArrayList<>();
        context=this;
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapterCategoria = new AdapterCategoria(this, lista_categorias, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

                Recursos_Mantenimientos.TEMP.setCategoria_temporal(lista_categorias.get(position));
                Intent intent=new Intent(MantenimientoCategoriasActivity.this,CategoriasJugadoresActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MantenimientoCategoriasActivity.this.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapterCategoria);
        recyclerView.setLayoutManager(linearLayoutManager);
        Listar_Categorias(context);


        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,EdicionCategoriactivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void Listar_Categorias(final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Categorias:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("categorias");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Plantel temp=new Plantel();
                            temp.setNum(i+1);
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_categoria(objeto.getString("NOMBRE_CATEGORIA"));
                            Grupo f =new Grupo();
                            f.setId(objeto.getInt("RANGO_ID"));
                            f.setDescripcion(objeto.getString("NOMBRE_GRUPO"));
                            temp.setRango(f);
                            temp.setFecha_registro(objeto.getString("FECHA_REGISTRO"));
                            Usuario u =new Usuario();
                            u.setId(objeto.getInt("ID_USUARIO"));
                            u.setNombres(objeto.getString("NOMBRES"));
                            u.setApellidos(objeto.getString("APELLIDOS"));
                            temp.setUsuario(u);
                            temp.setEstado(objeto.getInt("ESTADO"));
                            temp.setTotal(objeto.getInt("TOTAL"));

                            lista_categorias.add(temp);
                        }

                        adapterCategoria.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE CATEGORIAS");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar categorias :"+e);
                }
            }
        };

        RecuperarCategoriasPlantel xx = new RecuperarCategoriasPlantel(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    public void onBackPressed() {
        Intent intent=new Intent(MantenimientoCategoriasActivity.this,PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("o","o5");
        MantenimientoCategoriasActivity.this.startActivity(intent);

    }
}
