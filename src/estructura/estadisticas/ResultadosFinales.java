package estructura.estadisticas;

import javafx.util.Pair;

public class ResultadosFinales extends Resultados {
    public final double interavaloConfianzaPiso;
    public final double getInteravaloConfianzaTecho;
    public  final double promedioUsoCPU;

    public ResultadosFinales(Resultados resultados, Pair<Double, Double> intevaloConfianza, double promedioUsoCPU) {
        super(resultados.numeroConexionesCompletadas, resultados.tiempoPromedioVidaConexion,
                resultados.tamanoPromedioCola, resultados.tiempoPromedioUsoCPU);
        this.interavaloConfianzaPiso = intevaloConfianza.getKey();
        this.getInteravaloConfianzaTecho = intevaloConfianza.getValue();
        this.promedioUsoCPU = promedioUsoCPU;
    }
}
