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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Grupo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActivarEvaPersona;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarCategoria;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarCategoria;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONException;
import org.json.JSONObject;

public class EdicionCategoriactivity extends AppCompatActivity {


     EditText nombre_categoria;
     Spinner spinner_rangos;
     Spinner spinner_estados;
     Button guardar;
     Context context;
     ProgressDialog progressDialog;

    String [] lista_bases_numeros,lista_bases_numeros2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_categoriactivity);
        spinner_rangos=findViewById(R.id.cate_grupos);
        spinner_estados=findViewById(R.id.cate_estados);
        nombre_categoria=findViewById(R.id.cate_nombre);
        guardar=findViewById(R.id.cate_guardar);
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
        spinner_rangos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                debug("ON CHANGE RANGOS");
                if(position==0){
                    Recursos_Mantenimientos.TEMP.setRango_temporal(0);
                }else if(position==1){
                    Recursos_Mantenimientos.TEMP.setRango_temporal(1);
                }else if(position==2){
                    Recursos_Mantenimientos.TEMP.setRango_temporal(2);
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
            if(spinner_rangos.getSelectedItemPosition()!=0){

                Recuperar_Datos();
            }else{
                Toast.makeText(context, "Seleccione un Rango", Toast.LENGTH_SHORT).show();
            }
        }else{
            nombre_categoria.setError("Campo Obligatorio");
        }
    }

    private void Recuperar_Datos() {

        if(Recursos_Mantenimientos.TEMP.getCategoria_temporal()!=null){
              Plantel  temp=new Plantel();
              temp.setNombre_categoria(nombre_categoria.getText().toString());
              temp.setEstado( Recursos_Mantenimientos.TEMP.getEstado_Temporal());
              Grupo g=new Grupo();
              g.setId(Recursos_Mantenimientos.TEMP.getRango_temporal());
              temp.setRango(g);

             Actualizar_Categoria(Recursos_Mantenimientos.TEMP.getCategoria_temporal().getId(),temp);

         }else{
               Plantel  temp=new Plantel();
               temp.setNombre_categoria(nombre_categoria.getText().toString());
               temp.setEstado( Recursos_Mantenimientos.TEMP.getEstado_Temporal());
               Grupo g=new Grupo();
               g.setId(Recursos_Mantenimientos.TEMP.getRango_temporal());
               temp.setRango(g);

               Registrar_Categoria(Usuario.SESION_ACTUAL.getId(),temp);
         }

    }

    private void Actualizar_Categoria(int id_plan , Plantel temp) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Categorias:");
        progressDialog.setMessage("Actualizando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_rango=String.valueOf(temp.getRango().getId());
        String Nombre=temp.getNombre_categoria();
        String estado=String.valueOf(temp.getEstado());
        String id_plantel=String.valueOf(id_plan );
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();

                        Recursos_Mantenimientos.TEMP.setCategoria_temporal(null);
                        Recursos_Mantenimientos.TEMP.setEstado_Temporal(0);
                        Recursos_Mantenimientos.TEMP.setRango_temporal(0);

                        Intent intent=new Intent(EdicionCategoriactivity.this,MantenimientoCategoriasActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        EdicionCategoriactivity.this.startActivity(intent);
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

        ActualizarCategoria xx = new ActualizarCategoria(id_rango,Nombre,estado,id_plantel  ,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    private void Registrar_Categoria(int id_u,Plantel p) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Categorias:");
        progressDialog.setMessage("Registrando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_rango=String.valueOf(p.getRango().getId());
        String Nombre=p.getNombre_categoria();
        String id_usuario=String.valueOf(id_u);
        String estado=String.valueOf(p.getEstado());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();

                        Recursos_Mantenimientos.TEMP.setCategoria_temporal(null);
                        Recursos_Mantenimientos.TEMP.setEstado_Temporal(0);
                        Recursos_Mantenimientos.TEMP.setRango_temporal(0);

                        Intent intent=new Intent(EdicionCategoriactivity.this,MantenimientoCategoriasActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        EdicionCategoriactivity.this.startActivity(intent);
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

        RegistrarCategoria xx = new RegistrarCategoria(id_rango,Nombre,id_usuario,estado, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    private void Llenar_Spinner() {
        debug("ENTROO LLENAR SPINNER");
        lista_bases_numeros=new String[3];
        lista_bases_numeros[0]="-- SELECCIONE --";
        lista_bases_numeros[1]="ESCUELA BASE";
        lista_bases_numeros[2]="ESCUELA COMPETETITIVA";

        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_bases_numeros);
        spinner_rangos.setAdapter(adapter_arr);


        lista_bases_numeros2=new String[2];
        lista_bases_numeros2[0]="ACTIVO";
        lista_bases_numeros2[1]="DESACTIVO";

        ArrayAdapter<String> adapter_arr2=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_bases_numeros2);
        spinner_estados.setAdapter(adapter_arr2);


        if(Recursos_Mantenimientos.TEMP.getCategoria_temporal()!=null){
            Mostrar_datos();
            debug("ENTRO CON CATEGORIA LLENA");
        }

        debug("PASO LLENADO SPINNER");
    }

    private void Mostrar_datos() {
        nombre_categoria.setText(Recursos_Mantenimientos.TEMP.getCategoria_temporal().getNombre_categoria());
        if(Recursos_Mantenimientos.TEMP.getCategoria_temporal().getEstado()==1){
            spinner_estados.setSelection(0);
        }else if(Recursos_Mantenimientos.TEMP.getCategoria_temporal().getEstado()==2){
            spinner_estados.setSelection(1);
        }
        spinner_rangos.setSelection(Recursos_Mantenimientos.TEMP.getCategoria_temporal().getRango().getId());

    }

    public void onBackPressed() {

        Recursos_Mantenimientos.TEMP.setCategoria_temporal(null);
        Recursos_Mantenimientos.TEMP.setEstado_Temporal(0);
        Recursos_Mantenimientos.TEMP.setRango_temporal(0);

        Intent intent=new Intent(EdicionCategoriactivity.this,MantenimientoCategoriasActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        EdicionCategoriactivity.this.startActivity(intent);

    }


    public void debug(String f){
        System.out.println(f);
    }
}
