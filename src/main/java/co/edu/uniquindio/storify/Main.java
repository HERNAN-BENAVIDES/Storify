package co.edu.uniquindio.storify;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear el WebView
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Establecer una cadena de agente de usuario más moderna
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36";
        webEngine.setUserAgent(userAgent);

        // URL del video de Dailymotion
        String videoUrl = "https://www.dailymotion.com/embed/video/x8mn8ml?autoplay=1";

        // Cargar el contenido en el WebView
        webEngine.load(videoUrl);

        // Configurar el layout
        BorderPane root = new BorderPane();
        root.setCenter(webView);

        // Configurar la escena y el escenario
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Dailymotion Video Player");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
