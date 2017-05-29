package bit01.com.mx.firebasetest;

/**
 * Created by roeeyn on 29/05/17.
 */

public class Apostador {

    private long apostadorId;

    private long monto;

    @Override
    public String toString() {
        return "Apostador{" +
                "apostadorId='" + apostadorId + '\'' +
                ", monto=" + monto +
                '}';
    }

    public Apostador(long apostadorId, long monto) {
        this.apostadorId = apostadorId;
        this.monto = monto;
    }

    public long getApostadorId() {
        return apostadorId;
    }

    public void setApostadorId(long apostadorId) {
        this.apostadorId = apostadorId;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }
}
