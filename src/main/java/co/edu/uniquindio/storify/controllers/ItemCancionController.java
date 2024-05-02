package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.exceptions.ArtistaNoEncontradoException;
import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.model.Cancion;
import co.edu.uniquindio.storify.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
@Data

public class ItemCancionController implements Initializable {

    @FXML
    private Text lblAutorNombre;

    @FXML
    private Text lblNombreCancion;

    @FXML
    private ImageView portada;

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;
    private Cancion cancion= null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cargarDatos(Cancion cancion) throws ArtistaNoEncontradoException {
        this.cancion=cancion;

        String nombreCancion= cancion.getNombre();
        Artista nombreAutor= mfm.getTiendaMusica().buscarArtistaCancion(cancion);
        lblNombreCancion.setText(nombreCancion);
        lblAutorNombre.setText(nombreAutor.getNombre());

        //para insertar la foto en el imageview
        String foto=cancion.getCaratula();
        try {
            Image image = new Image(getClass().getResourceAsStream(foto));
            portada.setImage(image);
        } catch (Exception e) {
            if (!foto.isEmpty()) {
                Image image = new Image(foto);
                portada.setImage(image);
            }
        }
    }
}


