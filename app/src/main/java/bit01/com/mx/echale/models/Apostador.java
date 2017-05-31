package bit01.com.mx.echale.models;

/**
 * Created by roeeyn on 29/05/17.
 */

public class Apostador {

    private long monto;

    @Override
    public String toString() {
        return "Apostador{" +
                "monto=" + monto +
                '}';
    }

    public Apostador(long monto) {
        this.monto = monto;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }
}
