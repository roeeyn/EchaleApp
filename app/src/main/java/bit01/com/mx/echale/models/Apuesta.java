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

    private String evento;
    private long monto;

    public Apuesta(String evento, long monto) {
        this.evento = evento;
        this.monto = monto;
    }

    public Apuesta() {
        // Default constructor
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }
}
