package bit01.com.mx.echale.models;

import java.util.List;

/**
 * Created by ericklara on 27/05/17.
 */

public class User {

    private String nombre;
    private String mail;
    private int monedas;
    private Historial historial;
    private String photoUrl;

    public User() {
        // Default constructor
    }


    public User(String nombre, String mail, int monedas, Historial historial, String photoUrl) {
        this.nombre = nombre;
        this.mail = mail;
        this.monedas = monedas;
        this.historial = historial;
        this.photoUrl = photoUrl;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
