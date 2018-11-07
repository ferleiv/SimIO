package estructura.simulacion;

import estructura.componentes.Modulo;
import estructura.evento.TipoEvento;

public class Evento implements Comparable<Evento> {
    private final double tiempoEvento;
    private final Modulo modulo;
    private final TipoEvento tipoEvento;
    private final Programa programa;

    public Evento(double tiempoEvento, Modulo modulo, TipoEvento tipoEvento, Programa programa) {
        this.tiempoEvento = tiempoEvento;
        this.modulo = modulo;
        this.tipoEvento = tipoEvento;
        this.programa = programa;
    }

    public double getTiempoEvento() {
        return tiempoEvento;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public Programa getPrograma() {
        return programa;
    }

    @Override
    public int compareTo(Evento o) {
        return Double.compare(tiempoEvento, o.tiempoEvento);
    }
}
