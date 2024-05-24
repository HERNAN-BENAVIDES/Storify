package co.edu.uniquindio.storify.app;

import co.edu.uniquindio.storify.controllers.ModelFactoryController;
import co.edu.uniquindio.storify.controllers.VentanaCancionDetalleController;
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
    public VentanaCancionDetalleController ventanaCancionDetalleController;

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
            System.out.println(tipoUsuario);
            switch (tipoUsuario) {
                case "Administrador":
                    System.out.println("inicio panel admin");
                    controlador.mostrarPanelIzquierdoAdmin();
                    controlador.mostrarPanelDerechoAdminGestionarCanciones();
                    controlador.mostrarBarraSuperiorAdmin(null);
                    break;

                case "Cliente":
                    System.out.println("inicio panel cliente");
                    controlador.mostrarPanelIzquierdoCliente();
                    controlador.mostrarPanelDerechoClienteFavoritos();
                    controlador.mostrarBarraSuperiorCliente(null); break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarVentanaCrearEditarCancion(Cancion cancion){
        ventanaInicioController.mostrarPanelDerechoCrearEditarCancion(cancion);

    }

    public void mostrarVentanaCrearEditarArtista(Artista artista){
        ventanaInicioController.mostrarPanelDerechoCrearEditarArtista(artista);
    }

    public void mostrarVentanaMisCanciones(){
        ventanaInicioController.mostrarPanelDerechoClienteFavoritos();
    }

    public void mostrarVentanaCancionesGenerales(){
        ventanaInicioController.mostrarPanelDerechoCancionesGenerales();
    }

    public void mostrarVentanaEstadisticas(Boolean empiezaGenero){
        ventanaInicioController.mostrarPanelDerechoEstadisticas(empiezaGenero);

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

    public void motrarVentanaGestionCanciones(){
        ventanaInicioController.mostrarPanelDerechoAdminGestionarCanciones();
    }

    public void motrarVentanaGestionArtista(){
        ventanaInicioController.mostrarPanelDerechoAdminGestionarArtistas();

    }

    public void detenerVideoYoutube(){
        if (ventanaCancionDetalleController!=null){
            ventanaCancionDetalleController.stopWebView();
        }
    }
}