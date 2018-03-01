package org.futuroblanquiazul.futuroblaquiazul.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaMasivosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaPersonaMasivoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterMasivo;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterSolicitudes;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Masivo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Solicitudes;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarMasivos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarSolicitudes;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SolicitudesFragment extends Fragment {

    Context context;

    RecyclerView recyclerView;
    private LinearLayoutManager linearLayout;
    private AdapterSolicitudes adapter;
    private List<Solicitudes> lista_solicitudes;
    ProgressDialog progressDialog;

    public SolicitudesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=getActivity();
        lista_solicitudes=new ArrayList<>();

        Listar_Solicitudes(context);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_solicitudes, container, false);

        recyclerView=v.findViewById(R.id.lista_solicitudes);
        linearLayout = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);


        adapter = new AdapterSolicitudes(context, lista_solicitudes, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);



        return v;
    }


    private void Listar_Solicitudes(final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Solicitudes:");
        progressDialog.setMessage("Listando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("solicitudes");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Solicitudes temp=new Solicitudes();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_solicitud(objeto.getString("NOMBRES"));
                            temp.setDni_solicitud(objeto.getInt("DNI"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            temp.setFecha_re(objeto.getString("FECHA_REGISTRO"));

                            lista_solicitudes.add(temp);

                        }

                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE SOLICITUDES");
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

        RecuperarSolicitudes xx = new RecuperarSolicitudes(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

}
