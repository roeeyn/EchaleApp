package bit01.com.mx.echale.models;

/**
 * Created by roeeyn on 1/06/17.
 */

public class ApuestaPendiente {

    private String status;
    private String evento;
    private double monto;
    private String nombreLocal;
    private String uriLocal;
    private String nombreVisita;
    private String uriVisita;
    private String fecha;

    public ApuestaPendiente() {
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ApuestaPendiente(String status, String evento, double monto, String nombreLocal, String uriLocal, String nombreVisita, String uriVisita, String fecha) {
        this.status = status;
        this.evento = evento;
        this.monto = monto;
        this.nombreLocal = nombreLocal;
        this.uriLocal = uriLocal;
        this.nombreVisita = nombreVisita;
        this.uriVisita = uriVisita;
        this.fecha = fecha;
    }
}
