package org.futuroblanquiazul.futuroblaquiazul.Activities.CaptacionMasiva;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaPersonaSeguimientoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Fragments_Resultados.Tab1;
import org.futuroblanquiazul.futuroblaquiazul.Fragments_Resultados.Tab2;
import org.futuroblanquiazul.futuroblaquiazul.Fragments_Resultados.Tab3;
import org.futuroblanquiazul.futuroblaquiazul.Fragments_Resultados.Tab4;
import org.futuroblanquiazul.futuroblaquiazul.Fragments_Resultados.Tab5;
import org.futuroblanquiazul.futuroblaquiazul.Fragments_Resultados.Tab6;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.ResultadosDiagnostico;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class MasivoResultadosActivity extends AppCompatActivity{
    TabLayout tabLayout;
    ViewPager viewPager;
    Context context;
    TextView resultado_persona;
    TextView resultado_ubigeo;
    ImageView foto_resultados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_masivo_resultados);
        resultado_persona=findViewById(R.id.Tabs_resultados);
        resultado_ubigeo=findViewById(R.id.Tabs_resultados2);
        tabLayout=(TabLayout)findViewById(R.id.TabLayout);
        viewPager=findViewById(R.id.pager);
        tabLayout.setupWithViewPager(viewPager);
        foto_resultados=findViewById(R.id.foto_resultados);
        SetupViewPager(viewPager);

        if(Usuario.SESION_ACTUAL.getPersona_seguimiento()!=null){
            resultado_persona.setText(Usuario.SESION_ACTUAL.getPersona_seguimiento().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_seguimiento().getApellidos_Persona());
            int totalito= ResultadosDiagnostico.RESULTADO_TEMP.getTotal_fisico()+ResultadosDiagnostico.RESULTADO_TEMP.getTotal_capacidad()+ResultadosDiagnostico.RESULTADO_TEMP.getTotal_social()+ResultadosDiagnostico.RESULTADO_TEMP.getTotal_tecnico()+ResultadosDiagnostico.RESULTADO_TEMP.getTotal_psico();
            resultado_ubigeo.setText("TOTAL: "+ totalito+" Ptos.");

            Glide.with(context)
                    .load(Usuario.SESION_ACTUAL.getPersona_seguimiento().getFoto())
                    .error(R.drawable.user_default)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(foto_resultados);
        }else{
            if(Persona.PERSONA_TEMP.getId()!=0){
                resultado_persona.setText(Persona.PERSONA_TEMP.getNombre_Persona()+" "+Persona.PERSONA_TEMP.getApellidos_Persona());
                int totalito= ResultadosDiagnostico.RESULTADO_TEMP.getTotal_fisico()+ResultadosDiagnostico.RESULTADO_TEMP.getTotal_capacidad()+ResultadosDiagnostico.RESULTADO_TEMP.getTotal_social()+ResultadosDiagnostico.RESULTADO_TEMP.getTotal_tecnico()+ResultadosDiagnostico.RESULTADO_TEMP.getTotal_psico();
                resultado_ubigeo.setText("TOTAL: "+totalito+" Ptos.");
            }else{
                resultado_persona.setText("SIN DATOS");
                resultado_ubigeo.setText("SIN DATOS DE TOTAL");
            }
        }
    }
    public void SetupViewPager(ViewPager viewPager){
        MyViewPageAdapter myViewPageAdapter=new MyViewPageAdapter(getSupportFragmentManager());
        myViewPageAdapter.AddPageFragment(new Tab1(),"Generales");
        myViewPageAdapter.AddPageFragment(new Tab2(),"Fisico");
        myViewPageAdapter.AddPageFragment(new Tab3(),"Capacidad");
        myViewPageAdapter.AddPageFragment(new Tab4(),"Social");
        myViewPageAdapter.AddPageFragment(new Tab5(),"Tecnico");
        myViewPageAdapter.AddPageFragment(new Tab6(),"Psicologico");

        viewPager.setAdapter(myViewPageAdapter);
    }

    public class MyViewPageAdapter extends FragmentPagerAdapter{
       private List<Fragment> myFragment= new ArrayList<Fragment>();
       private List<String>   myTitle= new ArrayList<>();

       public MyViewPageAdapter(FragmentManager manager){
           super(manager);

       }

       public void  AddPageFragment(Fragment Frag,String title){
            myFragment.add(Frag);
            myTitle.add(title);
       }


       @Override
       public CharSequence getPageTitle(int position){
           return myTitle.get(position);
       }

        @Override
        public Fragment getItem(int position) {
            return myFragment.get(position);
        }

        @Override
        public int getCount() {
            return 6;
        }
    }


    public void onBackPressed() {
        if(Usuario.SESION_ACTUAL.getPersona_seguimiento()!=null){
            Usuario.SESION_ACTUAL.setPersona_seguimiento(null);
            ResultadosDiagnostico.RESULTADO_TEMP.setFISICO(null);
            ResultadosDiagnostico.RESULTADO_TEMP.setSOCIAL(null);
            ResultadosDiagnostico.RESULTADO_TEMP.setTECNICO(null);
            ResultadosDiagnostico.RESULTADO_TEMP.setPSICO(null);
            ResultadosDiagnostico.RESULTADO_TEMP.setCAPACIDAD(null);

            ResultadosDiagnostico.RESULTADO_TEMP.setFecha_registro(null);
            ResultadosDiagnostico.RESULTADO_TEMP.setSugerido1(0);
            ResultadosDiagnostico.RESULTADO_TEMP.setSugerido2(0);
            ResultadosDiagnostico.RESULTADO_TEMP.setSugerid3(0);
            ResultadosDiagnostico.RESULTADO_TEMP.setLaterlaidad(null);
            ResultadosDiagnostico.RESULTADO_TEMP.setTotal_fisico(0);
            ResultadosDiagnostico.RESULTADO_TEMP.setTotal_capacidad(0);
            ResultadosDiagnostico.RESULTADO_TEMP.setTotal_social(0);
            ResultadosDiagnostico.RESULTADO_TEMP.setTotal_tecnico(0);
            ResultadosDiagnostico.RESULTADO_TEMP.setTotal_psico(0);
            ResultadosDiagnostico.RESULTADO_TEMP.setNombre_Scout(null);
            ResultadosDiagnostico.RESULTADO_TEMP.setSugeridos(null);

            Intent intent = new Intent(MasivoResultadosActivity.this,ListaPersonaSeguimientoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            MasivoResultadosActivity.this.startActivity(intent);
        }else{
            Intent intent = new Intent(MasivoResultadosActivity.this,ListaPersonaMasivoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            MasivoResultadosActivity.this.startActivity(intent);
        }
    }
}
