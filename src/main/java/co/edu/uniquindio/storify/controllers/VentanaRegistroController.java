package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.exceptions.*;
import co.edu.uniquindio.storify.model.Cliente;
import co.edu.uniquindio.storify.model.TiendaMusica;
import co.edu.uniquindio.storify.model.Usuario;
import co.edu.uniquindio.storify.util.Alertas;
import co.edu.uniquindio.storify.util.StorifyUtil;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
@Data

public class VentanaRegistroController implements Initializable {

    public Pane paneCrearCuenta;
    public TextField txtShowPassword;
    public Button btnRegistrar;
    public TextField txtNombre;
    public TextField txtCorreo;
    public PasswordField txtHidePassword;
    public TextField txtApellido;
    public Pane paneIngresar;
    public TextField txtUsername2;
    public TextField txtShowPassword2;
    public PasswordField txtHidePassword2;
    public AnchorPane paneSolid;
    public Label txtNoRegistrado;
    public Button btnYaTengoCuenta;
    public Button btnRegistrarmePanel;
    public Label txtYaRegistrado;
    public ImageView openEye1;
    public ImageView closeEye1;
    public ImageView openEye2;
    public ImageView closeEye2;
    public String password2;
    public String password;
    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    public TiendaMusica tiendaMusica = mfm.getTiendaMusica();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuarioSesion;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtContrasenia;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtShowPassword2.setVisible(false);
        closeEye1.setVisible(true);
        closeEye2.setVisible(true);
        openEye2.setVisible(false);
        txtShowPassword.setVisible(false);
        openEye1.setVisible(false);
    }

    @FXML
    public void ingresarUsuario(){

        try {
            Usuario usuario = mfm.getTiendaMusica().buscarUsuario(txtUsername2.getText(), txtShowPassword2.getText());
            String nombreUsuario=usuario.getPersona().getNombre();
            iniciarSesionUsuario(usuario); // Guardar el usuario que inició sesión
            Alertas.mostrarMensaje("Ingreso Exitoso", "Operación completada", "¡Haz ingresado correctamente "+nombreUsuario+"!", Alert.AlertType.INFORMATION);

        } catch (AtributoVacioException | UsuarioNoExistenteException e) {
            Alertas.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método para guardar el usuario que inició sesión en la variable de instancia
    public void iniciarSesionUsuario(Usuario usuario) throws AtributoVacioException, UsuarioNoExistenteException {
        this.usuarioSesion = usuario;
        ventana.close();
        aplicacion.mostrarVentanaPrincipal(usuario);
    }


    public void switchFormbtnIngresarPanel(){
        TranslateTransition slider= new TranslateTransition();
        slider.setNode(paneSolid);
        slider.setToX(0);
        slider.setDuration(Duration.seconds(.7));
        slider.setOnFinished(event -> {
            btnYaTengoCuenta.setVisible(false);
            txtYaRegistrado.setVisible(false);
            btnRegistrarmePanel.setVisible(true);
            txtNoRegistrado.setVisible(true);
            limpiarRegistrarPanel();

        });

        slider.play();

    }

    public void switchFormbtnRegistrarmePanel(){
        TranslateTransition slider= new TranslateTransition();

        slider.setNode(paneSolid);
        slider.setToX(370);
        slider.setDuration(Duration.seconds(.7));
        slider.setOnFinished(event -> {
            btnYaTengoCuenta.setVisible(true);
            txtYaRegistrado.setVisible(true);
            btnRegistrarmePanel.setVisible(false);
            txtNoRegistrado.setVisible(false);
            limpiarIngresarPanel();
        });

        slider.play();

    }
    @FXML
    public void Open_Eye_OnClickAction2(MouseEvent mousevent) {
        txtShowPassword2.setVisible(false);
        openEye1.setVisible(false);
        closeEye1.setVisible(true);
        txtHidePassword2.setVisible(true);

        txtShowPassword.setVisible(false);
        openEye2.setVisible(false);
        closeEye2.setVisible(true);
        txtHidePassword.setVisible(true);

    }
    @FXML
    public void Close_Eye_ClickOnAction2(MouseEvent mousevent) {
        txtShowPassword2.setVisible(true);
        openEye1.setVisible(true);
        closeEye1.setVisible(false);
        txtHidePassword2.setVisible(false);

        txtShowPassword.setVisible(true);
        openEye2.setVisible(true);
        closeEye2.setVisible(false);
        txtHidePassword.setVisible(false);
    }
    @FXML
    public void ShowPasswordOnAction2(KeyEvent keyevent) {
        password2 = txtShowPassword2.getText();
        txtHidePassword2.setText(password2);

        password = txtShowPassword.getText();
        txtHidePassword.setText(password);
    }
    @FXML
    public void HidePasswordKeyReleased2(KeyEvent keyevent) {

        password2 = txtHidePassword2.getText();
        txtShowPassword2.setText(String.valueOf(password2));

        password = txtHidePassword.getText();
        txtShowPassword.setText(String.valueOf(password));
    }
    public void limpiarIngresarPanel(){
        txtUsername2.clear();
        txtShowPassword2.clear();
        txtHidePassword2.clear();
        password2="";
    }
    public void limpiarRegistrarPanel(){
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        txtUsername.clear();
        txtShowPassword.clear();
        txtHidePassword.clear();
        password="";
    }
    public void registrarCliente(){
        try {
            Cliente cliente = tiendaMusica.crearCliente(txtNombre.getText(), txtApellido.getText());
            Usuario usuario = tiendaMusica.crearUsuario(txtUsername.getText(), txtShowPassword.getText(), txtCorreo.getText(), cliente);
            tiendaMusica.agregarUsuario(usuario);
            Alertas.mostrarMensaje("Registro Confirmado", "Operación completada", "Se ha registrado correctamente el cliente: "+cliente.getNombre(), Alert.AlertType.INFORMATION);
            mfm.guardarDatosBinario();
            limpiarRegistrarPanel();

        } catch (AtributoVacioException | UsuarioYaRegistradoException e) {
            Alertas.mostrarAlertaError(e.getMessage());
        }

    }

}
