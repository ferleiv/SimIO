package estructura.estadisticas;

import estructura.tipos.TipoModulo;
import estructura.simulacion.Tiempo;

import java.util.*;
import java.util.stream.Collectors;

public class Estadisticas {
    private double lambda;
    private Tiempo promedioVidaPrograma;
    private Tiempo promedioUsoCPU;
    private Tiempo promedioUsoIO;
    private int numeroProgramasCompletados;

    private double usoCPU=0;  // Anyelo, sumatoria tiempo en cpu de programas

    public Estadisticas() {
        lambda = 0.0005; // 30 conx por minuto -> 0.0005 conx por milisegundo
        promedioVidaPrograma = new Tiempo();
        promedioUsoCPU = new Tiempo();
        promedioUsoIO = new Tiempo();
        numeroProgramasCompletados = 0;

    }

    public void anadirTiempoConsultaFinalizada(double tiempo) {
        promedioVidaPrograma.anadirTiempoAcumulado(tiempo);
    }

    public void anadirTiempoUsoCPU(double tiempo) {
        promedioUsoCPU.anadirTiempoAcumulado(tiempo);
    }

    public void anadirTiempoUsoIO(double tiempo) {
        promedioUsoIO.anadirTiempoAcumulado(tiempo);
    }

    public int getNumeroProgramasCompletados() {
        return numeroProgramasCompletados;
    }

    public void anadirNumeroConexionesCompletadas() {
        this.numeroProgramasCompletados++;
    }

    public Resultados obtenerResultados(Map<TipoModulo, EstadisticasComponente> estadisticasModulos) {
        Map<TipoModulo, Double> tamanoPromedioCola = estadisticasModulos.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().sacarTiempoPromedioCola() * lambda));

        /*Map<TipoModulo, Double> tiempoPromedioConsultaPorModulo = new EnumMap<>(TipoModulo.class);
        Arrays.stream(TipoModulo.values()).forEach(tipo ->
                tiempoPromedioConsultaPorModulo.put(tipo, estadisticasModulos.get(tipo).sacarTiempoServicioTipoConsulta()));*/

        return new Resultados(getNumeroProgramasCompletados(), promedioVidaPrograma.getPromedio(numeroProgramasCompletados),
                tamanoPromedioCola, promedioUsoCPU.getPromedio(numeroProgramasCompletados), promedioUsoIO.getPromedio(numeroProgramasCompletados)
                /*, tiempoPromedioConsultaPorModulo*/);
    }
}
