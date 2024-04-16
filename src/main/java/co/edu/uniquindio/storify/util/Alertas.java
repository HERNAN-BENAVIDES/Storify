package co.edu.uniquindio.storify.util;

import javafx.scene.control.Alert;

@SuppressWarnings("all")
public class Alertas {

    /**
     * Muestra una alerta de error con el mensaje proporcionado.
     *
     * @param mensaje El mensaje a mostrar en la alerta de error
     */
    public static void mostrarAlertaError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


    /**
     * Muestra una alerta de informacion con el mensaje proporcionado.
     *
     * @param mensaje El mensaje a mostrar en la alerta de error
     */
    public static void mostrarAlertaInformacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
