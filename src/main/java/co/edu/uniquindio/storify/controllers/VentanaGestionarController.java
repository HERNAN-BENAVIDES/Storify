package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.controllers.controladorListeners.MyListenerArtista;
import co.edu.uniquindio.storify.controllers.controladorListeners.MyListenerCancion;
import co.edu.uniquindio.storify.estructurasDeDatos.arbolBinario.BinaryTree;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimpleCircular;
import co.edu.uniquindio.storify.estructurasDeDatos.nodo.Node;
import co.edu.uniquindio.storify.exceptions.ArtistaNoEncontradoException;
import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.model.Cancion;
import co.edu.uniquindio.storify.model.Usuario;
import co.edu.uniquindio.storify.util.Alertas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
@Data

public class VentanaGestionarController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;
    private ListaEnlazadaSimpleCircular<Cancion> listaCanciones = new ListaEnlazadaSimpleCircular<>();
    BinaryTree<Artista> listaArtistas= mfm.getTiendaMusica().getArtistas();
    private Boolean esGestionCanciones;
    private Boolean esGestionArtistas;

    //DATOS VALOR CAMBIANTE PARA LAS VENTANAS
    public Cancion cancionElegida;
    public Artista artistaElegido;

    //listeners
    private MyListenerCancion myListenerCancion;
    private MyListenerArtista myListenerArtista;

    @FXML
    private Button btnDetalles;

    @FXML
    private Text txtAdvertencia;

    @FXML
    private Button btnEliminar;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scrollGrid;

    @FXML
    private ScrollPane scrollTabla;

    @FXML
    private Button btnCrear;

    @FXML
    private Text txtTituloGestion;

    @FXML
    private Button btnEditar;

    @FXML
    private TableView<Artista> tablaArtistas;

    @FXML
    private TableColumn<Artista, String> columnaTipoArtista;

    @FXML
    private TableColumn<Artista, String> columnaCodigo;

    @FXML
    private TableColumn<Artista, String> columnaNombre;

    @FXML
    private TableColumn<Artista, String> columnaNacionalidad;

    @FXML
    private TableColumn<Artista, String> columnaNumCanciones;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //inicializan ambas, scroll y tabla, como invisibles
        tablaArtistas.setVisible(false);
        grid.setVisible(false);
        scrollGrid.setVisible(false);
        scrollTabla.setVisible(false);

    }

    public void iniciarGestionCanciones() {
        esGestionCanciones=true;
        esGestionArtistas=false;
        txtAdvertencia.setText("Antes de elegir una opción de gestión, tenga en cuenta que debe seleccionar una canción");
        txtTituloGestion.setText("Gestión de Canciones");
        iniciarScrollCanciones();
        grid.setVisible(true);
        scrollGrid.setVisible(true);
        tablaArtistas.setVisible(false);
        scrollTabla.setVisible(false);

    }

    public void iniciarTablaArtistas(){
        tablaArtistas.getItems().clear();
        ObservableList<Artista> listaArtistasProperty= FXCollections.observableArrayList();
        // Asignar las propiedades de las columnas
        columnaTipoArtista.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().obtenerTipoArtistaString()));
        columnaCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnaNacionalidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNacionalidad()));
        columnaNumCanciones.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().obtenerCantidadCanciones())));

        listaArtistasProperty.clear();
        listaArtistasProperty.addAll(mfm.getTiendaMusica().convertirArbolALista(listaArtistas));
        tablaArtistas.setItems(listaArtistasProperty);
    }

    public void iniciarGestionArtistas(){
        esGestionArtistas=true;
        esGestionCanciones=false;
        txtAdvertencia.setText("Antes de elegir una opción de gestión, tenga en cuenta que debe seleccionar un artista");
        txtTituloGestion.setText("Gestión de Artistas");
        grid.setVisible(false);
        scrollGrid.setVisible(false);
        tablaArtistas.setVisible(true);
        scrollTabla.setVisible(true);
        iniciarTablaArtistas();

    }

    public void iniciarScrollCanciones()  {
        establecerListaCancionesGenerales();
        if (listaCanciones.size() > 0) {
            myListenerCancion = new MyListenerCancion() {
                @Override
                public void onClickListener(Cancion cancion) {
                    setCancionElegida(cancion);
                }
            };
        }

        int column = 0;
        int row = 1;
        try {
            Node<Cancion> currentNode = listaCanciones.getHeadNode(); // Obtener el nodo de inicio de la lista
            if (currentNode != null) {
                do {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/ItemCancion.fxml"));
                    AnchorPane anchorPane = loader.load();

                    ItemCancionController controlador = loader.getController();
                    controlador.setEsGestion(true);
                    controlador.setMyListenerCancion(myListenerCancion);
                    controlador.setAplicacion(this.aplicacion);
                    controlador.setUsuario(this.usuario);
                    controlador.cargarDatos(currentNode.getData());

                    if (column == 3) {
                        column = 0;
                        row++;
                    }
                    grid.add(anchorPane, column++, row);

                    // Ajustar el ancho del grid
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);
                    // Ajustar la altura del grid
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(8, 8, 8, 8));

                    currentNode = currentNode.getNextNode(); // Avanzar al siguiente nodo
                } while (currentNode != null && currentNode != listaCanciones.getHeadNode()); // Seguir iterando hasta que se vuelva al nodo de inicio
            }

            scrollGrid.setVvalue(0.05); //para que no se vea el espacio en blanco de 2 cm entre el filtro y el panel
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArtistaNoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    public void establecerListaCancionesGenerales(){
        listaCanciones= mfm.getTiendaMusica().obtenerCancionesGenerales();
    }

    public void crear(){
        if (esGestionCanciones) {
            Cancion cancionVacia=null;
            aplicacion.mostrarVentanaCrearEditarCancion(cancionVacia);
        }else if (esGestionArtistas){
            Artista artistaVacio=null;
            aplicacion.mostrarVentanaCrearEditarArtista(artistaVacio);
        }

    }

    public void eliminar(){
        if (esGestionCanciones) {
            if (cancionElegida!=null){
                if (confirmarEliminacion("la canción")){
                    mfm.getTiendaMusica().eliminarCancion(cancionElegida);
                    iniciarScrollCanciones();
                    cancionElegida=null;
                }
            }else{
                Alertas.mostrarMensaje("Error", "Entrada no valida", "Por favor seleccione una canción primero", Alert.AlertType.ERROR);
            }
        } else if (esGestionArtistas){
            artistaElegido=tablaArtistas.getSelectionModel().getSelectedItem();

            if (artistaElegido!=null){
                if (confirmarEliminacion("el artista")){
                    //mfm.getTiendaMusica().eliminarArtista(artistaElegido);
                    iniciarTablaArtistas();
                    artistaElegido=null;
                }
            }else{
                Alertas.mostrarMensaje("Error", "Entrada no valida", "Por favor seleccione un artista primero", Alert.AlertType.ERROR);
            }

        }


    }

    public boolean confirmarEliminacion(String objeto) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar Eliminación");
        alerta.setHeaderText("¿Está seguro de eliminar a "+objeto+" seleccionado/a?");
        alerta.setContentText("Esta acción no se puede deshacer.");

        // Configurar los botones del cuadro de diálogo
        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alerta.getButtonTypes().setAll(botonSi, botonCancelar);

        // Mostrar el cuadro de diálogo y esperar la respuesta del usuario
        Optional<ButtonType> resultado = alerta.showAndWait();

        // Devolver true si el usuario ha confirmado, false si ha cancelado
        return resultado.isPresent() && resultado.get() == botonSi;
    }

    public boolean confirmarDetalle() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar Detalle");
        alerta.setHeaderText("Esta acción solo le mostrará las canciones del artista elegido");
        alerta.setContentText("Todos los detalles del artista estan en la tabla");

        // Configurar los botones del cuadro de diálogo
        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alerta.getButtonTypes().setAll(botonSi, botonCancelar);

        // Mostrar el cuadro de diálogo y esperar la respuesta del usuario
        Optional<ButtonType> resultado = alerta.showAndWait();

        // Devolver true si el usuario ha confirmado, false si ha cancelado
        return resultado.isPresent() && resultado.get() == botonSi;
    }

    public void editar(){
        if (esGestionCanciones) {
            if (cancionElegida!=null){
                aplicacion.mostrarVentanaCrearEditarCancion(cancionElegida);
            }else{
                Alertas.mostrarMensaje(
                        "Error", "Entrada no valida", "Por favor seleccione una canción primero", Alert.AlertType.ERROR);
            }
        }else if (esGestionArtistas){
            artistaElegido=tablaArtistas.getSelectionModel().getSelectedItem();
            if (artistaElegido!=null){
                aplicacion.mostrarVentanaCrearEditarArtista(artistaElegido);
            }else{
                Alertas.mostrarMensaje("Error", "Entrada no valida", "Por favor seleccione un artista primero", Alert.AlertType.ERROR);
            }
        }

    }

    public void detalles(){

        if (esGestionCanciones) {
            if (cancionElegida!=null){
                aplicacion.abrirDetalleCancion(cancionElegida);
            }else{
                Alertas.mostrarMensaje("Error", "Entrada no valida", "Por favor seleccione una canción primero", Alert.AlertType.ERROR);
            }
        }else if (esGestionArtistas){
            artistaElegido=tablaArtistas.getSelectionModel().getSelectedItem();
            if (artistaElegido!=null) {
                if (confirmarDetalle()) {
                    aplicacion.verCancionesDeArtista(artistaElegido); //colocar boton volver en esa ventana, if cliente null
                }
            }else{
                Alertas.mostrarMensaje("Error", "Entrada no valida", "Por favor seleccione un artista primero", Alert.AlertType.ERROR);

            }
        }

    }



}
