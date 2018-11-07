package estructura.estadisticas;

import javafx.util.Pair;

public class ResultadosFinales extends Resultados {
    public final double interavaloConfianzaPiso;
    public final double getInteravaloConfianzaTecho;

    public ResultadosFinales(Resultados resultados, Pair<Double, Double> intevaloConfianza) {
        super(resultados.numeroConexionesCompletadas, resultados.tiempoPromedioVidaConexion,
                resultados.tamanoPromedioCola);
        this.interavaloConfianzaPiso = intevaloConfianza.getKey();
        this.getInteravaloConfianzaTecho = intevaloConfianza.getValue();
    }
}
