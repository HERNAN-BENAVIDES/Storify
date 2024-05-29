package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.estructurasDeDatos.arbolBinario.BinaryTree;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaDoble;
import co.edu.uniquindio.storify.exceptions.ArtistaNoEncontradoException;
import co.edu.uniquindio.storify.exceptions.ArtistasYaEnTiendaException;
import co.edu.uniquindio.storify.exceptions.AtributoVacioException;
import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.model.Cancion;
import co.edu.uniquindio.storify.model.Usuario;
import co.edu.uniquindio.storify.util.Alertas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
@Data

public class VentanaCrearEditarArtistaController implements Initializable {

    public Button btnCargarArtista;
    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;
    private Artista artista;
    private ListaEnlazadaDoble<Cancion> listaCancionesArtista= new ListaEnlazadaDoble<>();
    private ListaEnlazadaDoble<Cancion> listaCancionesModificada;
    private ListaEnlazadaDoble<Cancion> listaCancionesEliminadas = new ListaEnlazadaDoble<>();

    @FXML
    private TextField txtNombreArtista;

    @FXML
    private ComboBox<String> comboNacionalidad;

    @FXML
    private TextField txtCodigo;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Cancion, String> columnaGenero;

    @FXML
    private TableView<Cancion> tablaCancionesArtista;

    @FXML
    private TableColumn<Cancion, String> columnaCodigo;

    @FXML
    private TableColumn<Cancion, String> columnaNombre;

    @FXML
    private TableColumn<Cancion, String> columnaAlbum;

    @FXML
    private ComboBox<String> comboTipo;

    @FXML
    private TableColumn<Cancion, String> columnaDuracion;
    private String rutaArchivo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void iniciarDatosCrearEditar(){
        iniciarCombos();
        if (artista!=null){ // si el guia existe se tratara de una edicion artista
            iniciarCancionesDeArtista();
            iniciarTabla();
            txtNombreArtista.setText(artista.getNombre());
            txtCodigo.setText(artista.getCodigo());
            comboNacionalidad.setValue(artista.getNacionalidad());
            comboTipo.setValue(artista.obtenerTipoArtistaString());


        }else{//si no se tratara de una creacion artista
            btnCrear.setVisible(true);
            btnGuardar.setVisible(false);

        }
    }

