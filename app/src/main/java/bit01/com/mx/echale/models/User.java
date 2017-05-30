package bit01.com.mx.echale.models;

import java.util.List;

/**
 * Created by ericklara on 27/05/17.
 */

public class User {

    private String nombre;
    private String mail;
    private String urlPicture;
    private int monedas;
    private Historial historial;

    public User() {
        // Default constructor
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public User(String nombre, String mail, String urlPicture, int monedas, Historial historial) {
        this.nombre = nombre;
        this.mail = mail;
        this.urlPicture = urlPicture;
        this.monedas = monedas;
        this.historial = historial;
    }

    @Override
    public String toString() {
        return "User{" +
                "nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", urlPicture='" + urlPicture + '\'' +
                ", monedas=" + monedas +
                ", historial=" + historial +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    public Historial getHistorial() {
        return historial;
    }

    public void setHistorial(Historial historial) {
        this.historial = historial;
    }
}
