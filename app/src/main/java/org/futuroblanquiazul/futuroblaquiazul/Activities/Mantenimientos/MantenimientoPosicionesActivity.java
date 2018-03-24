package org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos;

import android.app.ProgressDialog;
import android.content.Context;
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

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPosiciones;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBarrios;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPos1;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPos2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPosiciones;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPosicion1;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPosicion2;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MantenimientoPosicionesActivity extends AppCompatActivity {

    EditText Posicion1,Posicion2;
    Button Agregar1,Agregar2;
    RecyclerView recyclerView1,recyclerView2;
    LinearLayoutManager linearLayoutManager1,linearLayoutManager2;
    AdapterPosiciones adapterPosiciones,adapterPosiciones2;
    Context context;
    List<Posicion> ListaPosiciones1,ListaPosiciones2;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_posiciones);

        Posicion1=findViewById(R.id.mant_pos_nuevo1);
        Posicion2=findViewById(R.id.mant_pos_nuevo2);
        Agregar1=findViewById(R.id.mant_pos_agregar1);
        Agregar2=findViewById(R.id.mant_pos_agregar2);
        recyclerView1=findViewById(R.id.mant_pos_recycler1);
        recyclerView2=findViewById(R.id.mant_pos_recycler2);
        ListaPosiciones1=new ArrayList<>();
        ListaPosiciones2=new ArrayList<>();

        linearLayoutManager1=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        linearLayoutManager2=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        adapterPosiciones = new AdapterPosiciones(context,ListaPosiciones1, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        });
        recyclerView1.setAdapter(adapterPosiciones);
        recyclerView1.setLayoutManager(linearLayoutManager1);

        adapterPosiciones2 = new AdapterPosiciones(context,ListaPosiciones2, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        });
        recyclerView2.setAdapter(adapterPosiciones2);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        Agregar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if(Posicion1.getText().length()!=0){
                    Agregar_Posicion1(Posicion1.getText().toString(),context);
                }else{
                    Toast.makeText(context, "Ingrese Posición", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Agregar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Posicion2.getText().length()!=0){
                    Agregar_Posicion2(Posicion2.getText().toString(),context);
                }else{
                    Toast.makeText(context, "Ingrese Posición 2", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Lista_Posiciones_1(context);
    }

    private void Agregar_Posicion1(String s,final Context context) {
        String posicion=String.valueOf(s);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(context, "Posición Agregado Correctamente!", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        RegistrarPosicion1 xx = new RegistrarPosicion1(posicion, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    private void Agregar_Posicion2(String s,final Context context) {
        String posicion=String.valueOf(s);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(context, "Posición Agregado Correctamente!", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        RegistrarPosicion2 xx = new RegistrarPosicion2(posicion, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }

    private void Lista_Posiciones_1(final Context context) {


        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray xx=jsonResponse.getJSONArray("posiciones1");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            Posicion temp=new Posicion();
                            temp.setNum(i+1);
                            temp.setNombre_Posicione(objeto.getString("NOMBRE_POSICION"));
                            temp.setEstado(objeto.getInt("ESTADO"));

                            ListaPosiciones1.add(temp);


                        }

                        adapterPosiciones.notifyDataSetChanged();


                        Listar_Posiciones_2(context);

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

        RecuperarPos1 xx = new RecuperarPos1(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);



    }

    private void Listar_Posiciones_2(final Context context) {

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray xx=jsonResponse.getJSONArray("posiciones2");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            Posicion temp=new Posicion();
                            temp.setNum(i+1);
                            temp.setNombre_Posicione(objeto.getString("NOMBRE_POSICION"));
                            temp.setEstado(objeto.getInt("ESTADO"));

                            ListaPosiciones2.add(temp);


                        }

                        adapterPosiciones2.notifyDataSetChanged();



                        System.out.println("LISTADO COMPLETO DE POSICION 2");
                    } else {

                        Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarPos2 xx = new RecuperarPos2(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);





    }


}
