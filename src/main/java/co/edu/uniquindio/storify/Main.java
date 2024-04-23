package co.edu.uniquindio.storify;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Especifica la ruta del archivo FXML
            String fxmlPath = "src/main/resources/ventanas/VentanaInicio.fxml";
            URL location = getClass().getResource(fxmlPath);

            // Carga el archivo FXML
            FXMLLoader loader = new FXMLLoader(location);
            AnchorPane root = loader.load();

            // Configura la escena
            Scene scene = new Scene(root, 400, 300);
            primaryStage.setScene(scene);

            // Establece el título de la ventana
            primaryStage.setTitle("Mi Ventana JavaFX");

            // Muestra la ventana
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
