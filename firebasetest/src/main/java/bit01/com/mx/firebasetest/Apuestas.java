package bit01.com.mx.firebasetest;

/**
 * Created by roeeyn on 29/05/17.
 */

/*
*
*
* ESTA CLASE ES PARA USARSE EN FIREBASE, NO SE CONFUNDA CON "APUESTA" QUE ES LA CLASE USADA EN HISTORIAL
*
* */

public class Apuestas {

    private long bolsaTotalPartido = 0;

    private Empate empate;

    private Visita visita;

    private Local local;

    @Override
    public String toString() {
        return "Apuestas{" +
                "bolsaTotalPartido=" + bolsaTotalPartido +
                ", empate=" + empate +
                ", visita=" + visita +
                ", local=" + local +
                '}';
    }

    public Apuestas(long bolsaTotalPartido, Empate empate, Visita visita, Local local) {
        this.bolsaTotalPartido = bolsaTotalPartido;
        this.empate = empate;
        this.visita = visita;
        this.local = local;
    }

    public long getBolsaTotalPartido() {
        return bolsaTotalPartido;
    }

    public void setBolsaTotalPartido(long bolsaTotalPartido) {
        this.bolsaTotalPartido = bolsaTotalPartido;
    }

    public Empate getEmpate() {
        return empate;
    }

    public void setEmpate(Empate empate) {
        this.empate = empate;
    }

    public Visita getVisita() {
        return visita;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
}
