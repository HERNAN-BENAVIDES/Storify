package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.exceptions.ArtistasYaEnTiendaException;
import co.edu.uniquindio.storify.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Data;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

@Data

public class VentanaArchivosController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;
    private boolean archivoCargado=false;
    private File archivoSeleccionado;

    @FXML
    private TextArea textArea;

    @FXML
    private Button btnCargar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cargarArchivo() {
        if (!archivoCargado) {
            FileChooser fileChooser = new FileChooser();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("File Viewer");

            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                archivoSeleccionado = file;
                displayFileContent(file, textArea);
                archivoCargado = true;
                btnCargar.setText("Guardar cambios");
            }
        } else {
            try {
                mfm.getTiendaMusica().cargarArtistasDesdeArchivo(archivoSeleccionado.getAbsolutePath());
                aplicacion.motrarVentanaGestionArtista();
            } catch (IOException | InterruptedException | ArtistasYaEnTiendaException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayFileContent(File file, TextArea textArea) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        textArea.setText(content.toString());
    }


}
