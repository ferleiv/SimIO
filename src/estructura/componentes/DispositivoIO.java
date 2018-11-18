package estructura.componentes;

import estructura.simulacion.Distribuciones;
import estructura.simulacion.Programa;
import estructura.Simulacion;

import java.util.Random;

import static java.lang.Math.sqrt;

public class DispositivoIO extends Modulo {

    private final int media;
    private final double desviacionEstandar;

    public DispositivoIO(Simulacion simulacion, Modulo siguienteModulo, int numeroServidores) {
        super(simulacion, siguienteModulo, numeroServidores);

        media = 1000; // 1 seg -> 1000 milisegundos
        desviacionEstandar = sqrt(10000); // 0.01 segundos^2 -> 10000 milsegundos^2
    }

    /*
    * Tiempo de salida entre 40s y 20 s
    * */
    @Override
    protected double getTiempoSalida(Programa programa) {
        Random rand = new Random();
        return sqrt((rand.nextDouble() * 3) + 1);
    }

    @Override
    public void procesarSalida(Programa programa) {

        estadisticasComponente.anadirTiempoServicio(
                programa.getEstadisticaPrograma().getTiempoDeVida(simulacion.getReloj()));

        simulacion.getEstadisticas().anadirNumeroConexionesCompletadas();
        simulacion.getEstadisticas().anadirTiempoConsultaFinalizada(
                programa.getEstadisticaPrograma().getTiempoDeVida(simulacion.getReloj()));
        /**/
    }


}
