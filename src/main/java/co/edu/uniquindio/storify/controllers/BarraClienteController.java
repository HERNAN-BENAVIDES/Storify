package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.model.Usuario;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
@Data

public class BarraClienteController implements Initializable {

    @FXML
    private Label lblFecha;

    @FXML
    private ImageView userPic;

    @FXML
    private Text txtBienvenida;

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cargarInfo(){
        runTime();
        cargarDatosUsuario();
    }

    public void salirCuenta(){
        aplicacion.mostrarVentanaRegistroIngreso();
    }

    public void runTime(){
        new Thread(){
            public void run(){
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
                while(true){
                    try{
                        Thread.sleep(1000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        Date date = new Date(System.currentTimeMillis());
                        lblFecha.setText(format.format(date));
                    });
                }
            }
        }.start();
    }

    public void cargarDatosUsuario(){
        //PARA UNA FUTURA VARIABLE FOTO EN LA CLASE USUARIO (?
        /**
         * try {
         *             Image image = new Image(getClass().getResourceAsStream(usuario.getFoto()));
         *             userPic.setImage(image);
         *         } catch (Exception e) {
         *             if (!usuario.getFoto().equals("")) {
         *                 Image image = new Image(usuario.getFoto());
         *                 userPic.setImage(image);
         *             }
         *         }
         */
        Circle clip = new Circle(userPic.getFitWidth() / 2, userPic.getFitHeight() / 2, userPic.getFitWidth() / 2);
        userPic.setClip(clip);
        String nombre= usuario.getUsername();
        int espacio=nombre.length()*15;
        txtBienvenida.setText("¡Bienvenid@, "+nombre+"!");
        txtBienvenida.setX(txtBienvenida.getX()-espacio);
    }

}
