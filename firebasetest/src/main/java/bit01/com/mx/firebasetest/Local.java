package bit01.com.mx.firebasetest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roeeyn on 29/05/17.
 */

public class Local {

    Map<String, Object> apostadoresLocal = new HashMap<String, Object>();
    private long bolsaLocal;

    public Map<String, Object> getApostadoresLocal() {
        return apostadoresLocal;
    }

    public void setApostadoresLocal(Map<String, Object> apostadoresLocal) {
        this.apostadoresLocal = apostadoresLocal;
    }

    public Local(Map<String, Object> apostadoresLocal, int bolsaLocal) {

        this.apostadoresLocal = apostadoresLocal;
        this.bolsaLocal = bolsaLocal;
    }

    public long getBolsaLocal() {
        return bolsaLocal;
    }

    public void setBolsaLocal(long bolsaLocal) {
        this.bolsaLocal = bolsaLocal;
    }

    @Override
    public String toString() {
        return "Local{" +
                "apostadoresLocal=" + apostadoresLocal +
                ", bolsaLocal=" + bolsaLocal +
                '}';
    }
}
