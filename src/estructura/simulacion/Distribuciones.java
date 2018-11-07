package estructura.simulacion;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class Distribuciones {

    private static Random rand = new Random();

    public static double generarValorDistribucionNormal(int media, double desviacionEstandar) {
        return Math.abs(rand.nextGaussian() * media + desviacionEstandar);
    }

    public static double generarValorDistribucionUniforme(int inicioIntervalo, int finalIntervalo) {
        return (finalIntervalo - inicioIntervalo) * rand.nextDouble() + inicioIntervalo;
    }

    public static double generarValorDistibucionExponencial(double lambda) {
        return Math.log(1 - rand.nextDouble()) / -lambda;
    }
}
