package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.exceptions.AtributoVacioException;
import co.edu.uniquindio.storify.exceptions.EmailInvalidoException;
import co.edu.uniquindio.storify.exceptions.UsuarioNoExistenteException;
import co.edu.uniquindio.storify.model.TiendaMusica;
import co.edu.uniquindio.storify.model.Usuario;
import co.edu.uniquindio.storify.util.Alertas;
import co.edu.uniquindio.storify.util.ArchivoUtil;
import co.edu.uniquindio.storify.util.StorifyUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
@Data
public class VentanaPerfilUsuarioController implements Initializable {
    public TextField txtNombre;
    public TextField txtApellido;
    public ImageView btnFavoritos;
    public TextField txtCorreo;
    public TextField txtUsername;
    public TextField txtShowPassword;
    public PasswordField txtHidePassword;
    public ImageView closeEye;
    public ImageView openEye;
    public Button btnEditar;
    public Button btnConfirmar;
    public String password;
    public Usuario usuario;
    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    public TiendaMusica tiendaMusica = mfm.getTiendaMusica();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtCorreo.setEditable(false);
        txtHidePassword.setEditable(false);
        txtShowPassword.setEditable(false);
        txtUsername.setEditable(false);
        closeEye.setVisible(true);
        openEye.setVisible(false);
        txtShowPassword.setVisible(false);
    }
    public void setearUsuario(Usuario usuario) {
        this.usuario = usuario;
        cargarDatos(); // Cargar los datos del usuario al iniciar el controlador
        txtShowPassword.setVisible(false);
        openEye.setVisible(false);
        password=usuario.getPassword();
    }
    public void cargarDatos(){
        txtNombre.setText(usuario.getPersona().getNombre());
        txtApellido.setText(usuario.getPersona().getApellido());
        txtCorreo.setText(usuario.getEmail());
        txtUsername.setText(usuario.getUsername());
        txtHidePassword.setText(usuario.getPassword());
        txtShowPassword.setText(usuario.getPassword());
    }

    @FXML
    public void ShowPasswordOnAction(KeyEvent keyevent) {
        password = txtShowPassword.getText();
        txtHidePassword.setText(password);
        password = txtShowPassword.getText();
        txtHidePassword.setText(password);
    }

    public void confirmarEdicion(ActionEvent actionEvent) {
        try {
            Usuario usuario1 = tiendaMusica.editarUsuario(usuario,
                    txtUsername.getText(),
                    txtShowPassword.getText(),
                    txtCorreo.getText(),
                    txtNombre.getText(),
                    txtApellido.getText());
            Alertas.mostrarMensaje("Registro Confirmado", "Operación completada", "Se ha editado correctamente al cliente: " + usuario1.getUsername(), Alert.AlertType.INFORMATION);
            aplicacion.mostrarVentanaPrincipal(usuario1);
        } catch (EmailInvalidoException | AtributoVacioException | UsuarioNoExistenteException e) {
            throw new RuntimeException(e);
        }

    }

    public void habilitarEdicion() throws AtributoVacioException {
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtCorreo.setEditable(true);
        txtUsername.setEditable(true);
        txtHidePassword.setEditable(true);
        txtShowPassword.setEditable(true);
        btnEditar.setVisible(false);
        btnConfirmar.setVisible(true);
    }

    @FXML
    public void HidePasswordKeyReleased(KeyEvent keyevent) {
        password = txtHidePassword.getText();
        txtShowPassword.setText(String.valueOf(password));
        password = txtHidePassword.getText();
        txtShowPassword.setText(String.valueOf(password));
    }

    @FXML
    public void Open_Eye_OnClickAction(MouseEvent mousevent) {
        txtShowPassword.setVisible(false);
        openEye.setVisible(false);
        closeEye.setVisible(true);
        txtHidePassword.setVisible(true);
    }
    @FXML
    public void Close_Eye_OnClickAction(MouseEvent mousevent) {
        txtShowPassword.setVisible(true);
        openEye.setVisible(true);
        closeEye.setVisible(false);
        txtHidePassword.setVisible(false);
    }

    public void mostrarVentanaFavoritos(MouseEvent mouseEvent) {
        aplicacion.mostrarVentanaMisCanciones();
    }
}