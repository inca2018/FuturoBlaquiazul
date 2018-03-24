package org.futuroblanquiazul.futuroblaquiazul;

import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Jesus Inca on 21/03/2018.
 */

public class COMPONENTES {

      // CUERPO DE RECYCLER
    /*      linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        adapterUsuarios = new AdapterUsuarios(context,Lista_Usuarios, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        });
        recyclerView.setAdapter(adapterUsuarios);
        recyclerView.setLayoutManager(linearLayoutManager);

        Listar_Usuarios(context);*/


      int  ARMADO_DE_SPINNER=1;
    /*
    *
    *    String [] lista_bases_numeros=new String[my_Data.get(position).getLista_Numeros().size()+1];
            lista_bases_numeros[0]="-- SELECCIONE --";

            for(int i=0;i<my_Data.get(position).getLista_Numeros().size();i++){
                lista_bases_numeros[i+1]=String.valueOf(my_Data.get(position).getLista_Numeros().get(i)).trim();
            }

            ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_bases_numeros);
            spinner_numero.setAdapter(adapter_arr);

            */

      int  VERIFICACION=1;

      /*
      final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("SALIR")
                .setMessage("¿Desea Cerrar Aplicación?")
                .setPositiveButton("SI",
                                           new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
      })
              .setNegativeButton("NO",
                                         new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
            }
      });
        builder.show();
        */



      int CUERPO_DE_PETICION=1;


      /*


        String id_usuario=String.valueOf(id);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        BloquearUsuario xx = new BloquearUsuario(id_usuario, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

      */


      int PROGRESS=1;

      /*
      *      progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Usuario:");
        progressDialog.setMessage("Registrando Usuario...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

      *
      * */


      int REGRESO_BACK=1;


      /*
       *
        *   public void onBackPressed() {
        Intent intent=new Intent(BarrioIntimoActivity.this,PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("o","o1");
        BarrioIntimoActivity.this.startActivity(intent);
    }
        *
        * */


}
