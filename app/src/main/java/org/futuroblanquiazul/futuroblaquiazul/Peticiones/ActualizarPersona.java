package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class ActualizarPersona extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_MANTENIMIENTO;
    private Map<String, String> params;

    public ActualizarPersona(String nom,String ape,String fech_naci,String nacionalidad,String lugar_residencia,String id_depa,String id_prov,String id_dis,String club,String liga,String telefono,String telefono_fijo,String bautizo,String comunion,String confirmacion,String dni,String correo,String apoderado,String tele_apoderado,String categoria,String foto,String foto_nom,String foto_nom_antigua,String id_persona, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Actualizar_Persona");
        params.put("nombre_persona",nom);
        params.put("apellido_persona",ape);
        params.put("fecha_nacimiento",fech_naci);
        params.put("nacionalidad",nacionalidad);
        params.put("lugar_residencia",lugar_residencia);
        params.put("departamento",id_depa);
        params.put("provincia",id_prov);
        params.put("distrito",id_dis);
        params.put("club_actual",club);
        params.put("liga_Actual",liga);
        params.put("telefono",telefono);
        params.put("telefono_fijo",telefono_fijo);
        params.put("bautizo",bautizo);
        params.put("comunion",comunion);
        params.put("confirmacion",confirmacion);
        params.put("dni",dni);
        params.put("email",correo);
        params.put("apoderado",apoderado);
        params.put("tele_apoderado",tele_apoderado);
        params.put("categoria",categoria);

        params.put("foto",foto);
        params.put("foto_nom",foto_nom);

        params.put("foto_nom_antigua",foto_nom_antigua);

        params.put("id_personas",id_persona);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
