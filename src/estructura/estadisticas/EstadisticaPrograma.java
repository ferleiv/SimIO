package estructura.estadisticas;

public class EstadisticaPrograma {
    private final double tiempoInicial;
    private double tiempoCPU = 0;
    private double tiempoIO = 0;
    private double tiempoLlegadaModulo;

    public EstadisticaPrograma(double tiempoInicial) {
        this.tiempoInicial = tiempoInicial;
    }

    public void setTiempoLlegadaModulo(double tiempoLlegadaModulo) { this.tiempoLlegadaModulo = tiempoLlegadaModulo; }

    public void agregarTiempoUsoCPU(double tiempo) { this.tiempoCPU += ( tiempo - tiempoLlegadaModulo ); }

    public void agregarTiempoUsoIO(double tiempo) { this.tiempoIO += ( tiempo - tiempoLlegadaModulo ); }

    public double getTiempoDeVida(double tiempo) {
        return tiempo - tiempoInicial;
    }
    public double getTiempoUsoCPU() {
        return tiempoCPU;
    }
    public double getTiempoUsoIO() {
        return tiempoIO;
    }

    public double getTiempoCPU_IO(double tiempo) {return tiempo - tiempoLlegadaModulo; }

    public double getTiempoLlegadaModulo() { return tiempoLlegadaModulo; }

    public double getTiempoDesdeLlegadaModulo(double tiempo) { return tiempo - tiempoLlegadaModulo; }
}
