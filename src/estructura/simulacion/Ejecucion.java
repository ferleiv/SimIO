package estructura.simulacion;

import estructura.Simulacion;
import estructura.tipos.TipoModulo;
import estructura.estadisticas.Resultados;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Ejecucion {
    private final Simulacion simulacion;
    private List<Resultados> resultados;
    private final int veces;
    private final Queue<Observer> observersQueue;
    private Resultados ultimosResultados;
    private int numeroEjecuccion;

    public Ejecucion(int veces, int tiempoTotal, int timeout
                              ) {
        this.veces = veces;
        simulacion = new Simulacion(tiempoTotal, timeout);
        resultados = new LinkedList<>();
        observersQueue = new LinkedList<>();
        numeroEjecuccion = 0;
    }

    public Simulacion getSimulacion() {
        return simulacion;
    }

    /*public ResultadosFinales realizarEjecucciones() {
        for (int i = 0; i < veces; i++) {
            ultimosResultados = simulacion.realizarSimulacion();

            this.resultados.add(ultimosResultados);
            numeroEjecuccion = i + 1;
        }

        // Si se corre una sola vez, la tabla t-student se cae
        return veces == 1 ? new ResultadosFinales(ultimosResultados, new Pair<>(0.0, 0.0)) :
                new ResultadosFinales(getPromediosTodasEjecuciones(), getIntervaloConfianzaTiempoVidaConexion());
    }*/

    public Resultados getUltimosResultados() {
        return ultimosResultados;
    }

    public int getNumeroEjecuccion() {
        return numeroEjecuccion;
    }

    /*public Pair<Double, Double> getIntervaloConfianzaTiempoVidaConexion() {
        SummaryStatistics estadistica = new SummaryStatistics();
        resultados.forEach(resultado -> estadistica.addValue(resultado.tiempoPromedioVidaConexion));

        TDistribution tDist = new TDistribution(estadistica.getN() - 1);
        // Valor en tabla t
        double critVal = tDist.inverseCumulativeProbability(1.0 - (1 - 0.95) / 2);

        double intervaloConfianza = critVal * estadistica.getStandardDeviation() / Math.sqrt(estadistica.getN());
        return new Pair<>(estadistica.getMean() - intervaloConfianza, estadistica.getMean() + intervaloConfianza);
    }*/

    public Resultados getPromediosTodasEjecuciones() {
        Map<TipoModulo, Double> tamanoPromColaAcm = Arrays.stream(TipoModulo.values())
                .collect(Collectors.toMap(Function.identity(), k -> 0.0));

        Map<TipoModulo, Double> tiempoPromedioAcm = Arrays.stream(TipoModulo.values())
                .collect(Collectors.toMap(Function.identity(), d -> 0.0));

        double promedioPromediosVidaPrograma = 0;
        int numeroProgramasCompletadas = 0;

        for (Resultados resultado : resultados) {
            promedioPromediosVidaPrograma += resultado.tiempoPromedioVidaConexion;
            numeroProgramasCompletadas += resultado.numeroConexionesCompletadas;

            resultado.tamanoPromedioCola.forEach((k, v) -> tamanoPromColaAcm.put(k, tamanoPromColaAcm.get(k) + v));
        }

        promedioPromediosVidaPrograma /= resultados.size();
        numeroProgramasCompletadas /= resultados.size();

        tamanoPromColaAcm.forEach((k, v) -> tamanoPromColaAcm.put(k, v / resultados.size()));
        //tiempoPromedioAcm.forEach((modulo, map) -> map.forEach((consulta, map2) -> map.put(consulta, map2 / resultados.size())));

        return new Resultados(numeroProgramasCompletadas, promedioPromediosVidaPrograma,
                tamanoPromColaAcm/*,tiempoPromedioAcm*/);
    }
}
