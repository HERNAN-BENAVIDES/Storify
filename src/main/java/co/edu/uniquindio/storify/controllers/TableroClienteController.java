package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

@Data

public class TableroClienteController implements Initializable {

    public Button btnSolistas;
    public Button btnBandas;
    public Button btnArtistas;
    public Button btnMisFavoritos;
    public Button btnGeneros;
    public Button btnCanciones;
    public Button btnAjustesPerfil;
    public ImageView btnUnmute;
    public ImageView btnPausa;
    public ImageView btnBack;
    public ImageView btnNext;
    public Label txtNombreCancion;
    public Label txtNombreArtista;
    public ImageView btnMeGusta;
    public ImageView btnNoMeGusta;
    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void mostrarFavoritos(){
        aplicacion.mostrarVentanaMisCanciones(this.usuario);
    }

    public void mostrarCanciones(){

    }

    public void mostrarSolistas(ActionEvent actionEvent) {
    }

    public void mostrarBandas(ActionEvent actionEvent) {
    }

    public void mostrarArtistas(ActionEvent actionEvent) {
    }

    public void mostrarMisFavoritos(ActionEvent actionEvent) {
    }

    public void mostrarGeneros(ActionEvent actionEvent) {
    }

    public void mostrarPerfil(ActionEvent actionEvent) {
    }

    public void desmutear(MouseEvent mouseEvent) {
    }

    public void pausarCancion(MouseEvent mouseEvent) {
    }

    public void regresarCancion(MouseEvent mouseEvent) {
    }

    public void saltarCancion(MouseEvent mouseEvent) {
    }

    public void aniadirFavoritos(MouseEvent mouseEvent) {
    }

    public void eliminarFavoritos(MouseEvent mouseEvent) {
    }

    public void mutear(MouseEvent mouseEvent) {
    }
}