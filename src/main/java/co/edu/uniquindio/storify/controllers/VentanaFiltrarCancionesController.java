package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimpleCircular;
import co.edu.uniquindio.storify.estructurasDeDatos.nodo.Node;
import co.edu.uniquindio.storify.exceptions.ArtistaNoEncontradoException;
import co.edu.uniquindio.storify.model.Cancion;
import co.edu.uniquindio.storify.model.Cliente;
import co.edu.uniquindio.storify.model.Persona;
import co.edu.uniquindio.storify.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;
    private ListaEnlazadaSimpleCircular<Cancion> listaCanciones = new ListaEnlazadaSimpleCircular<>();

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
                } while (currentNode != listaCanciones.getHeadNode()); // Seguir iterando hasta que se vuelva al nodo de inicio
            }

            scroll.setVvalue(0.05); //para q no se vea el espacio en blanco de 2 cm entre el filtro y el panel
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void establecerListaCancionesFavoritas() {
        // Verificar si el usuario es un cliente
        Persona persona= usuario.getPersona();
        if (persona instanceof Cliente) {
            Cliente cliente = (Cliente) persona;
            listaCanciones = cliente.getCancionesFavoritas();
        }
    }
}
