package estructura.estadisticas;

import estructura.tipos.TipoModulo;

import java.util.Map;

public class Resultados {
    public final Map<TipoModulo, Double> tamanoPromedioCola;
    public final double tiempoPromedioVidaConexion;
    public final int numeroConexionesCompletadas;
    public final double tiempoPromedioUsoCPU;
    public final double tiempoPromedioUsoIO;
    //Map<TipoModulo, Map<TipoConsulta, Double>> tiempoPromedioPorTipoConsultas


    public Resultados(int numeroProgramasCompletados, double tiempoPromedioVidaConexion,
                      Map<TipoModulo, Double> tamanoPromedioCola,
                      double tiempoPromedioUsoCPU, double tiempoPromedioUsoIO/*
                      Map<TipoModulo, Map<TipoConsulta, Double>> tiempoPromedioPorTipoConsultas*/) {
        this.tamanoPromedioCola = tamanoPromedioCola;
        this.tiempoPromedioVidaConexion = tiempoPromedioVidaConexion;
        this.numeroConexionesCompletadas = numeroProgramasCompletados;
        this.tiempoPromedioUsoCPU = tiempoPromedioUsoCPU;
        this.tiempoPromedioUsoIO = tiempoPromedioUsoIO;
        //this.tiempoPromedioPorTipoConsultas = tiempoPromedioPorTipoConsultas;
    }
}
