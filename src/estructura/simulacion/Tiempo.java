package estructura.simulacion;

public class Tiempo {
    private int conteo;
    private double tiempo;

    public Tiempo() {
        conteo = 0;
        tiempo = 0;
    }

    public void anadirTiempoAcumulado(double tiempo) {
        conteo++;
        this.tiempo += tiempo;
    }

    public double getPromedio() {
        return conteo == 0 ? 0.0 : tiempo / conteo;
    }
}
