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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBarrio2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBarrios;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MantenimientoBarrioIntimoActivity extends AppCompatActivity {

    private RecyclerView recycler_barrio;
    private LinearLayoutManager linearLayout;
    private AdapterBarrio2 adapter;
    private List<BarrioIntimo> lista_barrio_intimo;
    Context context;
    ProgressDialog progressDialog;
    RelativeLayout lista_vacia;
    Button mant_barrio_nuevo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_barrio_intimo);
        context=this;
        recycler_barrio=findViewById(R.id.recycler_mant_barrio);
        lista_vacia=findViewById(R.id.lista_vacia_barrio_intimo);
        lista_barrio_intimo=new ArrayList<>();
        mant_barrio_nuevo=findViewById(R.id.mant_barrio_nuevo);
        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterBarrio2(this, lista_barrio_intimo, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

                Recursos_Mantenimientos.TEMP.setEvento_temporal(lista_barrio_intimo.get(position));
                Intent intent=new Intent(MantenimientoBarrioIntimoActivity.this,BarrioIntimoJugadoresActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MantenimientoBarrioIntimoActivity.this.startActivity(intent);
            }
        });
        recycler_barrio.setAdapter(adapter);
        recycler_barrio.setLayoutManager(linearLayout);

        Listar_barrio_intimo(context);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                if(adapter.getItemCount()==0){
                    lista_vacia.setVisibility(View.VISIBLE);
                    recycler_barrio.setVisibility(View.GONE);
                }else{
                    lista_vacia.setVisibility(View.GONE);
                    recycler_barrio.setVisibility(View.VISIBLE);
                }
            }
        });
        mant_barrio_nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,EdicionBarrioIntimoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
    }
    private void Listar_barrio_intimo(final Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Mantenimiento Barrio Intimo:");
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
                        adapter.notifyDataSetChanged();
                        //Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
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

    public void onBackPressed() {
        Intent intent=new Intent(MantenimientoBarrioIntimoActivity.this,PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("o","o5");
        MantenimientoBarrioIntimoActivity.this.startActivity(intent);

    }
}
