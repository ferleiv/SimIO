package estructura.estadisticas;

import estructura.Simulacion;
import estructura.simulacion.Tiempo;

public class EstadisticasComponente {
    private Tiempo tiempoEnCola;
    private Tiempo tiempoServicio;

    public EstadisticasComponente() {
        tiempoEnCola = new Tiempo();
        tiempoServicio = new Tiempo();
    }

    public void anadirTiempoClienteEnCola(double tiempo) {
        tiempoEnCola.anadirTiempoAcumulado(tiempo);
    }

    public void anadirTiempoServicio(double tiempo) {
        tiempoServicio.anadirTiempoAcumulado(tiempo);
    }

    public double sacarTiempoPromedioCola() {
        return 0;
    }

    public double sacarTiempoPromedioServicio() {
        return 0;
    }

}
