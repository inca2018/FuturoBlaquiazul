package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class RegistrarPersona extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_MANTENIMIENTO;
    private Map<String, String> params;

    public RegistrarPersona(String estado, String nom, String ape, String f_naci, String naci, String lu, String depa, String prov, String dist, String club, String liga, String tele,String fijo,String bautizo,String comunion,String cnfirmacion,String dni,String correo,String apo,String tele_apo,String cate,String foto,String nom_foto, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrarPersona");
        params.put("estado",estado);
        params.put("nombre_persona",nom);
        params.put("apellido_persona",ape);
        params.put("fecha_nacimiento",f_naci);
        params.put("nacionalidad",naci);
        params.put("lugar_residencia",lu);
        params.put("departamento",depa);
        params.put("provincia",prov);
        params.put("distrito",dist);
        params.put("club_actual",club);
        params.put("liga_Actual",liga);
        params.put("telefono",tele);
        params.put("telefono_fijo",fijo);
        params.put("bautizo",bautizo);
        params.put("comunion",comunion);
        params.put("confirmacion",cnfirmacion);
        params.put("dni",dni);
        params.put("email",correo);
        params.put("apoderado",apo);
        params.put("tele_apoderado",tele_apo);
        params.put("categoria",cate);

        params.put("foto",foto);
        params.put("foto_nom",nom_foto);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
