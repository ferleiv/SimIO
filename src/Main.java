import estructura.estadisticas.ResultadosFinales;
import estructura.simulacion.Ejecucion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main /*extends Application*/ {

    /*@Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("interfazGrafica/PantallaPrincipal.fxml"));
        primaryStage.setTitle("Simulación PintoDB");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.show();
    }*/

    public static void main(String[] args) {
        Ejecucion exec = new Ejecucion( 1,10000, 100 );
        ResultadosFinales RF = exec.realizarEjecucciones();
    }
}