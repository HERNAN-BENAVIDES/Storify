package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

@Data

public class VentanaInicioController implements Initializable {

    @FXML
    private AnchorPane panelIzquierdo;

    @FXML
    private AnchorPane panelPrincipal;

    @FXML
    private AnchorPane barraSuperior;

    @FXML
    private AnchorPane panelDerecho;

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void mostrarPanelIzquierdoCliente(){
        try {
            panelIzquierdo.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/TableroCliente.fxml"));
            Node node = loader.load();
            panelIzquierdo.getChildren().add(node);
            TableroClienteController controlador = loader.getController();
            controlador.setUsuario(this.usuario);
        }catch (Exception e){
            //log.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    public void mostrarPanelDerechosMisFavoritos(Usuario usuario){
        try {
            /**
             * panelDerecho.getChildren().clear();
             *             FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaGestionReserva.fxml"));
             *             Node node = loader.load();
             *             panelDerecho.getChildren().add(node);
             *             VentanaGestionReservaController controlador = loader.getController();
             *             controlador.setAplicacion(this.aplicacion);
             *             controlador.setCliente(cliente);
             *             controlador.setAdministrador(administrador);
             *             controlador.iniciarDatos();
             */
        }catch (Exception e){
            //log.severe(e.getMessage());
            e.printStackTrace();
        }
    }


}