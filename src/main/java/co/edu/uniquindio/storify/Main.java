package co.edu.uniquindio.storify;

import co.edu.uniquindio.storify.estructurasDeDatos.arbolBinario.BinaryTree;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaDoble;
import co.edu.uniquindio.storify.exceptions.EmptyNodeException;
import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.model.Cancion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;

public class Main   {
    public static void main(String[] args) {
//        launch(args);

//        Artista artista = new Artista("a4", "Ana", "a4", null);
//
//        BinaryTree<Artista> arbol = new BinaryTree<>();
//        arbol.insert(new Artista("a1", "Juan", "a1", null));
//        arbol.insert(new Artista("a2", "Pedro", "a2", null));
//        arbol.insert(new Artista("a3", "Maria", "a3", null));
//        arbol.insert(artista);
//
//        Cancion cancion = new Cancion("c1", "c1", "c1", 4, 3.24, null,"www");
//        artista.getCanciones().add(cancion);
//
//        Artista artista2 = new Artista("a5", "Ana", "a5", null);
//
//        ListaEnlazadaDoble<Cancion> cancionesArtista = arbol.find(artista2).getCanciones();

        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(50);
        tree.insert(70);
        tree.insert(30);
        tree.insert(40);
        tree.insert(20);
        tree.insert(80);
        tree.insert(60);
        tree.insert(1000);

        System.out.println("Árbol original:");
        tree.inorder(); // Imprimir el árbol original

        // Eliminar algunos nodos
        try {
            tree.delete(20);
            tree.delete(80);
            tree.delete(50);
        } catch (EmptyNodeException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nÁrbol después de eliminar nodos:");
        tree.inorder(); // Imprimir el árbol después de eliminar nodos

    }

//    @Override
//    public void start(Stage primaryStage) {
//        try {
//            // Especifica la ruta del archivo FXML
//            String fxmlPath = "src/main/resources/ventanas/VentanaInicio.fxml";
//            URL location = getClass().getResource(fxmlPath);
//
//            // Carga el archivo FXML
//            FXMLLoader loader = new FXMLLoader(location);
//            AnchorPane root = loader.load();
//
//            // Configura la escena
//            Scene scene = new Scene(root, 400, 300);
//            primaryStage.setScene(scene);
//
//            // Establece el título de la ventana
//            primaryStage.setTitle("Mi Ventana JavaFX");
//
//            // Muestra la ventana
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
