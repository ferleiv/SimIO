package estructura.simulacion;

import estructura.componentes.Modulo;
import estructura.estadisticas.EstadisticaPrograma;

public class Programa {
    private Modulo moduloActual;
    private final EstadisticaPrograma estadisticaPrograma;

    public Programa(Modulo moduloActual, double tiempoInicial) {
        this.moduloActual = moduloActual;
        estadisticaPrograma = new EstadisticaPrograma(tiempoInicial);
    }

    public void setModuloActual(Modulo moduloActual) {
        this.moduloActual = moduloActual;
    }

    public EstadisticaPrograma getEstadisticaPrograma() {
        return estadisticaPrograma;
    }

    public Modulo getModuloActual() {
        return moduloActual;
    }

}
