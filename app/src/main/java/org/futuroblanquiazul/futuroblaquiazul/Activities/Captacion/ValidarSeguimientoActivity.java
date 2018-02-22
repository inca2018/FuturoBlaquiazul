package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Seguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarResultadosDiagnosticoSeguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarSeguimientos;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ValidarSeguimientoActivity extends AppCompatActivity {


    TextView Nombres_Persona,ubigeo,resu_diag,resu_campo,resu_goles,resu_tiempo,resu_total;
    CardView confirmacion_resultados_seguimiento;
    ProgressDialog progressDialog;
    int id_social,id_psico;
    Context context;

    List<Integer> Resultados_Diagnostico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_seguimiento);
        Resultados_Diagnostico=new ArrayList<>();
        context=this;

        Nombres_Persona=findViewById(R.id.resultado_persona_seguimiento);
        ubigeo=findViewById(R.id.resultado_ubigeo_seguimiento);
        resu_diag=findViewById(R.id.resultado_total_Diagnostico);
        resu_campo=findViewById(R.id.resultado_total_campo);
        resu_goles=findViewById(R.id.resultado_cant_goles);
        resu_tiempo=findViewById(R.id.resultado_tiempo_jugado);
        resu_total=findViewById(R.id.resultado_total_seguimiento);
        confirmacion_resultados_seguimiento=findViewById(R.id.confirmacion_resultados_seguimiento);

        if(Usuario.SESION_ACTUAL.getPersona_seguimiento().getNombre_Persona().length()!=0 && Usuario.SESION_ACTUAL.getPersona_seguimiento().getApellidos_Persona().length()!=0){
            String nombre_completo=Usuario.SESION_ACTUAL.getPersona_seguimiento().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_seguimiento().getApellidos_Persona();
            Nombres_Persona.setText(nombre_completo);
        }else{
            Nombres_Persona.setText("Nombres no disponible");
        }


        if(GestionUbigeo.CAPTACION_UBIGEO.getUbigeo_descripcion().length()!=0){
            ubigeo.setText(GestionUbigeo.CAPTACION_UBIGEO.getUbigeo_descripcion());
        }else{
            ubigeo.setText("Ubicación no disponible");
        }

        if(Recuperar_Diagnostico()!=0){
            resu_diag.setText(Recuperar_Diagnostico()+" Ptos.");
        }else{
            resu_diag.setText("No Disponible");
        }

        int PB=Seguimiento.SEGUIMIENTO.getCantidad_Pierde_Balon()*-1;
        int PG=Seguimiento.SEGUIMIENTO.getCantidad_Pase_Gol()*1;
        int DR=Seguimiento.SEGUIMIENTO.getCantidad_Dribbling()*1;
        int R=Seguimiento.SEGUIMIENTO.getCantidad_Recupera_Balon()*1;
        int goles=Seguimiento.SEGUIMIENTO.getGoles()*3;

        Seguimiento.SEGUIMIENTO.setTotal_Puntaje(PB+PG+DR+R+goles);


        if(Seguimiento.SEGUIMIENTO.getTotal_Puntaje()!=0){
            resu_campo.setText(Seguimiento.SEGUIMIENTO.getTotal_Puntaje()+" Ptos.");
        }else{
            resu_campo.setText("No Disponible");
        }

        if(Seguimiento.SEGUIMIENTO.getGoles()!=0){
            resu_goles.setText(Seguimiento.SEGUIMIENTO.getGoles()+" Goles");
        }else{
            resu_goles.setText("No Disponible");
        }


        if(Seguimiento.SEGUIMIENTO.getMinutos_Juego()!=0){
            resu_tiempo.setText(Seguimiento.SEGUIMIENTO.getMinutos_Juego()+" Minutos");
        }else{
            resu_tiempo.setText("No Disponible");
        }

        if(Seguimiento.SEGUIMIENTO.getMinutos_Juego()!=0){
            resu_tiempo.setText(Seguimiento.SEGUIMIENTO.getMinutos_Juego()+" Minutos");
        }else{
            resu_tiempo.setText("No Disponible");
        }
        Mostrar_Resultados();
        Calcular_Resultado();
        Mostrar_Resultados();


       if(Seguimiento.SEGUIMIENTO.getTotal_Puntaje()!=0){
           resu_total.setText(String.valueOf(Seguimiento.SEGUIMIENTO.getTotal_Puntaje()));
       }else{
           resu_total.setText("No Disponible");
       }
        confirmacion_resultados_seguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guardar_Seguimiento();
            }
        });
    }
    private void Guardar_Seguimiento() {

        Registrar_Diagnosticos(Resultados_Diagnostico,context);

    }
    private void Calcular_Resultado() {



        if(Seguimiento.SEGUIMIENTO.getTitular()==0){
            //nada
        }else if(Seguimiento.SEGUIMIENTO.getTitular()==1){
            int total=Seguimiento.SEGUIMIENTO.getTotal_Puntaje();
            Seguimiento.SEGUIMIENTO.setTotal_Puntaje(total+2);
        }

        if(Seguimiento.SEGUIMIENTO.getCapitan()==0){

        }else if(Seguimiento.SEGUIMIENTO.getCapitan()==1){
            int total=Seguimiento.SEGUIMIENTO.getTotal_Puntaje();
            Seguimiento.SEGUIMIENTO.setTotal_Puntaje(total+2);
        }

        if(Seguimiento.SEGUIMIENTO.getMinutos_Juego()!=0){
            int total=Seguimiento.SEGUIMIENTO.getTotal_Puntaje();
            int ma=(Seguimiento.SEGUIMIENTO.getMinutos_Juego()/2)+total;
            Seguimiento.SEGUIMIENTO.setTotal_Puntaje(ma);
        }else{

        }


        int suma_diag=Recuperar_Diagnostico();


        int total_general=Seguimiento.SEGUIMIENTO.getTotal_Puntaje()+suma_diag;
        Seguimiento.SEGUIMIENTO.setTotal_Puntaje(total_general);
        Unidad_Territorial depa=new Unidad_Territorial();
        depa.setCodigo(GestionUbigeo.CAPTACION_UBIGEO.getDepartamento().getCodigo());
        Unidad_Territorial prov=new Unidad_Territorial();
        prov.setCodigo(GestionUbigeo.CAPTACION_UBIGEO.getProvincia().getCodigo());
        Unidad_Territorial dist=new Unidad_Territorial();
        dist.setCodigo(GestionUbigeo.CAPTACION_UBIGEO.getDistrito().getCodigo());

        Seguimiento.SEGUIMIENTO.setDepartamento(depa);
        Seguimiento.SEGUIMIENTO.setProvincia(prov);
        Seguimiento.SEGUIMIENTO.setDistrito(dist);

        for(int i=0;i<Recursos_Diagnostico.LISTA_SOCIAL2.size();i++){
            Resultados_Diagnostico.add(Recursos_Diagnostico.LISTA_SOCIAL2.get(i).getResultado());
        }
        for(int i=0;i<Recursos_Diagnostico.LISTA_PSICO2.size();i++){
            Resultados_Diagnostico.add(Recursos_Diagnostico.LISTA_PSICO2.get(i).getResultado());
        }

    }
    private int Recuperar_Diagnostico() {
        int total;

        int suma=0;
        for(int i = 0; i< Recursos_Diagnostico.LISTA_SOCIAL2.size(); i++){
            suma=suma+Recursos_Diagnostico.LISTA_SOCIAL2.get(i).getResultado();
        }
        int suma2=0;
        for(int i=0;i< Recursos_Diagnostico.LISTA_PSICO2.size();i++){
            suma=suma+Recursos_Diagnostico.LISTA_PSICO2.get(i).getResultado();
        }

        total=suma+suma2;

        return total;
    }
    private void Registrar_Diagnosticos(final List<Integer> lista, final Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Seguimiento");
        progressDialog.setMessage("Registrando Información...");
        progressDialog.show();

        String s1=String.valueOf(lista.get(0));
        String s2=String.valueOf(lista.get(1));
        String s3=String.valueOf(lista.get(2));
        String s4=String.valueOf(lista.get(3));
        String p1=String.valueOf(lista.get(4));
        String p2=String.valueOf(lista.get(5));
        String p3=String.valueOf(lista.get(6));
        String p4=String.valueOf(lista.get(7));
        String id_persona=String.valueOf(Usuario.SESION_ACTUAL.getPersona_seguimiento().getId());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        id_social=jsonResponse.getInt("id_social");
                        id_psico=jsonResponse.getInt("id_psico");
                        Seguimiento.SEGUIMIENTO.setId_campo_psico(id_psico);
                        Seguimiento.SEGUIMIENTO.setId_campo_social(id_social);

                        Registrar_Modulo_Seguimiento(Seguimiento.SEGUIMIENTO,Usuario.SESION_ACTUAL.getPersona_seguimiento(),context);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion pruebas", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en json pruebas :"+e);
                }
            }
        };

        RegistrarResultadosDiagnosticoSeguimiento xx = new RegistrarResultadosDiagnosticoSeguimiento(s1,s2,s3,s4,p1,p2,p3,p4,id_persona,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
    private void Registrar_Modulo_Seguimiento(final Seguimiento seguimiento, final Persona temp, final Context context) {

        String id_user=String.valueOf(Usuario.SESION_ACTUAL.getId());
        String id_persona=String.valueOf(temp.getId());
        String id_social=String.valueOf(seguimiento.getId_campo_social());
        String id_psico=String.valueOf(seguimiento.getId_campo_psico());
        String titular=String.valueOf(seguimiento.getTitular());
        String capitan=String.valueOf(seguimiento.getCapitan());
        String departamento=String.valueOf(seguimiento.getDepartamento().getCodigo());
        String provincia=String.valueOf(seguimiento.getProvincia().getCodigo());
        String distrito=String.valueOf(seguimiento.getDistrito().getCodigo());
        String nom_competenia=String.valueOf(seguimiento.getNombre_Competencia());
        String nom_rival=String.valueOf(seguimiento.getNombre_Rival());
        String minutos=String.valueOf(seguimiento.getMinutos_Juego());
        String cant_1=String.valueOf(seguimiento.getCantidad_Pierde_Balon());
        String cant_2=String.valueOf(seguimiento.getCantidad_Recupera_Balon());
        String cant_3=String.valueOf(seguimiento.getCantidad_Pase_Gol());
        String cant_4=String.valueOf(seguimiento.getCantidad_Dribbling());
        String total_puntaje=String.valueOf(seguimiento.getTotal_Puntaje());
        String estado=String.valueOf(1);
        String goles=String.valueOf(seguimiento.getGoles());

        Response.Listener<String>

                responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        progressDialog.dismiss();
                        Seguimiento.SEGUIMIENTO.Vaciar_Datos();

                        for(int i=0;i<Recursos_Diagnostico.LISTA_SOCIAL2.size();i++){
                            Recursos_Diagnostico.LISTA_SOCIAL2.get(i).setResultado(0);
                        }

                        for(int i=0;i<Recursos_Diagnostico.LISTA_PSICO2.size();i++){
                            Recursos_Diagnostico.LISTA_PSICO2.get(i).setResultado(0);
                        }



                        Toast.makeText(context, "Registro de Seguimiento Exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ValidarSeguimientoActivity.this,ListaSeguimientosActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ValidarSeguimientoActivity.this.startActivity(intent);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion seguimientos", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en json seguimientos :"+e);
                }
            }
        };

        RegistrarSeguimientos xx = new RegistrarSeguimientos(id_persona,id_user,id_social,
                id_psico,titular,capitan,departamento,provincia,distrito,nom_competenia,
                nom_rival,minutos,cant_1,cant_2,cant_3,cant_4,total_puntaje,estado,goles,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    public void debug(String sm){
        System.out.println(sm);
    }
    public void Mostrar_Resultados(){
        debug("------------------------------------------------------------------");
        debug("TOTAL_PARCIAL"+Seguimiento.SEGUIMIENTO.getTotal_Puntaje());
        debug("NUM_PASE GOL"+Seguimiento.SEGUIMIENTO.getCantidad_Pase_Gol());
        debug("NUM_DRIBBLING"+Seguimiento.SEGUIMIENTO.getCantidad_Dribbling());
        debug("PIERDE BALON"+Seguimiento.SEGUIMIENTO.getCantidad_Pierde_Balon());
        debug("RECUPERA BALON"+Seguimiento.SEGUIMIENTO.getCantidad_Recupera_Balon());

        debug("CANT GOLES"+Seguimiento.SEGUIMIENTO.getGoles());

        debug("TIEMPO JUGADO"+Seguimiento.SEGUIMIENTO.getMinutos_Juego());
        debug("TITULAR"+Seguimiento.SEGUIMIENTO.getTitular());
        debug("CAPITAN"+Seguimiento.SEGUIMIENTO.getCapitan());
        debug("------------------------------------------------------------------");

        debug("TOTAL_GENERAL"+Seguimiento.SEGUIMIENTO.getTotal_Puntaje());


        debug("------------------------------------------------------------------");

    }


}
