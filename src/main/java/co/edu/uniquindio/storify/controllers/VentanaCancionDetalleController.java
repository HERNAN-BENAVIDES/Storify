package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.controllers.controladorFlujo.Comando;
import co.edu.uniquindio.storify.controllers.controladorFlujo.ComandoAgregarCancion;
import co.edu.uniquindio.storify.controllers.controladorFlujo.ComandoEliminarCancion;
import co.edu.uniquindio.storify.exceptions.ArtistaNoEncontradoException;
import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.model.Cancion;
import co.edu.uniquindio.storify.model.Cliente;
import co.edu.uniquindio.storify.model.Usuario;
import co.edu.uniquindio.storify.util.Alertas;
import co.edu.uniquindio.storify.util.YoutubeEmbedGenerator;
import com.google.api.client.http.HttpRequestInitializer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data

public class VentanaCancionDetalleController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;

    private Cancion cancion;

    @FXML
    private Text txtDuracion;

    @FXML
    private Text txtCancion;

    @FXML
    private Text txtAlbum;

    @FXML
    private WebView webView;

    @FXML
    private Text txtAnio;

    @FXML
    private Text txtArtista;

    @FXML
    private Text txtGenero;

    @FXML
    private Button btnEliminarFav;

    @FXML
    private Button btnAgregarFav;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnEliminarFav.setVisible(false);
        btnAgregarFav.setVisible(false);
    }


    public void iniciarDatosDaily() {

        iniciarTextos();
        if (cancion != null) {
            String dailymotionLink = cancion.getUrlYoutube(); //en vz de yt seria dailymotion
            String embedCode = YoutubeEmbedGenerator.obtenerEmbedCode(dailymotionLink);
            System.out.println(embedCode);
            if (embedCode != null && !embedCode.isEmpty()) {
                String htmlContent = String.format("<html><body style=\"margin:0; padding:0;\">%s</body></html>", embedCode);
                webView.getEngine().loadContent(htmlContent);
            } else {
                // Manejar el caso en el que no se pudo obtener el código de inserción
            }

        } else {
            // Manejar el caso en el que no hay enlace de YouTube disponible
        }

    }

    public void agregarFav(){
        Comando agregarCancion = new ComandoAgregarCancion((Cliente)usuario.getPersona(), cancion);
        agregarCancion.ejecutar();

        aplicacion.mostrarVentanaMisCanciones();
        Alertas.mostrarMensaje("Actualización Exitosa", "Operación completada", "¡Haz agregado correctamente una cancion a tu lista de favoritos! Puedes deshacer esta acción si lo requieres", Alert.AlertType.INFORMATION);

        aplicacion.ventanaInicioController.mostrarBarraSuperiorCliente(agregarCancion);

    }

    public void eliminarFav(){
        Comando eliminarCancion = new ComandoEliminarCancion((Cliente)usuario.getPersona(), cancion);
        eliminarCancion.ejecutar();

        aplicacion.mostrarVentanaMisCanciones();
        Alertas.mostrarMensaje("Actualización Exitosa", "Operación completada", "¡Haz eliminado correctamente una cancion de tu lista de favoritos! Puedes deshacer esta acción si lo requieres", Alert.AlertType.INFORMATION);
        aplicacion.ventanaInicioController.mostrarBarraSuperiorCliente(eliminarCancion);
    }

    public void iniciarTextos(){
        Cliente clienteUsuario= (Cliente)usuario.getPersona();
        if (clienteUsuario.getCancionesFavoritas().contains(cancion)){
            btnEliminarFav.setVisible(true);
        }else{
            btnAgregarFav.setVisible(true);
        }
        txtCancion.setText(cancion.getNombre());
        txtAlbum.setText("Album: "+cancion.getAlbum());
        txtAnio.setText("Año de lanzamiento: "+cancion.getAnioLanzamiento());
        txtDuracion.setText("Duración: "+cancion.getDuracion());
        txtGenero.setText("Género: "+cancion.obtenerGeneroComoString());
        Artista artista= null;
        try {
            artista = mfm.getTiendaMusica().buscarArtistaCancion(cancion);
        } catch (ArtistaNoEncontradoException e) {
            throw new RuntimeException(e);
        }
        txtArtista.setText("Artista: "+artista.getNombre());
    }







    /**
     * public void iniciarDatos(){
     *         if (cancion != null) {
     *             String youtubeLink = cancion.getUrlYoutube();
     *             if (youtubeLink != null && !youtubeLink.isEmpty()) {
     *                 String videoId = obtenerIdVideo(youtubeLink);
     *                 String embedUrl = String.format("https://www.youtube.com/embed/%s?autoplay=1", videoId);
     *                 webView.getEngine().load(embedUrl);
     *             } else {
     *                 // Manejar el caso en el que no hay enlace de YouTube disponible
     *             }
     *         }
     *     }
     */

}
