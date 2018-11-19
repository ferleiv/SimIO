package estructura;

import estructura.componentes.CPU;
import estructura.componentes.DispositivoIO;
import estructura.componentes.Modulo;
import estructura.simulacion.Evento;
import estructura.simulacion.Programa;
import estructura.tipos.TipoModulo;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.util.Pair;
import estructura.estadisticas.Datos;
import estructura.estadisticas.Estadisticas;
import estructura.estadisticas.Resultados;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class Simulacion {
    private double reloj;
    private final Queue<Evento> colaEventos;
    private final Map<TipoModulo, Modulo> modulos;
    private Estadisticas estadisticas;

    private final long tiempoTotal;
    private final int dist_option;

    private Datos resultadosParcialesMasRecientes;
    private final ReadOnlyLongWrapper progress;

    public Simulacion(int tiempoTotal, int quantum, int dist_option) {
        reloj = 0;
        this.tiempoTotal = tiempoTotal;
        this.dist_option = dist_option;
        colaEventos = new PriorityQueue<>();
        estadisticas = new Estadisticas();
        modulos = new EnumMap<>(TipoModulo.class);
        inicializadorModulos(quantum, dist_option);
        progress = new ReadOnlyLongWrapper(this, "progress");
    }

    private void inicializadorModulos(int timeout, int dist_option) {

        Modulo moduloCPU = new CPU(this, 1, timeout, dist_option);
        Modulo moduloIO = new DispositivoIO(this, moduloCPU, 1); // 2
        ((CPU) moduloCPU).setSiguienteModulo(moduloIO); // 1

        modulos.put(TipoModulo.CPU, moduloCPU);
        modulos.put(TipoModulo.IO, moduloIO);
    }

    public Resultados realizarSimulacion() {
        ((CPU) modulos.get(TipoModulo.CPU)).generarEntrada(); // Primera llegada
        progress.set((long) reloj);
        while (colaEventos.peek().getTiempoEvento() < tiempoTotal) {
            Evento eventoActual = colaEventos.poll();
            reloj = eventoActual.getTiempoEvento();
            System.out.print( Double.toString(getReloj()) + "\t\t\t" + eventoActual.getTipoEvento()
                     + "\t\t" + eventoActual.getModulo().datosActuales()
                    + "\t\tSacar tiempo servicio \t\t\t" + eventoActual.getModulo().getEstadisticasComponente().sacarTiempoPromedioServicio()
                    + "\t\tSacar tiempo USO CPU \t\t\t" + ((CPU) modulos.get(TipoModulo.CPU)).getTiempoUso()
                    + "\n");

            switch (eventoActual.getTipoEvento()) {
                case LLEGADA:
                    eventoActual.getModulo().procesarEntrada(eventoActual.getPrograma());
                    break;
                case SALIDA:
                    eventoActual.getModulo().procesarSalida(eventoActual.getPrograma());
                    break;
                case TIMEOUT:
                    eventoActual.getPrograma().getModuloActual().procesarSalida(eventoActual.getPrograma());
                    break;
            }
            resultadosParcialesMasRecientes = retornarDatosParciales();
            progress.set((long) reloj);
        }

        estadisticas.añadirTiempoUsoCPU(((CPU) modulos.get(TipoModulo.CPU)).getTiempoUso());


        Resultados resultados = estadisticas.obtenerResultados(modulos.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().getEstadisticasComponente())));



        progress.set(tiempoTotal);

        limpiarSimulacion();
        modulos.forEach((tipoModulo, modulo) -> modulo.limpiarModulo());


        return resultados;
    }

    private Datos retornarDatosParciales() {
        Map<TipoModulo, Pair<Integer, Integer>> informacionPorModulo = new EnumMap<>(TipoModulo.class);
        Arrays.stream(TipoModulo.values()).forEach(tipo ->
                informacionPorModulo.put(tipo, modulos.get(tipo).datosActuales()));

        return new Datos(reloj, estadisticas.getNumeroProgramasCompletados(),
                informacionPorModulo);
    }

    public Datos getResultadosParcialesMasRecientes() {
        return resultadosParcialesMasRecientes;
    }

    public double getReloj() { return reloj; }

    public long getTiempoTotal() {
        return tiempoTotal;
    }

    public void anadirEvento(Evento evento) {
        colaEventos.add(evento);
    }

    public void eliminarEvento(Programa programa) {
        colaEventos.removeIf(evento -> evento.getPrograma() == programa);
    }

    public Estadisticas getEstadisticas() {
        return estadisticas;
    }

    private void limpiarSimulacion() {
        reloj = 0;
        colaEventos.clear();
        estadisticas = new Estadisticas();
    }
}
