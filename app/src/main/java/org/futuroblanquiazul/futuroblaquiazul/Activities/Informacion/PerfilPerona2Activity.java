package org.futuroblanquiazul.futuroblaquiazul.Activities.Informacion;

import android.content.Context;
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

import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarResultados1;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Info;
import org.json.JSONException;
import org.json.JSONObject;

public class PerfilPerona2Activity extends AppCompatActivity {

    ImageView foto;
    TextView nombre;
    TextView v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_perona2);
        context=this;

        foto=findViewById(R.id.perfil2_foto);
        nombre=findViewById(R.id.perfil2_nombre);
        v1=findViewById(R.id.perfil_v1);
        v2=findViewById(R.id.perfil_v2);
        v3=findViewById(R.id.perfil_v3);
        v4=findViewById(R.id.perfil_v4);
        v5=findViewById(R.id.perfil_v5);
        v6=findViewById(R.id.perfil_v6);
        v7=findViewById(R.id.perfil_v7);
        v8=findViewById(R.id.perfil_v8);
        v9=findViewById(R.id.perfil_v9);
        v10=findViewById(R.id.perfil_v10);
        v11=findViewById(R.id.perfil_v11);
        v12=findViewById(R.id.perfil_v12);
        v13=findViewById(R.id.perfil_v13);
        v14=findViewById(R.id.perfil_v14);


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

                        double a1=jsonResponse.getDouble("V1");
                        double a2=jsonResponse.getDouble("V2");
                        double a3=jsonResponse.getDouble("V3");
                        double a4=jsonResponse.getDouble("V4");
                        double a5=jsonResponse.getDouble("V5");
                        double a6=jsonResponse.getDouble("V6");
                        double a7=jsonResponse.getDouble("V7");
                        double a8=jsonResponse.getDouble("V8");
                        double a9=jsonResponse.getDouble("V9");
                        double a10=jsonResponse.getDouble("V10");
                        double a11=jsonResponse.getDouble("V11");
                        double a12=jsonResponse.getDouble("V12");
                        double a13=jsonResponse.getDouble("V13");
                        double a14=jsonResponse.getDouble("V14");

                        Setear(a1,v1);
                        Setear(a2,v2);
                        Setear(a3,v3);
                        Setear(a4,v4);
                        Setear(a5,v5);
                        Setear(a6,v6);
                        Setear(a7,v7);
                        Setear(a8,v8);
                        Setear(a9,v9);
                        Setear(a10,v10);
                        Setear(a11,v11);
                        Setear(a12,v12);
                        Setear(a13,v13);
                        Setear(a14,v14);




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

        RecuperarResultados1 xx = new RecuperarResultados1(id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    private void Setear(double a1, TextView v1) {

        if(a1>0){
            v1.setText(String.valueOf(a1));
        }else{
            v1.setText("0");
        }
    }
}
