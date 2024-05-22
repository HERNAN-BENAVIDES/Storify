package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.controllers.controladorListeners.MyListenerArtista;
import co.edu.uniquindio.storify.controllers.controladorListeners.MyListenerCancion;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimpleCircular;
import co.edu.uniquindio.storify.estructurasDeDatos.nodo.Node;
import co.edu.uniquindio.storify.exceptions.ArtistaNoEncontradoException;
import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.model.Cancion;
import co.edu.uniquindio.storify.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
@Data

public class VentanaGestionarController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;
    private ListaEnlazadaSimpleCircular<Cancion> listaCanciones = new ListaEnlazadaSimpleCircular<>();
    private Boolean esGestionCanciones;

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
    private ScrollPane scroll;

    @FXML
    private Button btnCrear;

    @FXML
    private Text txtTituloGestion;

    @FXML
    private Button btnEditar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void iniciarGestionCanciones() throws ArtistaNoEncontradoException, IOException {
        esGestionCanciones=true;
        txtAdvertencia.setText("Antes de elegir una opción de gestión, tenga en cuenta que debe seleccionar una canción");
        txtTituloGestion.setText("Gestión de Canciones");
        iniciarScrollCanciones();

    }

    public void iniciarScrollCanciones() throws IOException, ArtistaNoEncontradoException {
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

            scroll.setVvalue(0.05); //para que no se vea el espacio en blanco de 2 cm entre el filtro y el panel
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void establecerListaCancionesGenerales(){
        listaCanciones= mfm.getTiendaMusica().obtenerCancionesGenerales();
    }



}
