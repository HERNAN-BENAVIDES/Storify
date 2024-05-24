package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.estructurasDeDatos.arbolBinario.BinaryTree;
import co.edu.uniquindio.storify.exceptions.ArtistaNoEncontradoException;
import co.edu.uniquindio.storify.exceptions.AtributoVacioException;
import co.edu.uniquindio.storify.exceptions.CancionYaRegistradaException;
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
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Data;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Data

public class VentanaCrearEditarCancionController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    BinaryTree<Artista> artistas= mfm.getTiendaMusica().getArtistas();
    private Usuario usuario;
    private Cancion cancion;
    private String caratulaElegida;
    private Artista artistaElegido;

    @FXML
    private TableView<Artista> tablaAutores;

    @FXML
    private TableColumn<Artista, String> columnaTipo;

    @FXML
    private TableColumn<Artista, String> columnaCodigo;

    @FXML
    private TableColumn<Artista, String> columnaNombre;

    @FXML
    private TableColumn<Artista, String> columnaNacionalidad;

    @FXML
    private TextField txtDuracion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtAlbum;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtYoutube;

    @FXML
    private Button subirCaratula;

    @FXML
    private ComboBox<String> comboGenero;

    @FXML
    private ImageView fotoCaratula;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCrear;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void iniciarDatosCrearEditar(){
        actualizarTabla();
        if (cancion!=null){ //si la cancion existe se tratará de una edicion
            txtNombre.setText(cancion.getNombre());
            txtAlbum.setText(cancion.getAlbum());
            txtAnio.setText(String.valueOf(cancion.getAnioLanzamiento()));
            txtDuracion.setText(cancion.getDuracion());
            txtYoutube.setText(cancion.getUrlYoutube());
            actualizarCombo();
            String generoCancion=cancion.obtenerGeneroComoString();
            comboGenero.setValue(generoCancion);
            caratulaElegida=cancion.getCaratula();



            try {
                Artista artistaCancion= mfm.getTiendaMusica().buscarArtistaCancion(cancion);
                tablaAutores.getSelectionModel().select(artistaCancion);


                Image image = new Image(getClass().getResourceAsStream(cancion.getCaratula()));
                fotoCaratula.setImage(image);
            } catch (Exception e) {
                if (!cancion.getCaratula().equals("")) {
                    Image image = new Image(cancion.getCaratula());
                    fotoCaratula.setImage(image);
                }
            }
        }else{ //si no se tratara de una creacion cancion
            btnCrear.setVisible(true);
            btnGuardar.setVisible(false);
            actualizarCombo();

            Image image = new Image(getClass().getResourceAsStream("/imagenes/Banda Icon.png"));
            fotoCaratula.setImage(image);


        }
    }

    public void actualizarCombo(){
        ObservableList<String> tipoGeneroObservable= FXCollections.observableArrayList();
        tipoGeneroObservable.add("Rock");
        tipoGeneroObservable.add("Pop");
        tipoGeneroObservable.add("Salsa");
        tipoGeneroObservable.add("Bachata");
        tipoGeneroObservable.add("Punk");
        tipoGeneroObservable.add("Reggaeton");
        tipoGeneroObservable.add("Electronica");
        tipoGeneroObservable.add("R&B");
        tipoGeneroObservable.add("Otro");
        comboGenero.setItems(tipoGeneroObservable);
    }

    public void guardar(){
        try {
            Cancion cancionEditada = mfm.getTiendaMusica().editarCancion(
                    this.cancion,
                    txtNombre.getText(),
                    txtAlbum.getText(),
                    caratulaElegida,
                    txtAnio.getText(),
                    txtDuracion.getText(),
                    comboGenero.getValue(),
                    txtYoutube.getText()
            );
            Alertas.mostrarMensaje("Edición Confirmada", "Operación completada", "Se ha editado correctamente la canción: "+cancionEditada.getNombre(), Alert.AlertType.INFORMATION);
            aplicacion.motrarVentanaGestionCanciones();

        } catch (AtributoVacioException| ArtistaNoEncontradoException e) {
            Alertas.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void crear(){

        try {
            Cancion cancionNueva= mfm.getTiendaMusica().crearCancion(
                    txtNombre.getText(),
                    txtAlbum.getText(),
                    caratulaElegida,
                    txtAnio.getText(),
                    txtDuracion.getText(),
                    comboGenero.getValue(),
                    txtYoutube.getText());
            Artista artistaElegido= tablaAutores.getSelectionModel().getSelectedItem();
            mfm.getTiendaMusica().agregarCancion(cancionNueva, artistaElegido);

            Alertas.mostrarMensaje("Registro Confirmado", "Operación completada", "Se ha registrado correctamente la canción: "+cancionNueva.getNombre(), Alert.AlertType.INFORMATION);
            aplicacion.motrarVentanaGestionCanciones();

        } catch (AtributoVacioException | CancionYaRegistradaException e) {
            Alertas.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void volver(){
        aplicacion.motrarVentanaGestionCanciones();
    }

    public void actualizarTabla(){
        tablaAutores.getItems().clear();
        ObservableList<Artista> listaArtistasProperty= FXCollections.observableArrayList();
        // Asignar las propiedades de las columnas
        columnaTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().obtenerTipoArtistaString()));
        columnaCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnaNacionalidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNacionalidad()));

        listaArtistasProperty.clear();
        listaArtistasProperty.addAll(mfm.getTiendaMusica().convertirArbolALista(artistas));
        tablaAutores.setItems(listaArtistasProperty);
    }

    public void subirFoto(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.jpg", "*.png", "*.jpeg"));

        // Mostrar el cuadro de diálogo para seleccionar un archivo
        File selectedFile = fileChooser.showOpenDialog(ventana);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            fotoCaratula.setImage(image);
            this.caratulaElegida=fotoCaratula.getImage().getUrl();
        } else {
            // Restaurar la imagen a null si no se selecciona ninguna
            Image image =  new Image ("/imagenes/user.png");
            fotoCaratula.setImage(image);
            this.caratulaElegida=fotoCaratula.getImage().getUrl();
        }


    }


}
