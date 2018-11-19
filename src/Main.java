import estructura.estadisticas.Resultados;
import estructura.estadisticas.ResultadosFinales;
import estructura.simulacion.Ejecucion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;

import static javafx.application.Application.launch;

public class Main /*extends Application*/ {

    /*@Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("interfazGrafica/PantallaPrincipal.fxml"));
        primaryStage.setTitle("Simulación PintoDB");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.show();
    }*/

    public static void get_parameters( int[] params, int phase) throws IOException {
        String output = null;
        switch (phase) {
            case 0: output = "Digite el numero de corridas de la simulación\n"; break;
            case 1: output = "Digite el tiempo máximo de simulación\n"; break;
            case 2: output = "Digite el quantum para las corridas\n"; break;
            case 3: output = "Digite # de acuerdo a la distribución que va a utilizar: \n1. Dist. Normal\n2. Dist. Exponencial\n"; break;
            default: break;
        }
        if ( phase < 4 ) {
            System.out.print(output);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //Para leer de consola
            String entrada = null;
            try {
                entrada = br.readLine(); //Lee lo que digite el usuario
            } catch ( IOException e ) {
                e.printStackTrace();
            }
            try{
                params[phase] = Integer.parseInt( entrada ); //Intenta convertirlo en entero
                if (phase == 3 && params[phase] > 2 ) {
                    System.out.print("El número debe ser 1 o 2\n");
                    get_parameters(params, phase);
                }
                get_parameters( params, ++phase );
            } catch( NumberFormatException nfe ){
                System.err.println("Formato invalido");
                get_parameters( params, phase ); //Si el formato esta mal, vuelve a solicitar el numero
            }
            br.close();
        }
    }

    public static void print_results(List<Resultados> results){
        String space = "   |   ";
        DecimalFormat df = new DecimalFormat("#.####");
        System.out.print("Resultados de cada corrida:\n");
        for ( int i = 0; i < 25; i++ ) { System.out.print(" "); }
        for ( int i = 0; i < results.size(); i++ ) { System.out.print(space + "  #" + (i+1) + "  " ); }
        System.out.print("\nTiempo prom. en sistema: ");
        for ( int i = 0; i < results.size(); i++ ) { System.out.print(space + df.format(results.get(i).tiempoPromedioVidaConexion)); }

        System.out.print("\nTiempo prom. uso CPU:    ");
        for ( int i = 0; i < results.size(); i++ ) { System.out.print(space + df.format(results.get(i).tiempoPromedioUsoCPU)); }

        System.out.print("\nOcupación del servidor:  ");

        System.out.print("\nTiempo prom. uso E/S:    ");

        System.out.print("\nTiempo prom. en colas:   ");
    }

    public static void print_final_results(ResultadosFinales RF){
        DecimalFormat df = new DecimalFormat("#.####");
        System.out.print("\n\nResultados promedio de todas las corridas:\n");
        System.out.print("\nTiempo prom. en sistema: " + df.format(RF.tiempoPromedioVidaConexion));
        System.out.print("\nTiempo prom. uso CPU:    " + df.format(RF.tiempoPromedioUsoCPU));
        System.out.print("\nOcupación del servidor:  ");
        System.out.print("\nTiempo prom. uso E/S:    ");
        System.out.print("\nTiempo prom. en colas:   ");
    }

    public static void main(String[] args) throws IOException {
        int[] params = {0,0,0,0};
        //get_parameters(params, 0);
        //Ejecucion exec = new Ejecucion( params[0], params[1], params[2], params[3] );
        Ejecucion exec = new Ejecucion( 3, 1000, 100, 1);
        ResultadosFinales RF = exec.realizarEjecucciones();
        print_results( exec.getResultados() );
        print_final_results(RF);
        //System.out.print( params[0] + "" + params [1] + "" + params[2] + "" + params[3]);
    }
}