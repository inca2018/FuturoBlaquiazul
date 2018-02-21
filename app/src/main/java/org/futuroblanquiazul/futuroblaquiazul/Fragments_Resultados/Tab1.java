package org.futuroblanquiazul.futuroblaquiazul.Fragments_Resultados;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.ResultadosDiagnostico;

import java.util.ArrayList;

public class Tab1 extends Fragment {

    Context context;
    private RadarChart mChart;
    Typeface mTfRegular;
    Typeface mTfLight;
    TextView scout,sugeridos,lateralidad;
    public Tab1() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_tab1, container, false);
        scout=v.findViewById(R.id.tab1_nombre_scout);
        sugeridos=v.findViewById(R.id.tab1_posiciones_sugeridas);
        lateralidad=v.findViewById(R.id.tab1_lateralidad);

        mChart =v.findViewById(R.id.chart1);
        mChart.setBackgroundColor(getResources().getColor(R.color.blanco));
        mChart.getDescription().setEnabled(false);

        mChart.setWebLineWidth(1f);
        mChart.setWebColor(getResources().getColor(R.color.verde_bajo));
        mChart.setWebLineWidthInner(1f);
        mChart.setWebColorInner(getResources().getColor(R.color.deep_naranja900));
        mChart.setWebAlpha(100);


        setData();

        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"FISICO", "CAPACIDAD", "SOCIAL", "TECNICO", "PSICOLOGICO"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(getResources().getColor(R.color.colorPrimary));
        xAxis.setTextSize(12f);

        YAxis yAxis = mChart.getYAxis();
        yAxis.setTypeface(mTfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(25f);
        //Numeros de gradis
        yAxis.setDrawLabels(false);

       Legend l = mChart.getLegend();
        //l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setTypeface(mTfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(getResources().getColor(R.color.titulos));
        l.setTextSize(20f);

        return v ;
    }


    public void setData() {


        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();


        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        float total_fisico= ResultadosDiagnostico.RESULTADO_TEMP.getTotal_fisico();
        float total_capacidad=ResultadosDiagnostico.RESULTADO_TEMP.getTotal_capacidad();
        float total_social=ResultadosDiagnostico.RESULTADO_TEMP.getTotal_social();
        float total_tecnico=ResultadosDiagnostico.RESULTADO_TEMP.getTotal_tecnico();
        float total_psico=ResultadosDiagnostico.RESULTADO_TEMP.getTotal_psico();

        scout.setText(ResultadosDiagnostico.RESULTADO_TEMP.getNombre_Scout());
        lateralidad.setText(ResultadosDiagnostico.RESULTADO_TEMP.getLaterlaidad());
        String vacio="";
        for(int i=0;i<ResultadosDiagnostico.RESULTADO_TEMP.getSugeridos().size();i++){
            vacio=vacio+" - "+ResultadosDiagnostico.RESULTADO_TEMP.getSugeridos().get(i)+"\n";
        }

        if(vacio.length()!=0){
            sugeridos.setText(vacio);
        }else{
            sugeridos.setText("Posiciones Sugeridas no Registradas");
        }

        entries1.add(new RadarEntry(total_fisico));
        entries1.add(new RadarEntry(total_capacidad));
        entries1.add(new RadarEntry(total_social));
        entries1.add(new RadarEntry(total_tecnico));
        entries1.add(new RadarEntry(total_psico));

        RadarDataSet set1 = new RadarDataSet(entries1, "Diagnostico");
        set1.setColor(getResources().getColor(R.color.blue));
        set1.setFillColor(getResources().getColor(R.color.n));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);


        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);


        RadarData data = new RadarData(sets);
        data.setValueTypeface(mTfLight);
        data.setValueTextSize(15f);
        data.setDrawValues(true);
        data.setValueTextColor(getResources().getColor(R.color.blue));

        mChart.setData(data);
        mChart.invalidate();
    }



}
