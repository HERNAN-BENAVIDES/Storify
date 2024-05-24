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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
@Data

public class VentanaCrearEditarArtistaController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;
    private Artista artista;
    private ListaEnlazadaDoble<Cancion> listaCancionesArtista= new ListaEnlazadaDoble<>();

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

    public void iniciarCancionesDeArtista(){
        this.listaCancionesArtista=artista.getCanciones();
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
                listaCancionesArtista.removeData(cancionEliminar);
                iniciarTabla();

                //la lista se actualiza pero elimina directamente de toda ubicacion la cancion
                //esto deberia de pasar unicamente cuando se de click al boton de guardar
                //pero funciona correctamente si se ignora que se esta borrando la cancion indefinidamente
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
                    listaCancionesArtista
            );
            Alertas.mostrarMensaje("Edición Confirmada", "Operación completada", "Se ha editado correctamente el artista: "+artistaEditado.getNombre(), Alert.AlertType.INFORMATION);
            aplicacion.motrarVentanaGestionArtista();

        } catch (AtributoVacioException | ArtistaNoEncontradoException e) {
            Alertas.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void volver(){
        aplicacion.motrarVentanaGestionArtista();
    }
}
