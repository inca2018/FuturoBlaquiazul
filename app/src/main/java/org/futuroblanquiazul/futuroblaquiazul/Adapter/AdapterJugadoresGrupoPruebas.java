package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaDiagnosticoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaFisicoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaNutricionalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaPsicologicaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaTacticaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaTecnicaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.GrupoPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaFisica;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaNutricional;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaTactica;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.DesactivarPersona;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.DisponibilidadDiagnostico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.DisponibilidadFisico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.DisponibilidadNutricional;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.DisponibilidadPsico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.DisponibilidadTactica;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.DisponibilidadTecnico;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterJugadoresGrupoPruebas extends RecyclerView.Adapter<AdapterJugadoresGrupoPruebas.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    ProgressDialog progressDialog;

    public AdapterJugadoresGrupoPruebas(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


        TextView nom;
        ImageView acciones;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nom=itemView.findViewById(R.id.nombre_persona_grupo_pruebas);
            acciones=itemView.findViewById(R.id.acciones_personas_grupo_pruebas);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_persona_grupo_prueba,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.nom.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());

        holder.acciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PopupMenu popupMenu=new PopupMenu(context,holder.acciones);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item_persona_grupo_pruebas,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().toString().equalsIgnoreCase("Prueba Diagnostico")){

                            Verificar_Diagnostico(Usuario.SESION_ACTUAL.getGrupoPruebasTEMP(),my_Data.get(position).getId(),context,my_Data.get(position));          debug("VERIFICAR DIAGNOSTICO: CODIGO_GRUPO - "+Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getId()+" PERSONA CODIGO:  "+my_Data.get(position).getId());

                        }else if(item.getTitle().toString().equalsIgnoreCase("Prueba Fisico")){

                            Verificar_Fisico(Usuario.SESION_ACTUAL.getGrupoPruebasTEMP(),my_Data.get(position).getId(),context,my_Data.get(position));
                            debug("VERIFICAR FISICO: CODIGO_GRUPO - "+Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getId()+" PERSONA CODIGO:  "+my_Data.get(position).getId());


                        }else if(item.getTitle().toString().equalsIgnoreCase("Prueba Tecnico")){

                            Verificar_Tecnico(Usuario.SESION_ACTUAL.getGrupoPruebasTEMP(),my_Data.get(position).getId(),context,my_Data.get(position));
                            debug("VERIFICAR DIAGNOSTICO: CODIGO_GRUPO - "+Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getId()+" PERSONA CODIGO:  "+my_Data.get(position).getId());

                    }else if(item.getTitle().toString().equalsIgnoreCase("Prueba Psicologica")){
                            Verificar_Psicologica(Usuario.SESION_ACTUAL.getGrupoPruebasTEMP(),my_Data.get(position).getId(),context,my_Data.get(position));
                            debug("VERIFICAR DIAGNOSTICO: CODIGO_GRUPO - "+Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getId()+" PERSONA CODIGO:  "+my_Data.get(position).getId());


                    }else if(item.getTitle().toString().equalsIgnoreCase("Prueba Tactica")){

                            Verificar_Tactica(Usuario.SESION_ACTUAL.getGrupoPruebasTEMP(),my_Data.get(position).getId(),context,my_Data.get(position));
                            debug("VERIFICAR DIAGNOSTICO: CODIGO_GRUPO - "+Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getId()+" PERSONA CODIGO:  "+my_Data.get(position).getId());


                    }else if(item.getTitle().toString().equalsIgnoreCase("Prueba Nutricional")){

                            Verificar_Nutricional(Usuario.SESION_ACTUAL.getGrupoPruebasTEMP(),my_Data.get(position).getId(),context,my_Data.get(position));
                            debug("VERIFICAR DIAGNOSTICO: CODIGO_GRUPO - "+Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getId()+" PERSONA CODIGO:  "+my_Data.get(position).getId());

                    }

                        return true;
                    }
                });

                popupMenu.show();


            }
        });


    }

    private void Verificar_Nutricional(final GrupoPruebas grupo, final int persona, final Context context, final Persona p) {


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Metodologia:");
        progressDialog.setMessage("Verificando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_grupo=String.valueOf(grupo.getId());
        String id_plantel=String.valueOf(grupo.getPlantel().getId());
        String id_persona=String.valueOf(persona);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();
                        Usuario.SESION_ACTUAL.setPersona_metodologia_pruebas(p);


                        Intent intent = new Intent(context,PruebaNutricionalActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Ya se realizo la prueba Nutricional!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        DisponibilidadNutricional xx = new DisponibilidadNutricional(id_grupo,id_plantel,id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    private void Verificar_Tactica(final GrupoPruebas grupo, final int persona, final Context context, final Persona p) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Metodologia:");
        progressDialog.setMessage("Verificando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_grupo=String.valueOf(grupo.getId());
        String id_plantel=String.valueOf(grupo.getPlantel().getId());
        String id_persona=String.valueOf(persona);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();
                        Usuario.SESION_ACTUAL.setPersona_metodologia_pruebas(p);


                        Intent intent = new Intent(context,PruebaTacticaActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Ya se realizo la prueba  Tactica!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        DisponibilidadTactica xx = new DisponibilidadTactica(id_grupo,id_plantel,id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    private void Verificar_Psicologica(final GrupoPruebas grupo, final int persona, final Context context, final Persona p) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Metodologia:");
        progressDialog.setMessage("Verificando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_grupo=String.valueOf(grupo.getId());
        String id_plantel=String.valueOf(grupo.getPlantel().getId());
        String id_persona=String.valueOf(persona);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();
                        Usuario.SESION_ACTUAL.setPersona_metodologia_pruebas(p);


                        Intent intent = new Intent(context,PruebaPsicologicaActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Ya se realizo la prueba  Psicologica!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        DisponibilidadPsico xx = new DisponibilidadPsico(id_grupo,id_plantel,id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);




    }

    private void Verificar_Tecnico(final GrupoPruebas grupo, final int persona, final  Context context, final Persona p) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Metodologia:");
        progressDialog.setMessage("Verificando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_grupo=String.valueOf(grupo.getId());
        String id_plantel=String.valueOf(grupo.getPlantel().getId());
        String id_persona=String.valueOf(persona);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();
                        Usuario.SESION_ACTUAL.setPersona_metodologia_pruebas(p);


                        Intent intent = new Intent(context,PruebaTecnicaActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Ya se realizo la prueba de Tecnica!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        DisponibilidadTecnico xx = new DisponibilidadTecnico(id_grupo,id_plantel,id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    private void Verificar_Fisico(final GrupoPruebas grupo, final int persona, final Context context, final Persona p) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Metodologia:");
        progressDialog.setMessage("Verificando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_grupo=String.valueOf(grupo.getId());
        String id_plantel=String.valueOf(grupo.getPlantel().getId());
        String id_persona=String.valueOf(persona);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();
                        Usuario.SESION_ACTUAL.setPersona_metodologia_pruebas(p);

                        Intent intent = new Intent(context,PruebaFisicoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Ya se realizo la prueba de Fisico!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        DisponibilidadFisico validarSesion = new DisponibilidadFisico(id_grupo,id_plantel,id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(validarSesion);


    }

    private void Verificar_Diagnostico(final GrupoPruebas grupo , final int  persona, final Context context, final Persona p) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Metodologia:");
        progressDialog.setMessage("Verificando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_grupo=String.valueOf(grupo.getId());
        String id_plantel=String.valueOf(grupo.getPlantel().getId());
        String id_persona=String.valueOf(persona);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {


                        Usuario.SESION_ACTUAL.setPersona_metodologia_pruebas(p);

                        Intent intent = new Intent(context, PruebaDiagnosticoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        progressDialog.dismiss();
                    }else {

                        Toast.makeText(context, "Ya se realizo la prueba de diagnostico!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        DisponibilidadDiagnostico validarSesion = new DisponibilidadDiagnostico(id_grupo,id_plantel,id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(validarSesion);

    }


    @Override
    public int getItemCount() {
        return my_Data.size();
    }

    public void debug(String s){
        System.out.println(s);
    }
}