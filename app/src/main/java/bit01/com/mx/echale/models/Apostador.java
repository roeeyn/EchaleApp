package bit01.com.mx.echale.models;

/**
 * Created by roeeyn on 29/05/17.
 */

public class Apostador {

    private long monto;
    private String userID;

    public long getMonto() {
        return monto;
    }

    public Apostador() {
    }

    @Override
    public String toString() {
        return "Apostador{" +
                "monto=" + monto +
                ", userID='" + userID + '\'' +
                '}';
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Apostador(long monto, String userID) {
        this.monto = monto;
        this.userID = userID;
    }
}
