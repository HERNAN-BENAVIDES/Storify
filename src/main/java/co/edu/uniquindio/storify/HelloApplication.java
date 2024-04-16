package co.edu.uniquindio.storify;

import co.edu.uniquindio.storify.model.Artista;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.web.WebView;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        // Crear un WebView
        WebView webView = new WebView();

        // Obtener el enlace de YouTube (reemplaza este enlace con tu propio enlace)
        String youtubeLink = "https://www.youtube.com/watch?v=VVaEQHnRtKA";

        // Obtener el ID del video de YouTube
        String videoId = obtenerIdVideo(youtubeLink);

        // Crear la URL de incrustación de YouTube
        String embedUrl = String.format("https://www.youtube.com/embed/%s?autoplay=1", videoId);

        // Cargar la página de YouTube en el WebView utilizando la URL de incrustación
        webView.getEngine().load(embedUrl);

        // Crear la escena y mostrarla en el escenario
        Scene scene = new Scene(new VBox(webView), 640, 480);
        stage.setTitle("Reproductor de YouTube");
        stage.setScene(scene);
        stage.setResizable(false); // Deshabilitar la capacidad de redimensionar la ventana
        stage.show();
    }

    // Método para obtener el ID del video de un enlace de YouTube
    private String obtenerIdVideo(String link) {
        String videoId = null;

        // Expresión regular para extraer el ID del video de un enlace de YouTube
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(link); // El enlace de YouTube

        if (matcher.find()) {
            videoId = matcher.group();
        }

        return videoId;
    }


    public static void main(String[] args) {
        launch();
    }
}
