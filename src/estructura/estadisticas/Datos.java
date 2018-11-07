package estructura.estadisticas;

import estructura.tipos.TipoModulo;
import javafx.util.Pair;

import java.util.Map;

public class Datos {
    public final double reloj;
    public final int numeroProgramasCompletados;
    public final Map<TipoModulo, Pair<Integer, Integer>> infoModulo;

    public Datos(double reloj, int numeroProgramasCompletados, Map<TipoModulo, Pair<Integer, Integer>> infoModulo) {
        this.reloj = reloj;
        this.numeroProgramasCompletados = numeroProgramasCompletados;
        this.infoModulo = infoModulo;
    }
}
