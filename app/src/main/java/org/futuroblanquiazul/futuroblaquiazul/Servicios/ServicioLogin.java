package org.futuroblanquiazul.futuroblaquiazul.Servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;


public interface ServicioLogin {

    String Validar_Sesion(String usuario, String pass, Context context);
    int Recuperar_Codigo_Usuario(String usuario, String pass, Context context);
    Usuario Recuperar_Usuario(int codigo_usuario, Context context);


}
