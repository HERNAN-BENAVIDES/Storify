package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.estructurasDeDatos.arbolBinario.BinaryTree;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimpleCircular;
import co.edu.uniquindio.storify.estructurasDeDatos.nodo.Node;
import co.edu.uniquindio.storify.exceptions.ArtistaNoEncontradoException;
import co.edu.uniquindio.storify.model.*;
import co.edu.uniquindio.storify.util.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Data
public class VentanaFiltrarCancionesController implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ComboBox<String> comboDuracion;

    @FXML
    private ComboBox<String> comboGenero;

    @FXML
    private ComboBox<String> comboLanzamiento;

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;
    private ListaEnlazadaSimpleCircular<Cancion> listaCancionesFavs = new ListaEnlazadaSimpleCircular<>();
    private ListaEnlazadaSimpleCircular<Cancion> listaCanciones = new ListaEnlazadaSimpleCircular<>();
    private BinaryTree<Artista> arbolArtistas= mfm.getTiendaMusica().getArtistas();
    private boolean esVentanaFavoritos=false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void iniciarGridPane() throws ArtistaNoEncontradoException {
        int column = 0;
        int row = 1;
        try {
            Node<Cancion> currentNode = listaCanciones.getHeadNode(); // Obtener el nodo de inicio de la lista
            if (currentNode != null) {
                do {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/ItemCancion.fxml"));
                    AnchorPane anchorPane = loader.load();

                    ItemCancionController controlador = loader.getController();
                    controlador.setAplicacion(this.aplicacion);
                    controlador.setUsuario(this.usuario);
                    controlador.cargarDatos(currentNode.getData()); //settea tmb la cancion en el nodo
                    controlador.setEsVentanaFavs(esVentanaFavoritos);
                    if (column == 3) {
                        column = 0;
                        row++;
                    }
                    grid.add(anchorPane, column++, row);

                    //grid width
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);
                    //grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(8, 8, 8, 8));

                    currentNode = currentNode.getNextNode(); // Avanzar al siguiente nodo
                } while (currentNode != null && currentNode != listaCanciones.getHeadNode()); // Seguir iterando hasta que se vuelva al nodo de inicio
            }

            scroll.setVvalue(0.05); //para q no se vea el espacio en blanco de 2 cm entre el filtro y el panel
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iniciarCombos(){
        //llenar combobox tipoGenero
        ObservableList<String> tipoGenero= FXCollections.observableArrayList();
        tipoGenero.addAll(mfm.getTiendaMusica().obtenerTipoGeneroCancionesSinRepetir());
        comboGenero.setItems(tipoGenero);

        //llenar combobox lanzamientos
        ObservableList<String> listaLanzamientos=FXCollections.observableArrayList();
        listaLanzamientos.addAll(mfm.getTiendaMusica().obtenerAniosLanzamientoSinRepetir());
        comboLanzamiento.setItems(listaLanzamientos);

        //llenar combobox duracion
        ObservableList<String> listaDuracion=FXCollections.observableArrayList();
        listaDuracion.addAll(mfm.getTiendaMusica().obtenerDuracionCancionesSinRepetir());
        comboDuracion.setItems(listaDuracion);

    }


    public void filtrarMaximo() throws InterruptedException, ArtistaNoEncontradoException {
        grid.getChildren().clear();
        String genero=comboGenero.getValue();
        String lanzamiento=comboLanzamiento.getValue();
        String duracion=comboDuracion.getValue();

        if (genero==null && lanzamiento==null && duracion==null) { //si se da que ninguno fue seleccioando

            Alertas.mostrarMensaje("Entradas Erroneas", "Operación Incompleta", "¡Tiene que seleccionar al menos un criterio de filtro!", Alert.AlertType.WARNING);
        }else { //en otro caso, si al menos uno de los filtros fue modificado, se cambian los que no fueron seleccionados por la variable todos

            if (genero == null) {
                genero = "Vacio";
            }
            if (lanzamiento == null) {
                lanzamiento = "Vacio";
            }
            if (duracion == null) {
                duracion = "Vacio";
            }

            //si es ventana de favoritos sera diferente de ventanageneral o ventanabandasolista
            if (esVentanaFavoritos){
                this.listaCanciones=mfm.getTiendaMusica().obtenerListaMaximoFiltroDeFavoritos(listaCancionesFavs, genero, lanzamiento,duracion);

            }else{
                this.listaCanciones=arbolArtistas.obtenerBusquedaTodosFiltros(genero, lanzamiento, duracion).convertirListaDobleASimpleCircular();

            }

            // Verificar si la lista de canciones está vacía
            if (listaCanciones.size()==0) {

                Alertas.mostrarMensaje("Filtro de canciones", "Resultados obtenidos", "No se encontraron canciones que cumplan con los creiterios de busqueda", Alert.AlertType.INFORMATION);

            } else {
                iniciarGridPane();
            }
        }

    }



    public void filtrarMinimo() throws InterruptedException, ArtistaNoEncontradoException {
        grid.getChildren().clear();
        String genero=comboGenero.getValue();
        String lanzamiento=comboLanzamiento.getValue();
        String duracion=comboDuracion.getValue();

        if (genero==null && lanzamiento==null && duracion==null) { //si se da que ninguno fue seleccioando

            Alertas.mostrarMensaje("Entradas Erroneas", "Operación Incompleta", "¡Tiene que seleccionar al menos un criterio de filtro!", Alert.AlertType.WARNING);
        }else { //en otro caso, si al menos uno de los filtros fue modificado, se cambian los que no fueron seleccionados por la variable todos

            if (genero == null) {
                genero = "Vacio";
            }
            if (lanzamiento == null) {
                lanzamiento = "Vacio";
            }
            if (duracion == null) {
                duracion = "Vacio";
            }

            //si es ventana de favoritos sera diferente de ventanageneral o ventanabandasolista
            if (esVentanaFavoritos){
                this.listaCanciones=mfm.getTiendaMusica().obtenerListaMinimoFiltroDeFavoritos(listaCancionesFavs, genero, lanzamiento,duracion);

            }else{
                this.listaCanciones=arbolArtistas.obtenerBusquedaAlMenosUnFiltro(genero, lanzamiento, duracion).convertirListaDobleASimpleCircular();

            }
            // Verificar si la lista de canciones está vacía
            if (listaCanciones.size()==0) {

                Alertas.mostrarMensaje("Filtro de canciones", "Resultados obtenidos", "No se encontraron canciones que cumplan con los creiterios de busqueda", Alert.AlertType.INFORMATION);

            } else {
                iniciarGridPane();
            }
        }

    }


    public void establecerListaCancionesFavoritas() {
        // Verificar si el usuario es un cliente
        Persona persona= usuario.getPersona();
        if (persona instanceof Cliente) {
            Cliente cliente = (Cliente) persona;
            listaCanciones = cliente.getCancionesFavoritas();
            listaCancionesFavs=cliente.getCancionesFavoritas();
        }
    }

    public void establecerListaCancionesGenerales(){
        listaCanciones= mfm.getTiendaMusica().obtenerCancionesGenerales();
    }

    public void establecerArbolPorArtista(Artista artista) {
        arbolArtistas=mfm.getTiendaMusica().obtenerArbolPorArtista(artista);
        listaCanciones= artista.getCanciones().convertirListaDobleASimpleCircular();
    }



}
