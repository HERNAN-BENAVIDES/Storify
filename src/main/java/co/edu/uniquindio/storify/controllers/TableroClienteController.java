package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.model.Usuario;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

@Data

public class TablaClienteController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void mostrarFavoritos(){
        aplicacion.mostrarVentanaMisCanciones(this.usuario);
    }

}
