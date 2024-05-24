package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.model.Usuario;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

@Data

public class TableroAdminController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void gestionarCanciones(){
        aplicacion.motrarVentanaGestionCanciones();
        aplicacion.detenerVideoYoutube();

    }

    public void gestionarArtistas(){
        aplicacion.motrarVentanaGestionArtista();
        aplicacion.detenerVideoYoutube();

    }

    public void verCancionesGenerales(){
        aplicacion.mostrarVentanaCancionesGenerales();
        aplicacion.detenerVideoYoutube();
    }

    public void verEstadisticas(){
        aplicacion.mostrarVentanaEstadisticas(true);
        aplicacion.detenerVideoYoutube();

    }

    public void gestionarUsuarios(){

    }





}
