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

import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.CaptacionActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaMasivosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Ubigeo.UbigeoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Validar_Ubigeo;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.json.JSONException;
import org.json.JSONObject;

import static org.futuroblanquiazul.futuroblaquiazul.ActivityEntity.modulo_captacion.BASE;
import static org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario.SESION_ACTUAL;


public class CaptacionFragment extends Fragment {
    public Context mContext;
    Button accion1,accion2,accion3,accion5,accion6;
    TextView texto_ubigeo_Capta,texto_ubigeo_Capta_masivo,texto_ubigeo_barrio;
    ImageView imagen_ubigeo_Capta,imagen_ubigeo_Capta_masivo,imagen_ubigeo_barrio;

    public CaptacionFragment() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        //setHasOptionsMenu(true);
        Verificacion_UbigeoCaptacion();
        Verificacion_UbigeoCaptacion_Masivo();
        Verificacion_UbigeoCaptacion_Barrio();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_captacion, container, false);

        accion1=(Button)v.findViewById(R.id.accion_1);
        texto_ubigeo_Capta=v.findViewById(R.id.texto_ubigeo);
        imagen_ubigeo_Capta=v.findViewById(R.id.icon_ubigeo);
        texto_ubigeo_Capta_masivo=v.findViewById(R.id.texto_ubigeo_masivo);
        imagen_ubigeo_Capta_masivo=v.findViewById(R.id.icon_ubigeo_masivo);
        texto_ubigeo_barrio=v.findViewById(R.id.texto_ubigeo_barrio);
        imagen_ubigeo_barrio=v.findViewById(R.id.icon_ubigeo_barrio);

        accion2=(Button)v.findViewById(R.id.accion_2);
        accion3=(Button)v.findViewById(R.id.accion_3);

        accion5=(Button)v.findViewById(R.id.accion_5);
        accion6=(Button)v.findViewById(R.id.accion_6);

       Acciones();

        return v;
    }
    private void Acciones() {
        accion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GestionUbigeo.CAPTACION_UBIGEO.isEstado()==true){
                    Intent intent= new Intent(mContext,CaptacionActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(mContext, "Seleccione Ubicación de Trabajo", Toast.LENGTH_SHORT).show();
                }
            }
        });
        accion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(GestionUbigeo.CAPTACION_UBIGEO.isEstado()==true){
                    Intent intent= new Intent(mContext,CaptacionActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(mContext, "Seleccione Ubicación de Trabajo", Toast.LENGTH_SHORT).show();
                }
            }
        });
        accion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(GestionUbigeo.CAPTACION_UBIGEO_MASIVO.isEstado()==true){
                    Intent intent= new Intent(mContext,ListaMasivosActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(mContext, "Seleccione Ubicación de Trabajo", Toast.LENGTH_SHORT).show();
                }
            }
        });



        accion5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(GestionUbigeo.CAPTACION_UBIGEO_BARRIO.isEstado()==true){
                    Intent intent= new Intent(mContext,ListaMasivosActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(mContext, "Seleccione Ubicación de Trabajo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        accion6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(GestionUbigeo.CAPTACION_UBIGEO_BARRIO.isEstado()==true){
                    Intent intent= new Intent(mContext,CaptacionActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(mContext, "Seleccione Ubicación de Trabajo", Toast.LENGTH_SHORT).show();
                }
            }
        });



        imagen_ubigeo_Capta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GestionUbigeo.CAPTACION_UBIGEO.isEstado()==true){
                    Intent intent= new Intent(mContext,UbigeoActivity.class);
                    intent.putExtra("TYPE","UPDATE");
                    intent.putExtra("MODULO","1");
                    startActivity(intent);
                }else{
                    Intent intent= new Intent(mContext,UbigeoActivity.class);
                    intent.putExtra("TYPE","NEW");
                    intent.putExtra("MODULO","1");
                    startActivity(intent);
                }
            }
        });
        imagen_ubigeo_Capta_masivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GestionUbigeo.CAPTACION_UBIGEO_MASIVO.isEstado()==true){
                    Intent intent= new Intent(mContext,UbigeoActivity.class);
                    intent.putExtra("TYPE","UPDATE");
                    intent.putExtra("MODULO","2");
                    startActivity(intent);
                }else{
                    Intent intent= new Intent(mContext,UbigeoActivity.class);
                    intent.putExtra("TYPE","NEW");
                    intent.putExtra("MODULO","2");
                    startActivity(intent);
                }
            }
        });


        imagen_ubigeo_barrio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GestionUbigeo.CAPTACION_UBIGEO_BARRIO.isEstado()==true){
                    Intent intent= new Intent(mContext,UbigeoActivity.class);
                    intent.putExtra("TYPE","UPDATE");
                    intent.putExtra("MODULO","3");
                    startActivity(intent);
                }else{
                    Intent intent= new Intent(mContext,UbigeoActivity.class);
                    intent.putExtra("TYPE","NEW");
                    intent.putExtra("MODULO","3");
                    startActivity(intent);
                }
            }
        });

    }
    private void Verificacion_UbigeoCaptacion() {
               int codigo_captacion=1;
               GestionUbigeo.CAPTACION_UBIGEO.setCodigo_modulo(codigo_captacion);
               int codigo_usuario=SESION_ACTUAL.getId();

               BASE.setId_usuario(SESION_ACTUAL.getId());

               System.out.println("CODIGO_USER:"+codigo_usuario+" CODIGO_MODULO:"+codigo_captacion+" ENVIADOS");
               Validar_Ubicacion(codigo_usuario,GestionUbigeo.CAPTACION_UBIGEO.getCodigo_modulo(),mContext);

    }
    private void Verificacion_UbigeoCaptacion_Masivo() {
        int codigo_captacion=2;
        GestionUbigeo.CAPTACION_UBIGEO_MASIVO.setCodigo_modulo(codigo_captacion);
        int codigo_usuario=SESION_ACTUAL.getId();

        BASE.setId_usuario(SESION_ACTUAL.getId());
        System.out.println("CODIGO_USER:"+codigo_usuario+" CODIGO_MODULO:"+codigo_captacion+" ENVIADOS");
        Validar_Ubicacion(codigo_usuario,GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getCodigo_modulo(),mContext);


    }
    private void Verificacion_UbigeoCaptacion_Barrio() {
        int codigo_captacion=3;
        GestionUbigeo.CAPTACION_UBIGEO_BARRIO.setCodigo_modulo(codigo_captacion);
        int codigo_usuario=SESION_ACTUAL.getId();

        BASE.setId_usuario(SESION_ACTUAL.getId());
        System.out.println("CODIGO_USER:"+codigo_usuario+" CODIGO_MODULO:"+codigo_captacion+" ENVIADOS");
        Validar_Ubicacion(codigo_usuario,GestionUbigeo.CAPTACION_UBIGEO_BARRIO.getCodigo_modulo(),mContext);

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
                            case 1:

                                if(estado==true){
                                    SpannableString mitextoU = new SpannableString("UBICACION: "+ubigeo_general);
                                    mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
                                    texto_ubigeo_Capta.setText(mitextoU);
                                    imagen_ubigeo_Capta.setImageResource(R.mipmap.icon_update);
                                    GestionUbigeo.CAPTACION_UBIGEO.setEstado(true);

                                    Unidad_Territorial Departamento=new Unidad_Territorial();
                                    Departamento.setCodigo(jsonResponse.getInt("id_depa"));
                                    Departamento.setDescripcion(jsonResponse.getString("desc_depa"));
                                    Unidad_Territorial Provincia=new Unidad_Territorial();
                                    Provincia.setCodigo(jsonResponse.getInt("id_prov"));
                                    Provincia.setDescripcion(jsonResponse.getString("desc_prov"));
                                    Unidad_Territorial Distrito=new Unidad_Territorial();
                                    Distrito.setCodigo(jsonResponse.getInt("id_dist"));
                                    Distrito.setDescripcion(jsonResponse.getString("desc_dist"));

                                    GestionUbigeo.CAPTACION_UBIGEO.setDepartamento(Departamento);
                                    GestionUbigeo.CAPTACION_UBIGEO.setProvincia(Provincia);
                                    GestionUbigeo.CAPTACION_UBIGEO.setDistrito(Distrito);
                                    GestionUbigeo.CAPTACION_UBIGEO.setUbigeo_descripcion(ubigeo_general);



                                    System.out.println("PASO TRUE ESTADO MODULO 1");
                                }else{
                                    SpannableString mitextoU = new SpannableString("SELECCIONE UBICACION DE TRABAJO");
                                    mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
                                    texto_ubigeo_Capta.setText(mitextoU);
                                    imagen_ubigeo_Capta.setImageResource(R.mipmap.icon_next);
                                    GestionUbigeo.CAPTACION_UBIGEO.setEstado(false);

                                    System.out.println("PASO FALSE ESTADO MODULO 1");
                                }

                                break;
                            case 2:
                                if(estado==true){
                                    SpannableString mitextoU = new SpannableString("UBICACION: "+ubigeo_general);
                                    mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
                                    texto_ubigeo_Capta_masivo.setText(mitextoU);
                                    imagen_ubigeo_Capta_masivo.setImageResource(R.mipmap.icon_update);
                                    GestionUbigeo.CAPTACION_UBIGEO_MASIVO.setEstado(true);

                                    Unidad_Territorial Departamento=new Unidad_Territorial();
                                    Departamento.setCodigo(jsonResponse.getInt("id_depa"));
                                    Departamento.setDescripcion(jsonResponse.getString("desc_depa"));
                                    Unidad_Territorial Provincia=new Unidad_Territorial();
                                    Provincia.setCodigo(jsonResponse.getInt("id_prov"));
                                    Provincia.setDescripcion(jsonResponse.getString("desc_prov"));
                                    Unidad_Territorial Distrito=new Unidad_Territorial();
                                    Distrito.setCodigo(jsonResponse.getInt("id_dist"));
                                    Distrito.setDescripcion(jsonResponse.getString("desc_dist"));

                                    GestionUbigeo.CAPTACION_UBIGEO_MASIVO.setDepartamento(Departamento);
                                    GestionUbigeo.CAPTACION_UBIGEO_MASIVO.setProvincia(Provincia);
                                    GestionUbigeo.CAPTACION_UBIGEO_MASIVO.setDistrito(Distrito);
                                    GestionUbigeo.CAPTACION_UBIGEO_MASIVO.setUbigeo_descripcion(ubigeo_general);



                                    System.out.println("PASO TRUE ESTADO MODULO 2");
                                }else{
                                    SpannableString mitextoU = new SpannableString("SELECCIONE UBICACION DE TRABAJO");
                                    mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
                                    texto_ubigeo_Capta_masivo.setText(mitextoU);
                                    imagen_ubigeo_Capta_masivo.setImageResource(R.mipmap.icon_next);
                                    GestionUbigeo.CAPTACION_UBIGEO_MASIVO.setEstado(false);

                                    System.out.println("PASO FALSE ESTADO MODULO 2");
                                }

                                break;

                            case 3:
                                if(estado==true){
                                    SpannableString mitextoU = new SpannableString("UBICACION: "+ubigeo_general);
                                    mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
                                    texto_ubigeo_barrio.setText(mitextoU);
                                    imagen_ubigeo_barrio.setImageResource(R.mipmap.icon_update);
                                    GestionUbigeo.CAPTACION_UBIGEO_BARRIO.setEstado(true);

                                    Unidad_Territorial Departamento=new Unidad_Territorial();
                                    Departamento.setCodigo(jsonResponse.getInt("id_depa"));
                                    Departamento.setDescripcion(jsonResponse.getString("desc_depa"));
                                    Unidad_Territorial Provincia=new Unidad_Territorial();
                                    Provincia.setCodigo(jsonResponse.getInt("id_prov"));
                                    Provincia.setDescripcion(jsonResponse.getString("desc_prov"));
                                    Unidad_Territorial Distrito=new Unidad_Territorial();
                                    Distrito.setCodigo(jsonResponse.getInt("id_dist"));
                                    Distrito.setDescripcion(jsonResponse.getString("desc_dist"));

                                    GestionUbigeo.CAPTACION_UBIGEO_BARRIO.setDepartamento(Departamento);
                                    GestionUbigeo.CAPTACION_UBIGEO_BARRIO.setProvincia(Provincia);
                                    GestionUbigeo.CAPTACION_UBIGEO_BARRIO.setDistrito(Distrito);
                                    GestionUbigeo.CAPTACION_UBIGEO_BARRIO.setUbigeo_descripcion(ubigeo_general);



                                    System.out.println("PASO TRUE ESTADO MODULO 3");
                                }else{
                                    SpannableString mitextoU = new SpannableString("SELECCIONE UBICACION DE TRABAJO");
                                    mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
                                    texto_ubigeo_barrio.setText(mitextoU);
                                    imagen_ubigeo_barrio.setImageResource(R.mipmap.icon_next);
                                    GestionUbigeo.CAPTACION_UBIGEO_BARRIO.setEstado(false);

                                    System.out.println("PASO FALSE ESTADO MODULO 3");
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
