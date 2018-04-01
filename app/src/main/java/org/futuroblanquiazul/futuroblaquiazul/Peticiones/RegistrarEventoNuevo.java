package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class RegistrarEventoNuevo extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_ESTADISTICO;
    private Map<String, String> params;

    public RegistrarEventoNuevo(String nombre,String detalle,String id_depa,String id_pro,String id_dis ,String id_user,String plantel,String foto,String estado,String estado2 ,String f_inicio,String f_fin,String foto_nom,Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrar_nuevo_Evento");
        params.put("nombre_evento",nombre);
        params.put("detalle_evento",detalle);
        params.put("departamento",id_depa);
        params.put("provincia",id_pro);
        params.put("distrito",id_dis);
        params.put("id_user",id_user);
        params.put("id_plantel",plantel);
        params.put("foto",foto);
        params.put("estado",estado);
        params.put("estado2",estado2);
        params.put("fecha_inicio",f_inicio);
        params.put("fecha_fin",f_fin);
        params.put("foto_nom",foto_nom);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
