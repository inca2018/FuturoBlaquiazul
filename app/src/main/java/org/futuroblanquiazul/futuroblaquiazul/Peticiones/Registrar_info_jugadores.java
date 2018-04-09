package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class Registrar_info_jugadores extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_ESTADISTICO;
    private Map<String, String> params;

    public Registrar_info_jugadores(String id_gestion,String id_persona,String pg,String dr,String og,String r,
                                    String g,String of,String bp,String br,String f,String ta,String tr,String atj,String tj,
                                    String ptos,String titular,String expulsado, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Registrar_info_jugador");
        params.put("id_gestion",id_gestion);
        params.put("id_persona",id_persona);
        params.put("pg",pg);
        params.put("dr",dr);
        params.put("og",og);
        params.put("r",r);
        params.put("g",g);
        params.put("of",of);
        params.put("bp",bp);
        params.put("br",br);
        params.put("f",f);
        params.put("ta",ta);
        params.put("tr",tr);
        params.put("atj",atj);
        params.put("tj",tj);
        params.put("ptos",ptos);
        params.put("titular",titular);
        params.put("expulsado",expulsado);




    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
