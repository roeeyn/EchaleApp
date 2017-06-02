package bit01.com.mx.echale.models;

/**
 * Created by ericklara on 24/05/17.
 */


import java.util.HashMap;
import java.util.Map;

/**
 * Created by ericklara on 24/05/17.
 */


/**
 * Clase Partido para el Recycler View que muestra los partidos

 */

public class Partido {

    Map<String, Object> apuestas = new HashMap<String, Object>();
    private String idPartido;
    private String nombreLocal;
    private String nombreVisita;
    private String fecha;
    private String urlLocal;
    private String urlVisita;
    private String hora;
    private String status;
    private String resultado;

    public Partido(Map<String, Object> apuestas, String idPartido, String nombreLocal, String nombreVisita, String fecha, String urlLocal, String urlVisita, String hora, String status, String resultado) {
        this.apuestas = apuestas;
        this.idPartido = idPartido;
        this.nombreLocal = nombreLocal;
        this.nombreVisita = nombreVisita;
        this.fecha = fecha;
        this.urlLocal = urlLocal;
        this.urlVisita = urlVisita;
        this.hora = hora;
        this.status = status;
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Map<String, Object> getApuestas() {
        return apuestas;
    }

    public void setApuestas(Map<String, Object> apuestas) {
        this.apuestas = apuestas;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public String getNombreVisita() {
        return nombreVisita;
    }

    public void setNombreVisita(String nombreVisita) {
        this.nombreVisita = nombreVisita;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUrlLocal() {
        return urlLocal;
    }

    public void setUrlLocal(String urlLocal) {
        this.urlLocal = urlLocal;
    }

    public String getUrlVisita() {
        return urlVisita;
    }

    public void setUrlVisita(String urlVisita) {
        this.urlVisita = urlVisita;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Partido() {

    }

    public String getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return hora;
    }

    public void setTime(String hora) {
        this.hora = hora;
    }

    public String getLocalTeam() {
        return nombreLocal;
    }

    public void setLocalTeam(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public String getAwayTeam() {
        return nombreVisita;
    }

    public void setAwayTeam(String nombreVisita) {
        this.nombreVisita = nombreVisita;
    }

    public String getDate() {
        return fecha;
    }

    public void setDate(String fecha) {
        this.fecha = fecha;
    }

    public String getLocalTeamImageUrl() {
        return urlLocal;
    }

    public void setLocalTeamImageUrl(String urlLocal) {
        this.urlLocal = urlLocal;
    }

    public String getAwayTeamImageUrl() {
        return urlVisita;
    }

    public void setAwayTeamImageUrl(String urlVisita) {
        this.urlVisita = urlVisita;
    }
}
