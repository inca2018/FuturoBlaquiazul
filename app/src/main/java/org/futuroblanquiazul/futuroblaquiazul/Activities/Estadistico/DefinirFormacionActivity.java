package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.futuroblanquiazul.futuroblaquiazul.R;

public class DefinirFormacionActivity extends AppCompatActivity {

    LinearLayout base7,base8,base9,competetitiva;
    Spinner spinner_base;
    String[] lista_bases_nombres;
    Context context;
    int altura;
    int ancho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_formacion);
        spinner_base=findViewById(R.id.spinner_formacion);
        base7=findViewById(R.id.area_base7);
        base8=findViewById(R.id.area_base8);
        base9=findViewById(R.id.area_base9);
        competetitiva=findViewById(R.id.area_campetetiva);
        context=this;

       armar_Spinner();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels; // ancho absoluto en pixels
        int height = metrics.heightPixels; // alto absoluto en pixels
        altura=height;
        ancho=width;

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        debug("Ancho de la Pantalla " + Integer.toString(display.getWidth()));
        debug("Alto de la pantalla " + Integer.toString(display.getHeight()));
        debug("Densidad de la pantalla (dpi) " + getResources().getDisplayMetrics().densityDpi);

        System.out.println(" Altura :"+altura +"   ANCHO:"+ancho);

        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        debug("Escala " + Float.toString(getApplicationContext().getResources().getDisplayMetrics().density));

        // buscando los pixeles a partir de dips con la densidad
        int dips = 200;
        float pixelBoton = 0;
        float scaleDensity = 0;

        DisplayMetrics metrics2 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics2);

        switch(metrics2.densityDpi)
        {
            case DisplayMetrics.DENSITY_HIGH: //HDPI
                debug("Alta Densidad");
                scaleDensity = scale * 240;
                pixelBoton = dips * (scaleDensity / 240);
                break;
            case DisplayMetrics.DENSITY_MEDIUM: //MDPI
                debug("mediana Densidad");
                scaleDensity = scale * 160;
                pixelBoton = dips * (scaleDensity / 160);
                break;

            case DisplayMetrics.DENSITY_LOW:  //LDPI
                debug("baja Densidad");
                scaleDensity = scale * 120;
                pixelBoton = dips * (scaleDensity / 120);
                break;
        }
        debug(" CLASE : "+getClass().getSimpleName()+"  pixels:  "+Float.toString(pixelBoton));


    }

    private void armar_Spinner() {

        lista_bases_nombres=new String[5];
        lista_bases_nombres[0]="-- Seleccione Base --";
        lista_bases_nombres[1]=" Base 7 Jugadores";
        lista_bases_nombres[2]=" Base 8 Jugadores";
        lista_bases_nombres[3]=" Base 9 Jugadores";
        lista_bases_nombres[4]=" Base Competetiva(11 Jugadores)";

        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_bases_nombres);
        spinner_base.setAdapter(adapter_arr);

        spinner_base.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 switch (position){
                     case 0:
                       base7.setVisibility(View.GONE);
                       base8.setVisibility(View.GONE);
                       base9.setVisibility(View.GONE);
                       competetitiva.setVisibility(View.GONE);

                         break;
                     case 1:
                         base7.setVisibility(View.VISIBLE);
                         base8.setVisibility(View.GONE);
                         base9.setVisibility(View.GONE);
                         competetitiva.setVisibility(View.GONE);
                         break;
                     case 2:
                         base7.setVisibility(View.GONE);
                         base8.setVisibility(View.VISIBLE);
                         base9.setVisibility(View.GONE);
                         competetitiva.setVisibility(View.GONE);
                         break;
                     case 3:
                         base7.setVisibility(View.GONE);
                         base8.setVisibility(View.GONE);
                         base9.setVisibility(View.VISIBLE);
                         competetitiva.setVisibility(View.GONE);
                         break;
                     case 4:
                         base7.setVisibility(View.GONE);
                         base8.setVisibility(View.GONE);
                         base9.setVisibility(View.GONE);
                         competetitiva.setVisibility(View.VISIBLE);
                         break;
                 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void debug(String d){
        System.out.println(d);
    }
}
