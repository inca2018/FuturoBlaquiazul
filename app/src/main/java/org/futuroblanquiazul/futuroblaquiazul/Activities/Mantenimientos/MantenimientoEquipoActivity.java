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

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterCategoria;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterEquipo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Equipo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Grupo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarCategoriasPlantel;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarEquipos;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MantenimientoEquipoActivity extends AppCompatActivity {


    Button nuevo;
    RecyclerView recyclerView;
    AdapterEquipo adapterEquipo;
    List<Equipo> lista_equipo;
    LinearLayoutManager linearLayoutManager;
    Context context;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_equipo);
        recyclerView=findViewById(R.id.mant_lista_equipos);
        nuevo=findViewById(R.id.mant_nueva_equipo);
        lista_equipo=new ArrayList<>();
        context=this;
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapterEquipo = new AdapterEquipo(this, lista_equipo, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

             Recursos_Mantenimientos.TEMP.setEquipo_temporal(lista_equipo.get(position));
                Intent intent=new Intent(MantenimientoEquipoActivity.this,EquipoJugadoresActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MantenimientoEquipoActivity.this.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapterEquipo);
        recyclerView.setLayoutManager(linearLayoutManager);

        Listar_Equipos(context);


        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,EdicionEquipoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });

    }

    private void Listar_Equipos(final Context context) {
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
                        JSONArray departamentos=jsonResponse.getJSONArray("equipos");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Equipo temp=new Equipo();
                            temp.setNum(i+1);
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_equipo(objeto.getString("NOMBRE_SUB"));
                            temp.setFecha_registro(objeto.getString("FECHA_REGISTRO"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            Usuario u=new Usuario();
                            u.setId(objeto.getInt("ID_USUARIO"));
                            u.setNombres(objeto.getString("NOMBRES"));
                            u.setApellidos(objeto.getString("APELLIDOS"));
                            temp.setUser(u);
                            temp.setTotal(objeto.getInt("TOTAL"));
                            lista_equipo.add(temp);
                        }

                        adapterEquipo.notifyDataSetChanged();
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

        RecuperarEquipos xx = new RecuperarEquipos(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    public void onBackPressed() {
        Intent intent=new Intent(MantenimientoEquipoActivity.this,PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("o","o5");
        MantenimientoEquipoActivity.this.startActivity(intent);

    }
}
