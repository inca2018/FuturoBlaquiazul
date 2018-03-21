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
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterUsuarios;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Area_Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Perfil;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBarrios;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarUsuarios;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MantenimientoUsuarioActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    SearchView buscador_usuarios;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Context context;
    AdapterUsuarios adapterUsuarios;
    List<Usuario> Lista_Usuarios;

    ProgressDialog progressDialog;
    Button nuevo_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_usuario);
        buscador_usuarios=findViewById(R.id.buscador_usuarios);
        buscador_usuarios.setOnQueryTextListener(this);

        nuevo_usuario=findViewById(R.id.accion_boton_nuevo_usuario);

        recyclerView=findViewById(R.id.lista_usuarios);
        Lista_Usuarios=new ArrayList<>();
        context=this;
        linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);


        adapterUsuarios = new AdapterUsuarios(context,Lista_Usuarios, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        });
        recyclerView.setAdapter(adapterUsuarios);
        recyclerView.setLayoutManager(linearLayoutManager);

        Listar_Usuarios(context);


        nuevo_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,EdicionUsuarioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
    private void Listar_Usuarios(final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Usuarios:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("usuarios");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);

                            Usuario temp=new Usuario();
                             temp.setId(objeto.getInt("ID"));
                             temp.setUsuario(objeto.getString("USUARIO"));
                             temp.setPassword(objeto.getString("PASSWORD"));
                             temp.setNombres(objeto.getString("NOMBRES"));
                             temp.setApellidos(objeto.getString("APELLIDOS"));
                             temp.setDni(objeto.getInt("DNI"));

                            Area_Usuario a=new Area_Usuario();
                            a.setId(objeto.getInt("CODIGO_AREA"));
                            a.setDescripcion(objeto.getString("DESCRIPCION"));

                            temp.setCargo(objeto.getString("CARGO"));
                            temp.setCorreo(objeto.getString("CORREO"));

                            Perfil p=new Perfil();
                            p.setId(objeto.getInt("CODIGO_PERFIL"));
                            p.setNombre_Perfil(objeto.getString("NOMBRE_PERFIL"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            temp.setFecha_creacion(objeto.getString("FECHA_REGISTRO"));
                            temp.setFecha_conexion(objeto.getString("FECHA_ACTUALIZADO"));
                            temp.setFechaModificado(objeto.getString("FECHA_MODIFICADO"));
                            temp.setFoto(objeto.getString("FOTO"));

                            temp.setPerfil(p);
                            temp.setArea_usuario(a);

                            Lista_Usuarios.add(temp);

                        }

                        adapterUsuarios.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE USUARIOS");
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

        RecuperarUsuarios xx = new RecuperarUsuarios(responseListener);
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

        List<Usuario> filteredValues = new ArrayList<Usuario>(Lista_Usuarios);
        for (Usuario value : Lista_Usuarios) {
            if (!value.getNombres().toLowerCase().contains(newText.toLowerCase()) || !value.getApellidos().toLowerCase().contains(newText.toLowerCase()) ) {
                filteredValues.remove(value);
            }
        }

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        adapterUsuarios = new AdapterUsuarios(context,filteredValues, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        });
        recyclerView.setAdapter(adapterUsuarios);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Opciones();
        return false;
    }
    public void resetSearch() {
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        adapterUsuarios = new AdapterUsuarios(context,Lista_Usuarios, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        recyclerView.setAdapter(adapterUsuarios);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Opciones();
    }


    public void onBackPressed() {
        Intent intent= new Intent(context,PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("o","o5");
        startActivity(intent);

    }
}
