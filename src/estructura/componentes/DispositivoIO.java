package estructura.componentes;

import estructura.simulacion.Distribuciones;
import estructura.simulacion.Programa;
import estructura.Simulacion;

public class DispositivoIO extends Modulo {

    private final int media;
    private final double desviacionEstandar;

    public DispositivoIO(Simulacion simulacion, Modulo siguienteModulo, int numeroServidores) {
        super(simulacion, siguienteModulo, numeroServidores);

        media = 1000; // 1 seg -> 1000 milisegundos
        desviacionEstandar = Math.sqrt(10000); // 0.01 segundos^2 -> 10000 milsegundos^2
    }

    @Override
    protected double getTiempoSalida(Programa programa) {
        return Distribuciones.generarValorDistribucionNormal(media, desviacionEstandar);
    }
}
