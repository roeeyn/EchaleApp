package bit01.com.mx.echale.models;

/**
 * Created by ericklara on 27/05/17.
 */

/*
*
*
* ESTA CLASE ES PARA USARSE EN EL HISTORIAL, NO SE CONFUNDA CON "APUESTAS" QUE ES EL MODELO DE FIREBASE
*
* */

public class Apuesta {

    private String Evento;
    private String id_partido;
    private String monto;

    public Apuesta() {
        // Default constructor
    }

    public Apuesta(String evento, String id_partido, String monto) {
        Evento = evento;
        this.id_partido = id_partido;
        this.monto = monto;
    }

    public String getEvento() {
        return Evento;
    }

    public void setEvento(String evento) {
        Evento = evento;
    }

    public String getId_partido() {
        return id_partido;
    }

    public void setId_partido(String id_partido) {
        this.id_partido = id_partido;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
