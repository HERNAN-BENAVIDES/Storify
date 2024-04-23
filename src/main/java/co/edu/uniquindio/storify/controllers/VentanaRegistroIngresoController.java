package co.edu.uniquindio.storify.controller;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.exceptions.AtributoVacioException;
import co.edu.uniquindio.storify.model.Administrador;
import co.edu.uniquindio.storify.util.Alertas;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaRegistroIngresoController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void ingresarUsuario(){

        try {
            String tipoUsuario=mfm.getTiendaMusica().obtenerTipoUsuario();
            String nombreUsuario="";
            switch (tipoUsuario) {
                case "Administrador": Administrador admin= mfm.getTiendaMusica().buscarAdministrador();
                    nombreUsuario=admin.getNombre();
                    iniciarSesionAdministrador(admin); break;
                case "Cliente": Cliente cliente = mfm.getTiendaMusica().buscarCliente();
                    nombreUsuario=cliente.getNombre();
                    iniciarSesionCliente(cliente); break;
            }
            Alertas.mostrarMensaje("Ingreso Exitoso", "Operación completada", "¡Haz ingresado correctamente "+nombreUsuario+"!", Alert.AlertType.INFORMATION);

        } catch (AtributoVacioException e) {
            Alertas.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }

    }
}
