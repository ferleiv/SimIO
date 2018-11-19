package estructura.componentes;

import estructura.evento.TipoSalidaCPU;
import estructura.simulacion.Evento;
import estructura.simulacion.Programa;
import estructura.Simulacion;
import javafx.util.Pair;
import estructura.evento.TipoEvento;
import estructura.estadisticas.EstadisticasComponente;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Modulo {
    protected final Simulacion simulacion;
    Queue<Programa> colaProgramas;
    Modulo siguienteModulo;
    int numeroServidoresDisponibles;
    protected final int numeroServidoresTotales;
    protected EstadisticasComponente estadisticasComponente;

    public Modulo(Simulacion simulacion, int numeroServidores) {
        this.simulacion = simulacion;
        numeroServidoresTotales = numeroServidores;
        numeroServidoresDisponibles = numeroServidores;

        estadisticasComponente = new EstadisticasComponente();
        colaProgramas = new LinkedList<>();
    }

    public Modulo(Simulacion simulacion, Modulo siguienteModulo, int numeroServidores) {
        this(simulacion, numeroServidores);
        this.siguienteModulo = siguienteModulo;
    }

    public void setSiguienteModulo(Modulo siguienteModulo) {
        this.siguienteModulo = siguienteModulo;
    }

   public void procesarEntrada(Programa programa) {
        /*programa.getEstadisticaPrograma().setTiempoLlegadaModulo(simulacion.getReloj());
        programa.setModuloActual(this);
        // Servidores disponibles?
        if (numeroServidoresDisponibles > 0) {
            numeroServidoresDisponibles--;
            generarSalida(programa);

        } else {
            colaProgramas.add(programa);
        }*/
    }

    public void procesarSalida(Programa programa, TipoSalidaCPU tipoSalida) {
        /*// Anade tiempo de servicio
        estadisticasComponente.anadirTiempoServicio(
                programa.getEstadisticaPrograma().getTiempoDesdeLlegadaModulo(simulacion.getReloj()));

        //finalizacionProgramaProcesado(programa);
        siguientePrograma();*/
    }

    protected void siguientePrograma() {
        // Hay clientes esperando en fila?
        Programa siguientePrograma = getSiguientePrograma();
        if (siguientePrograma != null) {
            estadisticasComponente.anadirTiempoClienteEnCola(
                    siguientePrograma.getEstadisticaPrograma().getTiempoDesdeLlegadaModulo(simulacion.getReloj()));
            generarSalida(siguientePrograma);
        } else {
            numeroServidoresDisponibles++;
        }
    }

    /*private void finalizacionProgramaProcesado(Programa programa) {
            siguienteModulo.procesarEntrada(programa);
    }*/

    protected void generarSalida(Programa programa) {
        double tiempo = getTiempoSalida(programa);
        simulacion.anadirEvento(new Evento(
                simulacion.getReloj() + tiempo,
                this,
                TipoEvento.SALIDAIO,
                programa,null
        ));
    }

    protected abstract double getTiempoSalida(Programa programa);

    protected Programa getSiguientePrograma() {
        return colaProgramas.poll();
    }


    protected void terminarPrograma(Programa programa) {
        simulacion.getEstadisticas().anadirTiempoConsultaFinalizada(
                programa.getEstadisticaPrograma().getTiempoDeVida(simulacion.getReloj()));
    }

    public EstadisticasComponente getEstadisticasComponente() {
        return estadisticasComponente;
    }

    public void limpiarModulo() {
        colaProgramas.clear();
        estadisticasComponente = new EstadisticasComponente();
        numeroServidoresDisponibles = numeroServidoresTotales;
    }

    public Pair<Integer, Integer> datosActuales() {
        // Pair<Consultas siendo atendidas, Tamano de la cola>
        return new Pair<>(numeroServidoresTotales - numeroServidoresDisponibles, colaProgramas.size());
    }
}
