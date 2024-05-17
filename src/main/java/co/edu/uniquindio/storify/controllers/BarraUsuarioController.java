package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.controllers.controladorFlujo.Comando;
import co.edu.uniquindio.storify.model.Usuario;
import co.edu.uniquindio.storify.util.Alertas;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
@Data

public class BarraUsuarioController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;
    private Comando comando;

    @FXML
    private Button btnDeshacer;

    @FXML
    private Button btnRehacer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDeshacer.setVisible(false);
        btnRehacer.setVisible(false);
    }

    public void cargarInfo(){
        //bienvenida de nombre y hilo tiempo
    }

    public void cargarDeshacer(){
        btnDeshacer.setVisible(true);
        btnRehacer.setVisible(false);
    }

    public void cargarRehacer(){
        btnDeshacer.setVisible(false);
        btnRehacer.setVisible(true);
    }

    public void deshacer(){
        comando.deshacer();
        cargarRehacer();
        aplicacion.ventanaInicioController.mostrarPanelDerechoClienteFavoritos();
        Alertas.mostrarMensaje("Acción Comando", "Operación completada", "¡Deshiciste correctamente la anterior acción! Puedes rehacerla si lo requieres", Alert.AlertType.INFORMATION);

    }

    public void rehacer(){
        comando.rehacer();
        cargarDeshacer();
        Alertas.mostrarMensaje("Acción Comando", "Operación completada", "¡Rehiciste correctamente la anterior acción! Puedes deshacerla si lo requieres", Alert.AlertType.INFORMATION);
        aplicacion.ventanaInicioController.mostrarPanelDerechoClienteFavoritos();
    }

    public void salirCuenta(){
        aplicacion.mostrarVentanaRegistroIngreso();
    }

}
