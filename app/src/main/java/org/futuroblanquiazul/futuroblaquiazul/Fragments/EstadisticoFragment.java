package org.futuroblanquiazul.futuroblaquiazul.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico.ListaEventosEstadisticosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Ubigeo.UbigeoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Validar_Ubigeo;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.json.JSONException;
import org.json.JSONObject;

import static org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario.SESION_ACTUAL;
import static org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo.ESTADISTICO_UBIGEO;

public class EstadisticoFragment extends Fragment {

    Button btn1,btn2;
    TextView ubigeo;
    Context mContext;
    ImageView ubigeo_imagen;
    public EstadisticoFragment() {
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        //setHasOptionsMenu(true);
        Verificacion_UbigeoEstadistico();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_estadistico, container, false);
        btn1=v.findViewById(R.id.accion_eventos_estadisticos);
        btn2=v.findViewById(R.id.accion_eventos_resultados);
        ubigeo=v.findViewById(R.id.texto_ubigeo3);
        ubigeo_imagen=v.findViewById(R.id.icon_ubigeo3);




        ubigeo_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ESTADISTICO_UBIGEO.isEstado()==true){
                    Intent intent= new Intent(mContext,UbigeoActivity.class);
                    intent.putExtra("TYPE","UPDATE");
                    intent.putExtra("MODULO","4");
                    startActivity(intent);
                }else{
                    Intent intent= new Intent(mContext,UbigeoActivity.class);
                    intent.putExtra("TYPE","NEW");
                    intent.putExtra("MODULO","4");
                    startActivity(intent);
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GestionUbigeo.ESTADISTICO_UBIGEO.isEstado()==true){
                    Intent intent= new Intent(mContext,ListaEventosEstadisticosActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(mContext, "Seleccione Ubicación de Trabajo", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return v;
    }

    private void Verificacion_UbigeoEstadistico() {
        int codigo_captacion=4;
        ESTADISTICO_UBIGEO.setCodigo_modulo(codigo_captacion);
        int codigo_usuario=SESION_ACTUAL.getId();


        System.out.println("CODIGO_USER:"+codigo_usuario+" CODIGO_MODULO:"+codigo_captacion+" ENVIADOS");
        Validar_Ubicacion(codigo_usuario,ESTADISTICO_UBIGEO.getCodigo_modulo(),mContext);

    }

    public void Validar_Ubicacion(final int id_user,final int id_modulo, final Context context) {
        String user = String.valueOf(id_user).trim();
        String modulo=String.valueOf(id_modulo).trim();

        System.out.println("buscando modulos:"+modulo);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        String ubigeo_general=jsonResponse.getString("ubigeo_general");
                        boolean estado=jsonResponse.getBoolean("estado");
                        switch (id_modulo){
                            case 4:

                                if(estado==true){
                                    SpannableString mitextoU = new SpannableString("UBICACION: "+ubigeo_general);
                                    mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
                                    ubigeo.setText(mitextoU);
                                    ubigeo_imagen.setImageResource(R.mipmap.icon_update);
                                    ESTADISTICO_UBIGEO.setEstado(true);

                                    Unidad_Territorial Departamento=new Unidad_Territorial();
                                    Departamento.setCodigo(jsonResponse.getInt("id_depa"));
                                    Departamento.setDescripcion(jsonResponse.getString("desc_depa"));
                                    Unidad_Territorial Provincia=new Unidad_Territorial();
                                    Provincia.setCodigo(jsonResponse.getInt("id_prov"));
                                    Provincia.setDescripcion(jsonResponse.getString("desc_prov"));
                                    Unidad_Territorial Distrito=new Unidad_Territorial();
                                    Distrito.setCodigo(jsonResponse.getInt("id_dist"));
                                    Distrito.setDescripcion(jsonResponse.getString("desc_dist"));

                                    ESTADISTICO_UBIGEO.setDepartamento(Departamento);
                                    ESTADISTICO_UBIGEO.setProvincia(Provincia);
                                    ESTADISTICO_UBIGEO.setDistrito(Distrito);
                                    ESTADISTICO_UBIGEO.setUbigeo_descripcion(ubigeo_general);



                                    System.out.println("PASO TRUE ESTADO MODULO 1");
                                }else{
                                    SpannableString mitextoU = new SpannableString("SELECCIONE UBICACION DE TRABAJO");
                                    mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
                                    ubigeo.setText(mitextoU);
                                    ubigeo_imagen.setImageResource(R.mipmap.icon_next);
                                    ESTADISTICO_UBIGEO.setEstado(false);

                                    System.out.println("PASO FALSE ESTADO MODULO 1");
                                }

                                break;


                            default:
                                Toast.makeText(context, "No existe id de modulo!!", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        String error = jsonResponse.getString("error");
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en Recupera ubigeo user:"+e);
                }
            }
        };

        Validar_Ubigeo recuperarCodigo = new Validar_Ubigeo(user, modulo, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarCodigo);
    }





}
