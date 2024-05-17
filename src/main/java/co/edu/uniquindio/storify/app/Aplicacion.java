package co.edu.uniquindio.storify.app;

import co.edu.uniquindio.storify.controllers.ModelFactoryController;
import co.edu.uniquindio.storify.controllers.VentanaInicioController;
import co.edu.uniquindio.storify.controllers.VentanaRegistroController;
import co.edu.uniquindio.storify.exceptions.AtributoVacioException;
import co.edu.uniquindio.storify.exceptions.UsuarioNoExistenteException;
import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.model.Cancion;
import co.edu.uniquindio.storify.model.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Aplicacion extends Application {

    private Stage stage;
    private AnchorPane rootLayout;

    public VentanaInicioController ventanaInicioController;
    public VentanaRegistroController ventanaRegistroController;

    private ModelFactoryController mfm = ModelFactoryController.getInstance();

    public void start(Stage stage) throws Exception {
        mfm.setAplicacion(this);
        this.stage=stage;
        this.stage.setTitle("Storify");
        //this.stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/logoFinalVentana.png")));
        this.stage.setResizable(false);
        mostrarVentanaRegistroIngreso();

    }

    /**
     * Inicia la Ventana de Registro e Ingreso
     */
    public void mostrarVentanaRegistroIngreso() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/ventanaRegistro.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Storify | Inicio de Sesion");
            stage.setResizable(false);
            stage.show();
            VentanaRegistroController controlador = loader.getController();
            controlador.setVentana(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void mostrarVentanaPrincipal(Usuario usuario) throws AtributoVacioException, UsuarioNoExistenteException {
        try{
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/ventanas/VentanaInicio.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            VentanaInicioController controlador = loader.getController();
            ventanaInicioController=loader.getController();
            controlador.setUsuario(usuario);
            String tipoUsuario= mfm.getTiendaMusica().obtenerTipoUsuario(usuario.getUsername(), usuario.getPassword());

            switch (tipoUsuario) {
                case "Administrador":
                    /**
                     * controlador.mostrarPanelIzquierdoAdmin();
                     *                  controlador.mostrarPanelDerechoGestionarCanciones();
                     *                  controlador.mostrarBarraSuperiorAdmin(); break;
                     */

                case "Cliente":
                    controlador.mostrarPanelIzquierdoCliente();
                    controlador.mostrarPanelDerechoClienteFavoritos();
                    controlador.mostrarBarraSuperiorCliente(null); break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarVentanaMisCanciones(){
        ventanaInicioController.mostrarPanelDerechoClienteFavoritos();
    }

    public void mostrarVentanaCancionesGenerales(){
        ventanaInicioController.mostrarPanelDerechoCancionesGenerales();
    }

    public void abrirDetalleCancion(Cancion cancion){
        ventanaInicioController.abrirVentanaDetalleCancion(cancion);
    }

    public void mostrarVentanaArtistas(){
        ventanaInicioController.mostrarVentanaFiltrarArtistas();
    }

    public void mostrarVentanaCancionesBanda(boolean esBanda) {
        ventanaInicioController.mostrarVentanaBandas(esBanda);
    }

    public void verCancionesDeArtista(Artista artistaElegido) {
        ventanaInicioController.mostrarPanelDerechoCancionesArtista(artistaElegido);
    }
}