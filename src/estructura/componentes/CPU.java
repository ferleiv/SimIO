package estructura.componentes;

import estructura.evento.TipoSalidaCPU;
import estructura.simulacion.Distribuciones;
import estructura.simulacion.Evento;
import estructura.simulacion.Programa;
import estructura.Simulacion;
import estructura.evento.TipoEvento;

public class CPU extends Modulo {

    private final int timeout;
    private final int dist_option;
    private final double lambda;

    private double tiempoUso; // Anyelo

    public CPU(Simulacion simulacion, int numeroServidores, int timeout, int dist_option) {
        super(simulacion, numeroServidores);
        this.timeout = timeout;
        this.dist_option = dist_option;
        lambda = 0.03333;
        //tiempoUso=0;
    }


    //@Override
    public void procesarEntrada(Programa programa) {
        programa.setModuloActual(this);
        // Servidores disponibles
        if (numeroServidoresDisponibles > 0) {
            numeroServidoresDisponibles--;
            programa.getEstadisticaPrograma().setTiempoLlegadaModulo(simulacion.getReloj());
            generarSalida(programa);
        } else {
            // Se rechaza asignacion de CPU
            colaProgramas.add(programa);
        }
        generarEntrada();
    }

    public void generarEntrada() {
        double tiempo = 0;
        if (dist_option == 1) {
            tiempo = simulacion.getReloj() + Distribuciones.generarValorDistibucionExponencial(lambda);
        } else {
            tiempo = simulacion.getReloj() + Distribuciones.generarValorDistribucionNormal(25, 4);
        }
        Programa programa = new Programa(this,
                tiempo);
        simulacion.anadirEvento(new Evento(tiempo, this, TipoEvento.LLEGADA, programa, null));
    }

    @Override
    public void procesarSalida(Programa programa, TipoSalidaCPU tipoSalida) {
        /*estadisticasComponente.anadirTiempoServicio(
                programa.getEstadisticaPrograma().getTiempoDeVida(simulacion.getReloj()));*/
        /*simulacion.getEstadisticas().anadirTiempoUsoCPU(
                programa.getEstadisticaPrograma().getTiempoCPU_IO(simulacion.getReloj()));*/
        programa.getEstadisticaPrograma().agregarTiempoUsoCPU(simulacion.getReloj());
        if ( tipoSalida == TipoSalidaCPU.UNINTERRUPTED ) {
            this.colaProgramas.add(programa);
        } else if ( tipoSalida == TipoSalidaCPU.INTERRUPTED ) {
            programa.setModuloActual(this.siguienteModulo);
            if ( siguienteModulo.numeroServidoresDisponibles > 0 ) {
                programa.getEstadisticaPrograma().setTiempoLlegadaModulo(simulacion.getReloj());
                siguienteModulo.numeroServidoresDisponibles--;
                siguienteModulo.generarSalida(programa);
            } else {
                this.siguienteModulo.colaProgramas.add(programa);
            }
        } else if ( tipoSalida == TipoSalidaCPU.ENDED ) {
            simulacion.getEstadisticas().anadirNumeroConexionesCompletadas();
            simulacion.getEstadisticas().anadirTiempoConsultaFinalizada(
                    programa.getEstadisticaPrograma().getTiempoDeVida(simulacion.getReloj()));
            simulacion.getEstadisticas().anadirTiempoUsoCPU(programa.getEstadisticaPrograma().getTiempoUsoCPU());
            simulacion.getEstadisticas().anadirTiempoUsoIO(programa.getEstadisticaPrograma().getTiempoUsoIO());
        }
        siguientePrograma();
    }

    @Override
    protected double getTiempoSalida(Programa programa) {
        return (double)Distribuciones.generarValorDistibucionExponencial(.70);
    }

    @Override
    protected void siguientePrograma() {
        // Hay clientes esperando en fila?
        Programa siguientePrograma = getSiguientePrograma();
        if (siguientePrograma != null) {
            siguientePrograma.getEstadisticaPrograma().setTiempoLlegadaModulo(simulacion.getReloj());
            generarSalida(siguientePrograma);
        } else {
            numeroServidoresDisponibles++;
        }
    }

    @Override
    protected void generarSalida(Programa programa) {
        TipoSalidaCPU tipoSalidaCPU = Distribuciones.generarTipoSalidaCPU();
        double tiempoSalida = simulacion.getReloj() +
                (( tipoSalidaCPU != tipoSalidaCPU.ENDED ) ? Distribuciones.generarValorDistribucionUniforme(timeout/2, timeout) : timeout );
        simulacion.anadirEvento(new Evento(
                tiempoSalida,
                this,
                TipoEvento.SALIDACPU,
                programa, tipoSalidaCPU));
    }

    public void liberarConexion() {
        numeroServidoresDisponibles++;
    }
}
