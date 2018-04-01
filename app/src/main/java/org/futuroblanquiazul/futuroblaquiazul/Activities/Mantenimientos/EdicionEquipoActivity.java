package org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Equipo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Grupo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarCategoria;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarEquipo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarCategoria;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarEquipo;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONException;
import org.json.JSONObject;

public class EdicionEquipoActivity extends AppCompatActivity {


    EditText nombre_categoria;
    Spinner spinner_estados;
    Button guardar;
    Context context;
    ProgressDialog progressDialog;

    String [] lista_bases_numeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_equipo);


        spinner_estados=findViewById(R.id.eq_estados);
        nombre_categoria=findViewById(R.id.eq_nom);
        guardar=findViewById(R.id.eq_guardar);
        context=this;
        Llenar_Spinner();
        spinner_estados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                debug("ON CHANGE ESTADOS");
                if(position==0){
                    Recursos_Mantenimientos.TEMP.setEstado_Temporal(1);
                }else if(position==1){
                    Recursos_Mantenimientos.TEMP.setEstado_Temporal(2);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validar_datos();
            }
        });
    }

    private void Validar_datos() {

        if(nombre_categoria.getText().toString().length()!=0){
                Recuperar_Datos();
        }else{
            nombre_categoria.setError("Campo Obligatorio");
        }
    }

    private void Recuperar_Datos() {

        if(Recursos_Mantenimientos.TEMP.getEquipo_temporal()!=null){
            Equipo temp=new Equipo();
            temp.setNombre_equipo(nombre_categoria.getText().toString());
            temp.setEstado( Recursos_Mantenimientos.TEMP.getEstado_Temporal());
            Usuario u=new Usuario();
            u.setId(Usuario.SESION_ACTUAL.getId());
            temp.setUser(u);
            Actualizar_Equipo(Recursos_Mantenimientos.TEMP.getEquipo_temporal().getId(),temp);

        }else{
            Equipo temp=new Equipo();
            temp.setNombre_equipo(nombre_categoria.getText().toString());
            temp.setEstado( Recursos_Mantenimientos.TEMP.getEstado_Temporal());
            Registrar_Equipo(Usuario.SESION_ACTUAL.getId(),temp);
        }

    }

    private void Actualizar_Equipo(int id_eq , Equipo temp) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Equipos:");
        progressDialog.setMessage("Actualizando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String Nombre=temp.getNombre_equipo();
        String estado=String.valueOf(temp.getEstado());
        String id_equipo=String.valueOf(id_eq );
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();

                        Recursos_Mantenimientos.TEMP.setEquipo_temporal(null);
                        Recursos_Mantenimientos.TEMP.setEstado_Temporal(0);
                        Intent intent=new Intent(EdicionEquipoActivity.this,MantenimientoEquipoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        EdicionEquipoActivity.this.startActivity(intent);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        ActualizarEquipo xx = new ActualizarEquipo(Nombre,estado,id_equipo,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    private void Registrar_Equipo(int id, Equipo temp) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Equipos:");
        progressDialog.setMessage("Registrando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String id_usuario=String.valueOf(id);
        String Nombre=temp.getNombre_equipo();
        String estado=String.valueOf(temp.getEstado());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();

                        Recursos_Mantenimientos.TEMP.setEquipo_temporal(null);
                        Recursos_Mantenimientos.TEMP.setEstado_Temporal(0);
                        Intent intent=new Intent(EdicionEquipoActivity.this,MantenimientoEquipoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        EdicionEquipoActivity.this.startActivity(intent);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        RegistrarEquipo xx = new RegistrarEquipo(id_usuario,Nombre,estado, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);



    }

    private void Llenar_Spinner() {

        lista_bases_numeros=new String[2];
        lista_bases_numeros[0]="ACTIVO";
        lista_bases_numeros[1]="DESACTIVO";
        ArrayAdapter<String> adapter_arr2=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_bases_numeros);
        spinner_estados.setAdapter(adapter_arr2);

        if(Recursos_Mantenimientos.TEMP.getEquipo_temporal()!=null){
            Mostrar_datos();

        }


    }

    private void Mostrar_datos() {
        nombre_categoria.setText(Recursos_Mantenimientos.TEMP.getEquipo_temporal().getNombre_equipo());
        if(Recursos_Mantenimientos.TEMP.getEquipo_temporal().getEstado()==1){
            spinner_estados.setSelection(0);
        }else if(Recursos_Mantenimientos.TEMP.getEquipo_temporal().getEstado()==2){
            spinner_estados.setSelection(1);
        }

    }

    public void debug(String f){
        System.out.println(f);
    }

    public void onBackPressed() {

        Recursos_Mantenimientos.TEMP.setEquipo_temporal(null);
        Recursos_Mantenimientos.TEMP.setEstado_Temporal(0);

        Intent intent=new Intent(EdicionEquipoActivity.this,MantenimientoEquipoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        EdicionEquipoActivity.this.startActivity(intent);

    }
}
