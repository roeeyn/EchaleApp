package bit01.com.mx.firebasetest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roeeyn on 29/05/17.
 */

public class Visita {

    Map<String, Object> apostadoresVisita = new HashMap<String, Object>();
    private long bolsaVisita;

    public Visita(Map<String, Object> apostadoresVisita, int bolsaVisita) {
        this.apostadoresVisita = apostadoresVisita;
        this.bolsaVisita = bolsaVisita;
    }

    public Map<String, Object> getApostadoresVisita() {
        return apostadoresVisita;
    }

    public void setApostadoresVisita(Map<String, Object> apostadoresVisita) {
        this.apostadoresVisita = apostadoresVisita;
    }

    public long getBolsaVisita() {
        return bolsaVisita;
    }

    public void setBolsaVisita(long bolsaVisita) {
        this.bolsaVisita = bolsaVisita;
    }

    @Override
    public String toString() {
        return "Visita{" +
                "apostadoresVisita=" + apostadoresVisita +
                ", bolsaVisita=" + bolsaVisita +
                '}';
    }
}
