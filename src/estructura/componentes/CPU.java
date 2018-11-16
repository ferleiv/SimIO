package estructura.componentes;

import estructura.simulacion.Distribuciones;
import estructura.simulacion.Evento;
import estructura.simulacion.Programa;
import estructura.Simulacion;
import estructura.evento.TipoEvento;

public class CPU extends Modulo {

    private final int timeout;
    private final double lambda;

    public CPU(Simulacion simulacion, int numeroServidores, int timeout) {
        super(simulacion, numeroServidores);
        this.timeout = timeout;
        lambda = 0.03333;
    }

    public void setSiguienteModulo(Modulo siguienteModulo) {
        this.siguienteModulo = siguienteModulo;
    }

    //@Override
    public void procesarEntrada(Programa programa) {
            // Servidores disponibles?
            if (numeroServidoresDisponibles > 0) {
                numeroServidoresDisponibles--;
                generarTimeout(programa);
                //siguienteModulo.procesarEntrada(programa);
            } else {
                // Se rechaza conexion
                //simulacion.getEstadisticas().anadirConexionDescartada();
                colaProgramas.add(programa);
            }
            generarEntrada();
            programa.setModuloActual(this);
            generarSalida(programa);
        }

    public void generarEntrada() {
        double tiempo = simulacion.getReloj() + Distribuciones.generarValorDistibucionExponencial(lambda);
        Programa programa = new Programa(this,
                tiempo);
        simulacion.anadirEvento(new Evento(tiempo, this, TipoEvento.LLEGADA, programa));
    }

    @Override
    public void procesarSalida(Programa programa) {

        estadisticasComponente.anadirTiempoServicio(
                programa.getEstadisticaPrograma().getTiempoDeVida(simulacion.getReloj()));

        simulacion.getEstadisticas().anadirNumeroConexionesCompletadas();
        simulacion.getEstadisticas().anadirTiempoConsultaFinalizada(
                programa.getEstadisticaPrograma().getTiempoDeVida(simulacion.getReloj()));
        simulacion.eliminarEvento(programa);

        liberarConexion();
    }

    @Override
    protected double getTiempoSalida(Programa programa) {
        return (double)Distribuciones.generarValorDistibucionExponencial(.70);
    }

    private void generarTimeout(Programa programa) {
        simulacion.anadirEvento(new Evento(
                simulacion.getReloj() + timeout,
                null,
                TipoEvento.TIMEOUT,
                programa));
    }

    public void liberarConexion() {
        numeroServidoresDisponibles++;
    }
}
