package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.exceptions.AtributoVacioException;
import co.edu.uniquindio.storify.exceptions.UsuarioNoExistenteException;
import co.edu.uniquindio.storify.model.Administrador;
import co.edu.uniquindio.storify.model.Cliente;
import co.edu.uniquindio.storify.model.Usuario;
import co.edu.uniquindio.storify.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
@Data

public class VentanaRegistroIngresoController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtContrasenia;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void ingresar(){

        try {
            //String tipoUsuario=mfm.getTiendaMusica().obtenerTipoUsuario(txtUsername.getText(), txtContrasenia.getText());
            Usuario usuario= mfm.getTiendaMusica().buscarUsuario(txtUsername.getText(), txtContrasenia.getText());
            String nombreUsuario=usuario.getPersona().getNombre();
            iniciarSesionUsuario(usuario);
            /**
             * switch (tipoUsuario) {
             *                 case "Administrador":
             *                     //iniciarSesionAdministrador(usuario); break;
             *                 case "Cliente":
             *                     iniciarSesionCliente(usuario); break;
             *             }
             */
            Alertas.mostrarMensaje("Ingreso Exitoso", "Operación completada", "¡Haz ingresado correctamente "+nombreUsuario+"!", Alert.AlertType.INFORMATION);

        } catch (AtributoVacioException | UsuarioNoExistenteException e) {
            Alertas.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    /**
     * Inicia sesión para un usuario.
     * @param usuario El objeto Usuario correspondiente al usuario que ha iniciado sesión.
     */
    public void iniciarSesionUsuario(Usuario usuario) throws AtributoVacioException, UsuarioNoExistenteException {
        ventana.close();
        aplicacion.mostrarVentanaPrincipal(usuario);
    }
}
