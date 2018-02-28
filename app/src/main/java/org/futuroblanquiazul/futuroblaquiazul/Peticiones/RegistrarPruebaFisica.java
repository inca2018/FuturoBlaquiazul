package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class RegistrarPruebaFisica extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public RegistrarPruebaFisica(String id_user, String id_barrio,String id_persona,String peso,String talla,String rj,String cmj,String abk,String fms,String velocidad,String yoyo,String info_velo,String info_pot,String info_resi,String prom_velo,String prom_pote,String prom_resi,String total,String estado, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Registrar_prueba_fisica");
        params.put("id_user",id_user);
        params.put("id_barrio",id_barrio);
        params.put("id_persona",id_persona);
        params.put("peso",peso);
        params.put("talla",talla);
        params.put("rj",rj);
        params.put("cmj",cmj);
        params.put("abk",abk);
        params.put("fms",fms);
        params.put("velocidad",velocidad);
        params.put("yoyo",yoyo);
        params.put("info_velo",info_velo);
        params.put("info_potencia",info_pot);
        params.put("info_resistencia",info_resi);
        params.put("prom_velo",prom_velo);
        params.put("prom_potencia",prom_pote);
        params.put("prom_resistencia",prom_resi);
        params.put("total_general",total);
        params.put("estado",estado);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