    public void iniciarTabla(){
        tablaCancionesArtista.getItems().clear();
        ObservableList<Cancion> listaCancionesProperty= FXCollections.observableArrayList();
        // Asignar las propiedades de las columnas
        columnaCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnaAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlbum()));
        columnaDuracion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuracion()));
        columnaGenero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().obtenerGeneroComoString()));

        listaCancionesProperty.clear();
        listaCancionesProperty.addAll(mfm.getTiendaMusica().convertirAArrayList(listaCancionesArtista));
        tablaCancionesArtista.setItems(listaCancionesProperty);
    }

    public void iniciarTabla(ListaEnlazadaDoble<Cancion> listaCanciones){
        tablaCancionesArtista.getItems().clear();
        ObservableList<Cancion> listaCancionesProperty= FXCollections.observableArrayList();
        // Asignar las propiedades de las columnas
        columnaCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnaAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlbum()));
        columnaDuracion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuracion()));
        columnaGenero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().obtenerGeneroComoString()));

        listaCancionesProperty.clear();
        listaCancionesProperty.addAll(mfm.getTiendaMusica().convertirAArrayList(listaCanciones));
        tablaCancionesArtista.setItems(listaCancionesProperty);
    }

    public void iniciarCancionesDeArtista(){
        this.listaCancionesArtista=artista.getCanciones();
        this.listaCancionesModificada = new ListaEnlazadaDoble<>(listaCancionesArtista);
    }

    public void iniciarCombos(){
        ObservableList<String> tipoNaciones=FXCollections.observableArrayList();
        tipoNaciones.addAll(mfm.getTiendaMusica().obtenerNombresPaises());
        comboNacionalidad.setItems(tipoNaciones);



        ObservableList<String> tiposArtistas= FXCollections.observableArrayList();
        tiposArtistas.add("SOLISTA");
        tiposArtistas.add("BANDA");
        comboTipo.setItems(tiposArtistas);
    }

    public void eliminarCancion(){
        Cancion cancionEliminar = tablaCancionesArtista.getSelectionModel().getSelectedItem();
        if (cancionEliminar!=null){
            if (confirmarEliminacion(cancionEliminar)){
                listaCancionesModificada.removeData(cancionEliminar);
                listaCancionesEliminadas.add(cancionEliminar);
                iniciarTabla(listaCancionesModificada);
            }
        }else{
            Alertas.mostrarMensaje("Error", "Entrada no valida", "Debe elegir una canción", Alert.AlertType.ERROR);

        }
    }

    public boolean confirmarEliminacion(Cancion cancionElim) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar Eliminación");
        alerta.setHeaderText("¿Está seguro de eliminar la canción: "+cancionElim.getNombre()+" ?");
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

    public void crearArtista(){

        try {
            Artista artistaNuevo = mfm.getTiendaMusica().crearArtista(
                    txtNombreArtista.getText(),
                    txtCodigo.getText(),
                    comboNacionalidad.getValue(),
                    comboTipo.getValue()
            );
            mfm.guardarDatosBinario();
            mfm.getTiendaMusica().agregarArtista(artistaNuevo);
            Alertas.mostrarMensaje("Registro Confirmado", "Operación completada", "Se ha registrado correctamente el artista : "+artistaNuevo.getNombre(), Alert.AlertType.INFORMATION);
            aplicacion.motrarVentanaGestionArtista();

        } catch (AtributoVacioException | ArtistasYaEnTiendaException | InterruptedException e) {
            Alertas.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void guardarCambios(){
        try {
            Artista artistaEditado = mfm.getTiendaMusica().editarArtista(
                    this.artista,
                    txtNombreArtista.getText(),
                    txtCodigo.getText(),
                    comboNacionalidad.getValue(),
                    comboTipo.getValue(),
                    listaCancionesModificada
            );

            for (Cancion cancion : listaCancionesEliminadas){
                mfm.getTiendaMusica().eliminarCancionUsuario(cancion);
            }

            mfm.guardarDatosBinario();
            Alertas.mostrarMensaje("Edición Confirmada", "Operación completada", "Se ha editado correctamente el artista: "+artistaEditado.getNombre(), Alert.AlertType.INFORMATION);
            aplicacion.motrarVentanaGestionArtista();

        } catch (AtributoVacioException | ArtistaNoEncontradoException e) {
            Alertas.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void volver(){
        aplicacion.motrarVentanaGestionArtista();
    }

    public void cargarArtista(ActionEvent actionEvent) throws ArtistasYaEnTiendaException, IOException, InterruptedException {
        try {
            openFileChooser();
            mfm.getTiendaMusica().cargarArtistasDesdeArchivo(rutaArchivo);
            mfm.guardarDatosBinario();
            Alertas.mostrarMensaje("Carga de Artistas", "Artistas cargados con exito","Se han cargado correctamente los artistas desde el archivo "+rutaArchivo, Alert.AlertType.INFORMATION);
            aplicacion.motrarVentanaGestionArtista();
        } catch (FileNotFoundException e) {
            Alertas.mostrarMensaje("Error", "Archivo no encontrado", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void openFileChooser() {
        // Crear el FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo de texto");

        // Filtro para solo mostrar archivos .txt
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de texto (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostrar el cuadro de diálogo de abrir archivo
        File selectedFile = fileChooser.showOpenDialog(ventana);

        // Si el usuario selecciona un archivo, almacenar la ruta del archivo
        if (selectedFile != null) {
            rutaArchivo = selectedFile.getAbsolutePath();
        }
    }
}
