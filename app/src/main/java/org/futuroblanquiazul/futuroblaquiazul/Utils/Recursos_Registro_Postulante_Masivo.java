package org.futuroblanquiazul.futuroblaquiazul.Utils;

import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.ArrayList;
import java.util.List;

public class Recursos_Registro_Postulante_Masivo {
    public static final List<RegistroCaptacion_funcional> LISTA_REGISTRO =new ArrayList<>();

    static {
        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                1,
                R.id.capta_nombre2,
                null,
                "!Ingrese Nombre del Postulante¡"
                ,false,null));
        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                2,
                R.id.capta_apellido2,
                null,
                "!Ingrese Apellidos del Postulante¡"
                ,false,null));

        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                3,
                R.id.capta_nacionalidad2,
                null,
                "!Ingrese Nacionalidad del Postulante¡"
                ,false,null));

        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                4,
                R.id.capta_procedencia2,
                null,
                "!Ingrese Club de Procedencia del Postulante¡"
                ,false,null));

        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                5,
                R.id.capta_liga2,
                null,
                "!Ingrese Liga Actual del Postulante¡"
                ,false,null));

        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                6,
                R.id.capta_categoria2,
                null,
                "!Ingrese Categoria del Postulante¡"
                ,false,null));

        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                7,
                R.id.capta_dni2,
                null,
                "!Ingrese Dni del Postulante¡"
                ,false,null));

        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                8,
                R.id.capta_fecha_nacimiento2,
                null,
                "!Ingrese Fecha de Nacimiento del Postulante¡"
                ,false,null));

        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                9,
                R.id.capta_residencia2,
                null,
                "!Ingrese Lugar de Residencia del Postulante¡"
                ,false,null));

        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                10,
                R.id.capta_telefono12,
                null,
                "!Ingrese Telefonos de Contacto del Postulante¡"
                ,false,null));

        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                11,
                R.id.capta_email2,
                null,
                "!Ingrese correo del Postulante¡"
                ,false,null));

        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                12,
                R.id.capta_apoderado2,
                null,
                "!Ingrese Nombres del Apoderado del Postulante¡"
                ,false,null));

        LISTA_REGISTRO.add(new RegistroCaptacion_funcional(
                13,
                R.id.capta_telefono22,
                null,
                "!Ingrese Telefonos del Apoderado del Postulante¡"
                ,false,null));
    }

}
