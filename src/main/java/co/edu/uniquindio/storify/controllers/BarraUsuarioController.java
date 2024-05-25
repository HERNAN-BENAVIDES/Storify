package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.controllers.controladorFlujo.AdministradorComandos;
import co.edu.uniquindio.storify.model.Usuario;
import co.edu.uniquindio.storify.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Data;
import java.net.URL;
import java.util.ResourceBundle;
@Data

public class BarraUsuarioController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;
    private AdministradorComandos administradorComandos;


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

    public void actualizarBotones(){
        aplicacion.detenerVideoYoutube();

        btnDeshacer.setVisible(!administradorComandos.getPilaDeshacer().isEmpty());
        btnRehacer.setVisible(!administradorComandos.getPilaRehacer().isEmpty());
    }

    public void deshacer(){
        aplicacion.detenerVideoYoutube();
        administradorComandos.deshacer();
        actualizarBotones();
        aplicacion.ventanaInicioController.mostrarPanelDerechoClienteFavoritos();
        Alertas.mostrarMensaje("Acción Comando", "Operación completada", "¡Deshiciste correctamente la anterior acción! Puedes rehacerla si lo requieres", Alert.AlertType.INFORMATION);

    }

    public void rehacer(){
        aplicacion.detenerVideoYoutube();

        administradorComandos.rehacer();
        actualizarBotones();
        Alertas.mostrarMensaje("Acción Comando", "Operación completada", "¡Rehiciste correctamente la anterior acción! Puedes deshacerla si lo requieres", Alert.AlertType.INFORMATION);
        aplicacion.ventanaInicioController.mostrarPanelDerechoClienteFavoritos();
    }

    public void salirCuenta(){
        aplicacion.detenerVideoYoutube();

        aplicacion.mostrarVentanaRegistroIngreso();
    }

}
