package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 01/04/2018.
 */

public class Fase {
    PersonaEstadistico personaEstadistico;
    Partido partido;
    Oponente oponente;

    public Fase(){

    }


    public PersonaEstadistico getPersonaEstadistico() {
        return personaEstadistico;
    }

    public void setPersonaEstadistico(PersonaEstadistico personaEstadistico) {
        this.personaEstadistico = personaEstadistico;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Oponente getOponente() {
        return oponente;
    }

    public void setOponente(Oponente oponente) {
        this.oponente = oponente;
    }
}
