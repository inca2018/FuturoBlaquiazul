package org.futuroblanquiazul.futuroblaquiazul.Activities.CaptacionMasiva;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
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
        SetupViewPager(viewPager);

        if(Persona.PERSONA_TEMP.getId()!=0){
            resultado_persona.setText(Persona.PERSONA_TEMP.getNombre_Persona()+" "+Persona.PERSONA_TEMP.getApellidos_Persona());
             int totalito= ResultadosDiagnostico.RESULTADO_TEMP.getTotal_fisico()+ResultadosDiagnostico.RESULTADO_TEMP.getTotal_capacidad()+ResultadosDiagnostico.RESULTADO_TEMP.getTotal_social()+ResultadosDiagnostico.RESULTADO_TEMP.getTotal_tecnico()+ResultadosDiagnostico.RESULTADO_TEMP.getTotal_psico();
            resultado_ubigeo.setText(totalito+" Ptos.");
        }else{
            resultado_persona.setText("SIN DATOS");
            resultado_ubigeo.setText("SIN DATOS DE TOTAL");
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

}
