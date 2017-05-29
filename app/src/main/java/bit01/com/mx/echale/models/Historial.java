package bit01.com.mx.echale.models;

import java.util.List;

/**
 * Created by ericklara on 27/05/17.
 */

public class Historial {

    private List<Apuesta> apuestas;

    public Historial() {
        // Default constructor
    }

    public Historial(List<Apuesta> apuestas) {
        this.apuestas = apuestas;
    }

    public List<Apuesta> getApuestas() {
        return apuestas;
    }

    public void setApuestas(List<Apuesta> apuestas) {
        this.apuestas = apuestas;
    }
}
