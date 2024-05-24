package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.controllers.controladorFlujo.AdministradorComandos;
import co.edu.uniquindio.storify.controllers.controladorFlujo.Comando;
import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.model.Cancion;
import co.edu.uniquindio.storify.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
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
            controlador.setUsuario(usuario);
        }catch (Exception e){
            //log.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    public void mostrarPanelIzquierdoAdmin(){
        try {
            panelIzquierdo.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/TableroAdministrador.fxml"));
            Node node = loader.load();
            panelIzquierdo.getChildren().add(node);
            TableroAdminController controlador = loader.getController();
            controlador.setUsuario(this.usuario);
        }catch (Exception e){
            //log.severe(e.getMessage());
            e.printStackTrace();
        }
    }


    public void mostrarPanelDerechoAdminGestionarCanciones(){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaGestionar.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaGestionarController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion); //testear si cambia al llamar el de mfm
            controlador.setUsuario(this.usuario);
            controlador.establecerListaCancionesGenerales();
            controlador.iniciarGestionCanciones();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void mostrarPanelDerechoAdminGestionarArtistas(){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaGestionar.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaGestionarController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion); //testear si cambia al llamar el de mfm
            controlador.setUsuario(this.usuario);
            controlador.iniciarGestionArtistas();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Se usara el mismo metodo para canciones favoritas, y canciones generales
     */
    public void mostrarPanelDerechoClienteFavoritos(){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaFiltrarCanciones.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaFiltrarCancionesController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setUsuario(this.usuario);
            controlador.establecerListaCancionesFavoritas();
            controlador.setEsVentanaFavoritos(true);
            controlador.iniciarGridPane();
            controlador.iniciarCombos();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mostrarBarraSuperiorCliente(AdministradorComandos administrador){
        try {
            barraSuperior.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/BarraUsuario.fxml"));
            Node node = loader.load();
            barraSuperior.getChildren().add(node);
            BarraUsuarioController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion); //testear si cambia al llamar el de mfm
            controlador.setUsuario(this.usuario);
            controlador.cargarInfo();
            controlador.setAdministradorComandos(administrador);

            if (administrador!=null) {
                controlador.actualizarBotones();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mostrarBarraSuperiorAdmin(AdministradorComandos administrador){
        try {
            barraSuperior.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/BarraUsuario.fxml"));
            Node node = loader.load();
            barraSuperior.getChildren().add(node);
            BarraUsuarioController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion); //testear si cambia al llamar el de mfm
            controlador.setUsuario(this.usuario);
            controlador.cargarInfo();
            controlador.setAdministradorComandos(administrador);
            if (administrador!=null) {
                controlador.actualizarBotones();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mostrarPanelDerechoCancionesGenerales(){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaFiltrarCanciones.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaFiltrarCancionesController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setUsuario(this.usuario);
            controlador.establecerListaCancionesGenerales();
            controlador.iniciarGridPane();
            controlador.iniciarCombos();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void abrirVentanaDetalleCancion(Cancion cancion) {
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaCancionDetalle.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaCancionDetalleController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setUsuario(this.usuario);
            controlador.setCancion(cancion);
            controlador.iniciarVideoDatos();
            controlador.permitirVolverAdmin();//este ultimo boton solo funcionara para admin
            aplicacion.ventanaCancionDetalleController=loader.getController();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarVentanaFiltrarArtistas(){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaFiltrarArtistas.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaFiltrarArtistasController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setUsuario(this.usuario);
            controlador.actualizarTabla();
            controlador.actualizarCombos();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarVentanaBandas(Boolean esBanda){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaFiltrarArtistas.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaFiltrarArtistasController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setUsuario(this.usuario);

            controlador.setEsVentanaBandaSolista(true);
            controlador.ordenarVentanaBanda(esBanda);
            controlador.actualizarTabla();
            controlador.actualizarCombos();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void mostrarPanelDerechoCancionesArtista(Artista artistaElegido) {

        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaFiltrarCanciones.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaFiltrarCancionesController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setUsuario(this.usuario);
            controlador.establecerArbolPorArtista(artistaElegido);

            controlador.iniciarGridPane();
            controlador.iniciarCombos();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mostrarPanelDerechoCrearEditarCancion(Cancion cancion){

        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaCrearEditarCancion.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaCrearEditarCancionController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setUsuario(this.usuario);
            //para saber si se esta creando o editando
            controlador.setCancion(cancion);
            controlador.iniciarDatosCrearEditar();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void mostrarPanelDerechoCrearEditarArtista(Artista artista){

        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaCrearEditarArtista.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaCrearEditarArtistaController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setUsuario(this.usuario);
            //para saber si se esta creando o editando
            controlador.setArtista(artista);
            controlador.iniciarDatosCrearEditar();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void mostrarPanelDerechoEstadisticas(Boolean empiezaGenero){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaEstadisticas.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaEstadisticasController controlador = loader.getController();
            controlador.iniciarDatos();
            if (empiezaGenero){
                controlador.actualizarChartsGeneros();
            }else{
                controlador.actualizarChartsArtistas();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void mostrarVentanaPerfil() {
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaPerfilUsuario.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaPerfilUsuarioController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setUsuario(this.usuario);
            controlador.setearUsuario(this.usuario);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}