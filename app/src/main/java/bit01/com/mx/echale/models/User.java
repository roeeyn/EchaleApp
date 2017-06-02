package bit01.com.mx.echale.models;

import java.util.List;

/**
 * Created by ericklara on 27/05/17.
 */

public class User {

    /// Class variables
    private String nombre;
    private String mail;
    private String Fecha_nacimiento;
    private int monedas;
    private Historial historial;
    private String photoUrl;

    public User() {
        // Default constructor
    }

    // Constructor
    public User(String nombre, String mail, String fecha_nacimiento, int monedas, Historial historial, String photoUrl) {
        this.nombre = nombre;
        this.mail = mail;
        Fecha_nacimiento = fecha_nacimiento;
        this.monedas = monedas;
        this.historial = historial;
        this.photoUrl = photoUrl;
    }

    // Getters and setters

    public String getFecha_nacimiento() {
        return Fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        Fecha_nacimiento = fecha_nacimiento;
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
