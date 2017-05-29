package bit01.com.mx.firebasetest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roeeyn on 29/05/17.
 */

public class Empate {

    Map<String, Object> apostadoresEmpate = new HashMap<String, Object>();
    private long bolsaEmpate;

    public Empate(Map<String, Object> apostadoresEmpate, int bolsaEmpate) {
        this.apostadoresEmpate = apostadoresEmpate;
        this.bolsaEmpate = bolsaEmpate;
    }

    public Map<String, Object> getApostadoresEmpate() {
        return apostadoresEmpate;
    }

    public void setApostadoresEmpate(Map<String, Object> apostadoresEmpate) {
        this.apostadoresEmpate = apostadoresEmpate;
    }

    public long getBolsaEmpate() {
        return bolsaEmpate;
    }

    public void setBolsaEmpate(long bolsaEmpate) {
        this.bolsaEmpate = bolsaEmpate;
    }

    @Override
    public String toString() {
        return "Empate{" +
                "apostadoresEmpate=" + apostadoresEmpate +
                ", bolsaEmpate=" + bolsaEmpate +
                '}';
    }
}
