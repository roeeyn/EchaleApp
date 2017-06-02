package bit01.com.mx.echale.models;

import java.util.List;

/**
 * Created by ericklara on 27/05/17.
 */

public class Historial {

    private String evento;
    private long monto;
    private String nombreLocal;
    private String uriLocal;
    private String nombreVisita;
    private String uriVisita;
    private String fecha;
    private String resultado;

    public Historial() {
        // Default constructor
    }

    public Historial(String evento, long monto, String nombreLocal, String uriLocal, String nombreVisita, String uriVisita, String fecha,String resultado) {
        this.evento = evento;
        this.monto = monto;
        this.nombreLocal = nombreLocal;
        this.uriLocal = uriLocal;
        this.nombreVisita = nombreVisita;
        this.uriVisita = uriVisita;
        this.fecha = fecha;
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
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

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public String getUriLocal() {
        return uriLocal;
    }

    public void setUriLocal(String uriLocal) {
        this.uriLocal = uriLocal;
    }

    public String getNombreVisita() {
        return nombreVisita;
    }

    public void setNombreVisita(String nombreVisita) {
        this.nombreVisita = nombreVisita;
    }

    public String getUriVisita() {
        return uriVisita;
    }

    public void setUriVisita(String uriVisita) {
        this.uriVisita = uriVisita;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
