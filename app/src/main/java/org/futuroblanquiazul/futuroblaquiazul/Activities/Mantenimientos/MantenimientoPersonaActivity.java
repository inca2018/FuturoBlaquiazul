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
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPersona;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterUsuarios;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Area_Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Perfil;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasMant;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarUsuarios;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MantenimientoPersonaActivity extends AppCompatActivity {

    Button p_boton_nuevo_persona;
    RecyclerView recyclerView;
    AdapterPersona adapterPersona;
    List<Persona> Lista_Persona;
    ProgressDialog progressDialog;
    LinearLayoutManager linearLayoutManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_persona);
        p_boton_nuevo_persona=findViewById(R.id.p_boton_nuevo_persona);
        recyclerView=findViewById(R.id.p_lista_personas);
        context=this;
        Lista_Persona=new ArrayList<>();


        linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        Listar_Personas(context);
        adapterPersona = new AdapterPersona(context,Lista_Persona, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        });
        recyclerView.setAdapter(adapterPersona);
        recyclerView.setLayoutManager(linearLayoutManager);



        p_boton_nuevo_persona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MantenimientoPersonaActivity.this,EdicionPersonaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MantenimientoPersonaActivity.this.startActivity(intent);
            }
        });


    }

    private void Listar_Personas(final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Jugadores:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray xx=jsonResponse.getJSONArray("personas");
                            for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setNum(i+1);
                            temp.setId(objeto.getInt("ID"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setFecha_Nacimiento(objeto.getString("FECHA_NACIMIENTO"));

                            temp.setAno_nacimiento(objeto.getInt("ANO"));
                            temp.setMes_nacimiento(objeto.getInt("MES"));
                            temp.setDia_nacimiento(objeto.getInt("DIA"));
                            temp.setNacionalidad(objeto.getString("NACIONALIDAD"));
                            temp.setLugar_Residencia(objeto.getString("LUGAR"));

                            Unidad_Territorial depa=new Unidad_Territorial();
                            depa.setCodigo(objeto.getInt("ID_DEPARTAMENTO"));
                            depa.setDescripcion(objeto.getString("departamento"));
                            temp.setDepartamento(depa);

                            Unidad_Territorial prov=new Unidad_Territorial();
                            prov.setCodigo(objeto.getInt("ID_PROVINCIA"));
                            prov.setDescripcion(objeto.getString("provincia"));
                            temp.setProvincia(prov);

                            Unidad_Territorial dist=new Unidad_Territorial();
                            dist.setCodigo(objeto.getInt("ID_DISTRITO"));
                            dist.setDescripcion(objeto.getString("distrito"));
                            temp.setDistrito(dist);

                            temp.setClub_actual(objeto.getString("CLUB_ACTUAL"));
                            temp.setLiga_actual(objeto.getString("LIGA_ACTUAL"));
                            temp.setTelefono(objeto.getInt("TELE1"));
                            temp.setTelefono_fijo(objeto.getInt("TELEFONO_FIJO"));
                            temp.setBautizo(objeto.getInt("BAUTIZO"));
                            temp.setComunion(objeto.getInt("COMUNION"));
                            temp.setConfirmacion(objeto.getInt("CONFIRMACION"));
                            temp.setDni(objeto.getInt("DNI"));
                            temp.setCorreo(objeto.getString("EMAIL"));
                            temp.setNombre_Apoderado(objeto.getString("APODERADO"));
                            temp.setTelefono_apoderado(objeto.getInt("CONTACTO_APO"));
                            temp.setCategoria_Actual(objeto.getString("CATEGORIA_ACTUAL"));

                            temp.setFecha_registro(objeto.getString("FECHA_RE"));
                            temp.setFecha_actualizacion(objeto.getString("FECHA_ACTUALIZACION"));

                            temp.setFoto(objeto.getString("FOTO"));
                            Lista_Persona.add(temp);
                        }

                        adapterPersona.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE PERSONAS");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar Jugadores :"+e);
                }
            }
        };

        RecuperarPersonasMant xx = new RecuperarPersonasMant(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    public void onBackPressed() {

        Intent intent=new Intent(MantenimientoPersonaActivity.this,PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("o","o5");
        MantenimientoPersonaActivity.this.startActivity(intent);

    }
}
