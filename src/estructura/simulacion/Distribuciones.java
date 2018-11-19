package estructura.simulacion;

import estructura.evento.TipoSalidaCPU;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class Distribuciones {

    private static final Map<TipoSalidaCPU, Double> distribucionTipoSalidaCPU = new EnumMap<>(TipoSalidaCPU.class);

    static {
        distribucionTipoSalidaCPU.put(TipoSalidaCPU.UNINTERRUPTED, 0.70);
        distribucionTipoSalidaCPU.put(TipoSalidaCPU.INTERRUPTED, 0.94);
        distribucionTipoSalidaCPU.put(TipoSalidaCPU.ENDED, (double) 1);
    }

    private static Random rand = new Random();

    public static double generarValorDistribucionNormal(int media, double desviacionEstandar) {
        //return Math.abs(rand.nextGaussian() * media + desviacionEstandar);
        double z = 0;
        for(int i = 0; i<12; i++){
            z += generarValorDistribucionUniforme(0,1);
        }
        return desviacionEstandar*(z-6) + media;
    }

    public static double generarValorDistribucionUniforme(int inicioIntervalo, int finalIntervalo) {
        return (finalIntervalo - inicioIntervalo) * rand.nextDouble()/* - inicioIntervalo*/;
    }

    public static double generarValorDistibucionExponencial(double lambda) {
        return Math.log(1 - rand.nextDouble()) / -lambda;
    }

    public static TipoSalidaCPU generarTipoSalidaCPU() {
        double random = rand.nextDouble();
        for (Map.Entry<TipoSalidaCPU, Double> entrada :
                distribucionTipoSalidaCPU.entrySet()) {
            if (random < entrada.getValue()) return entrada.getKey();
        }
        return TipoSalidaCPU.UNINTERRUPTED;
    }
}
