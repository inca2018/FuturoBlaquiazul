package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;
import java.util.HashMap;
import java.util.Map;


public class RegistrarPersonaRecuperarCodigo extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public RegistrarPersonaRecuperarCodigo(String Nombres,String Apellidos,String Nacionalidad,String Club,String Liga,String Categoria,String Dni,String FechaNacimiento,String LugarResidencia,String Telefonos,String Correo,String Apoderado,String TelefonoApoderado,String estado,String estado_capta,String depa,String prov,String dis, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrar_persona");
        params.put("nombres", Nombres);
        params.put("apellidos", Apellidos);
        params.put("nacionalidad", Nacionalidad);
        params.put("club_actual", Club);
        params.put("liga_actual", Liga);
        params.put("categoria_actual", Categoria);
        params.put("dni", Dni);
        params.put("fecha_nacimiento", FechaNacimiento);
        params.put("lugar_res", LugarResidencia);
        params.put("tele1", Telefonos);
        params.put("email", Correo);
        params.put("apoderado", Apoderado);
        params.put("contacto_apoderado", TelefonoApoderado);
        params.put("estado", estado);
        params.put("estado_capta",estado_capta);
        params.put("departamento",depa);
        params.put("provincia",prov);
        params.put("distrito",dis);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
