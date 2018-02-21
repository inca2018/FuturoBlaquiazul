package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterCampo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Campo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.ArrayList;
import java.util.List;

public class SeguimientoActivity extends AppCompatActivity {
    private RecyclerView recyclerCampo;
    private GridLayoutManager grid;
    private AdapterCampo adapterCampo;
    CardView guardar_seguimiento;
    int altura,ancho,alt2,anc2;

    LinearLayout linea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);
        recyclerCampo=findViewById(R.id.Recycler_seguimiento);
        guardar_seguimiento=findViewById(R.id.guardar_seguimiento);
        linea=findViewById(R.id.altura_campo);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels; // ancho absoluto en pixels
        int height = metrics.heightPixels; // alto absoluto en pixels
        altura=height;
        ancho=width;
        Usuario.SESION_ACTUAL.setAltura( height); //600
        Usuario.SESION_ACTUAL.setAncho(width); //1024

        recyclercampoooo();


        if(Campo.LISTACAMPO.size()==0){
            listar_card();
        }
    }

    private void recyclercampoooo() {

        grid = new GridLayoutManager(this,24);
        adapterCampo = new AdapterCampo(this,Campo.LISTACAMPO, new RecyclerViewOnItemClickListener(){
            @Override
            public void onClick(View v, int position) {
            }
        });

        recyclerCampo.setAdapter(adapterCampo);
        recyclerCampo.setLayoutManager(grid);
    }

    private void listar_card() {
        if(ancho>800){

            int alt=altura;
            int anc=ancho;
            alt2=alt/18;
            anc2=anc/24;
            linea.getLayoutParams().height=(alt-25);

            System.out.println("PANTALLA GRANDE");

            for(int i=0;i<408;i++){
                Campo temp=new Campo(i,"",0,alt2,anc2,R.drawable.layout_border);
                Campo.LISTACAMPO.add(temp);
            }

            adapterCampo.notifyDataSetChanged();
        }else{
            int alt=altura;
            int anc=ancho;
             alt2=alt/13;
             anc2=anc/24;
            linea.getLayoutParams().height=(alt-30);
            System.out.println("PANTALLA PEKE");
            for(int i=0;i<312;i++){
                Campo temp=new Campo(i,"",0,alt2+1,anc2,R.drawable.layout_border);
                Campo.LISTACAMPO.add(temp);
            }

            adapterCampo.notifyDataSetChanged();
        }

        System.out.println("Altura BASE:"+(altura-20));
        System.out.println("ANcho BASE:"+(ancho-20));

        System.out.println("Altura Card:"+alt2);
        System.out.println("ANcho Card:"+anc2);


    }
}
