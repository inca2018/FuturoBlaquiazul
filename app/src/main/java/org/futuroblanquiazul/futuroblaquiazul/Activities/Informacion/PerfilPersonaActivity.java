package org.futuroblanquiazul.futuroblaquiazul.Activities.Informacion;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.LoginActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Area_Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Perfil;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarResultados1;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarResultados2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarResultadosDiagnostico;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Info;
import org.json.JSONException;
import org.json.JSONObject;

import static org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario.SESION_ACTUAL;

public class PerfilPersonaActivity extends AppCompatActivity {


    ImageView foto;
    TextView nombre;
    TextView v1,v2,v3;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilpersona);

        context=this;

        foto=findViewById(R.id.perfil1_foto);
        nombre=findViewById(R.id.perfil1_nombre);
        v1=findViewById(R.id.perfilx_v1);
        v2=findViewById(R.id.perfilx_v2);
        v3=findViewById(R.id.perfilx_v3);



        if(Info.GESTOR.getPersonal_Temporal()!=null){
            Glide.with(context)
                    .load(Info.GESTOR.getPersonal_Temporal().getFoto())
                    .error(R.drawable.user_default)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(foto);

            nombre.setText(Info.GESTOR.getPersonal_Temporal().getNombre_Persona()+" "+Info.GESTOR.getPersonal_Temporal().getApellidos_Persona());
            Recuperar_Resultados(Info.GESTOR.getPersonal_Temporal().getId(),context);
        }


    }


    private void Recuperar_Resultados(int id,final Context context) {
        String id_persona=String.valueOf(id);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {


                        String to1=jsonResponse.getString("V1");
                        String to2=jsonResponse.getString("V2");
                        String to3=jsonResponse.getString("V3");

                        if(String.valueOf(to1).length()!=0){
                            v1.setText(String.valueOf(to1));
                        }else if(to1==null){
                            v1.setText("0");
                        }

                        if(String.valueOf(to2).length()!=0){
                            v2.setText(String.valueOf(to2));
                        }else if(to2==null){
                            v2.setText("0");
                        }


                        if(String.valueOf(to3).length()!=0){
                            v3.setText(String.valueOf(to3));
                        }else if (to3==null){
                            v3.setText("0");
                        }


                        Toast.makeText(context, "Resultados Recuperados", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        RecuperarResultados2 xx = new RecuperarResultados2(id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
}
