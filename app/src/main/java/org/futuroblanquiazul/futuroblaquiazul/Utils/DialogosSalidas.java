package org.futuroblanquiazul.futuroblaquiazul.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class DialogosSalidas {

    public DialogosSalidas(){

    }

    public void SalidaLogin(final Context context){


        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("SALIR")
                .setMessage("¿Desea Cerrar Aplicación?")
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
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
    }
}
