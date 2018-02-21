package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.AgregarPersonaEnMasivo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPersonaRecuperarCodigo;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Registro_Postulante_Masivo;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.futuroblanquiazul.futuroblaquiazul.ActivityEntity.modulo_captacion.BASE;


public class PersonaNuevoMasivoActivity extends AppCompatActivity {

    public ImageButton btn1;
    public TextView f_naci;
    Calendar dateTime = Calendar.getInstance();
    Button guardar;
    List<String> ResultadosRegistro;
    Context context;
    int id_persona;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masivo_persona_registro_activity);
        guardar=findViewById(R.id.guardar_persona2);
        context=this;
        ResultadosRegistro=new ArrayList<>();
        Reiniciar_Array();
        f_naci=findViewById(R.id.capta_fecha_nacimiento2);
        btn1=findViewById(R.id.d_fecha_nacimiento2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });
        Recuperar_Variables();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean estado;
                estado=Verificar_Vacios();

                if(estado==true){

                    for(int i = 0; i< Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.size(); i++){
                        ResultadosRegistro.add(Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).getValor());
                    }


                    Guardar_Persona(ResultadosRegistro,context);

                }else{
                    String mensaje=Armar_Mensaje();
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Guardar_Persona(final List<String> resultadosRegistro,final  Context context) {
        String Nombres=resultadosRegistro.get(0).toUpperCase().trim();
        String Apellidos=resultadosRegistro.get(1).toUpperCase().trim();
        String Nacionalidad=resultadosRegistro.get(2).toUpperCase().trim();
        String Club=resultadosRegistro.get(3).toUpperCase().trim();
        String Liga=resultadosRegistro.get(4).toUpperCase().trim();
        String Categoria=resultadosRegistro.get(5).toUpperCase().trim();
        String Dni=resultadosRegistro.get(6).toUpperCase().trim();
        String FechaNacimiento=resultadosRegistro.get(7).toUpperCase().trim();
        String LugarResidencia=resultadosRegistro.get(8).toUpperCase().trim();
        String Telefonos=resultadosRegistro.get(9).toUpperCase().trim();
        String Correo=resultadosRegistro.get(10).toUpperCase().trim();
        String Apoderado=resultadosRegistro.get(11).toUpperCase().trim();
        String TelefonoApoderado=resultadosRegistro.get(12).toUpperCase().trim();
        String Estado=String.valueOf(2);
        String estado_capta=String.valueOf(1);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Registro");
        progressDialog.setMessage("Enviando Información...");
        progressDialog.show();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        id_persona=jsonResponse.getInt("id_persona");
                        Usuario.SESION_ACTUAL.setId_persona(id_persona);
                        debug("ID_PERSONA_RECUPERADO:"+id_persona);
                        BASE.setId_persona(id_persona);

                        if(id_persona==0){
                            progressDialog.dismiss();
                            Toast.makeText(context, "Problema de Conexión al Recuperar Codigo Persona", Toast.LENGTH_SHORT).show();
                        }else{

                            Registrar_Masivo_Persona(context);

                            debug("PASO REGISTRO DE PERSONA");

                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en Recupera codigo de usuario :"+e);
                }
            }
        };

        RegistrarPersonaRecuperarCodigo xx = new RegistrarPersonaRecuperarCodigo(Nombres, Apellidos,Nacionalidad,Club, Liga,Categoria,Dni,FechaNacimiento,LugarResidencia,Telefonos,Correo,Apoderado,TelefonoApoderado,Estado,estado_capta,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Registrar_Masivo_Persona(final Context context) {

        String id_masivo=String.valueOf(Usuario.SESION_ACTUAL.getId_masivo());
        String id_persona=String.valueOf(Usuario.SESION_ACTUAL.getId_persona());
        String id_user=String.valueOf(Usuario.SESION_ACTUAL.getId());

        debug("ID_MASIVO:"+id_masivo);
        debug("ID_PERSONA: "+id_persona);
        debug("ID_USER:"+id_user);
        debug("----------------------------------------");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    debug("RESPONSE: "+response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Registro Con exito", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(PersonaNuevoMasivoActivity.this, ListaPersonaMasivoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PersonaNuevoMasivoActivity.this.startActivity(intent);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en Recupera  guardar registro enlance :"+e);
                }
            }
        };

        AgregarPersonaEnMasivo xx = new AgregarPersonaEnMasivo(id_masivo,id_persona,id_user,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
    private void Recuperar_Variables() {
        for(int i = 0; i< Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.size(); i++){
            EditText temp=findViewById(Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).getRecurso());
            Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).setCampoEditText(temp);
        }
    }
    private void updateDate(){
        new DatePickerDialog(this, d1,1999,0,1).show();
    }
    private void updateTextLabel1(){
        String meso="";
        String diao="";
        int mes =dateTime.get(Calendar.MONTH)+1;
        int dia=dateTime.get(Calendar.DAY_OF_MONTH);
        int anoo=dateTime.get(Calendar.YEAR);
        if(mes<10){
            meso="0"+mes;
        }else{
            meso=""+mes;
        }
        if(dia<10){
            diao="0"+dia;
        }else{
            diao=""+dia;
        }

        String fecha_nacimiento=anoo+"-"+meso+"-"+diao;
        f_naci.setText(fecha_nacimiento);


        for(int i=0;i<Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.size();i++){
            if(Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).getId()==7){
                Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).setValor(fecha_nacimiento);
            }
        }
    }
    private String Armar_Mensaje() {
        String temp="";

        for(int i=0;i<Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.size();i++){
            if(Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).isEstado()==false){
                temp=temp+Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).getMensaje_Vacio()+"\n";
            }
        }


        return temp;
    }
    private Boolean Verificar_Vacios() {
        Boolean t=true;

        for(int i=0;i<Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.size();i++){
            String texto_temp=Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).getCampoEditText().getText().toString().trim();
            Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).setValor(texto_temp);
            if(texto_temp.length()!=0){
                Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).setEstado(true);
            }else if(texto_temp.length()==0){
                Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).setEstado(false);
            }
        }

        for(int i=0;i<Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.size();i++){
            if(Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).isEstado()==false){
                t=false;
            }
        }

        return t;
    }
    DatePickerDialog.OnDateSetListener d1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextLabel1();
        }
    };
    private void Reiniciar_Array(){
        for(int i=0;i<Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.size();i++){
            Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).setCampoEditText(null);
            Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).setValor("");
            Recursos_Registro_Postulante_Masivo.LISTA_REGISTRO.get(i).setEstado(false);
        }
    }

    void debug(String mensaje){
        System.out.println(mensaje);
    }


    public void onBackPressed() {

        Intent intent = new Intent(PersonaNuevoMasivoActivity.this,ListaPersonaMasivoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PersonaNuevoMasivoActivity.this.startActivity(intent);
        finish();


    }

}
