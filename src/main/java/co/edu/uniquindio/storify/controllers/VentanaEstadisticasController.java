package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.estructurasDeDatos.arbolBinario.BinaryTree;
import co.edu.uniquindio.storify.model.Artista;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.ResourceBundle;

public class VentanaEstadisticasController implements Initializable {

    private ModelFactoryController mfm = ModelFactoryController.getInstance();
    private Stage ventana = mfm.getVentana();
    private Aplicacion aplicacion = mfm.getAplicacion();
    BinaryTree<Artista> artistas= mfm.getTiendaMusica().getArtistas();

    @FXML
    private NumberAxis yAxis;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private BarChart<String, Double> chart;

    @FXML
    private PieChart pieChart;

    @FXML
    private Text txtTituloEstadistica;

    @FXML
    private ComboBox<String> comboEstadistica;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void iniciarDatos(){
        //inicio de combo
        ObservableList<String> opciones= FXCollections.observableArrayList(
                "GENEROS",
                "ARTISTAS"
        );
        comboEstadistica.setItems(opciones);

    }

    public void analizar(){
        String chosen=comboEstadistica.getSelectionModel().getSelectedItem();
        if (chosen!=null || !chosen.isBlank()){
            if(chosen.equals("GENEROS")){
                aplicacion.mostrarVentanaEstadisticas(true);
            }
            else if (chosen.equals("ARTISTAS")){
                aplicacion.mostrarVentanaEstadisticas(false);
            }
        }
    }


    public void actualizarChartsGeneros() {
        comboEstadistica.setValue("GENEROS");
        xAxis.setLabel("Géneros");
        yAxis.setLabel("Cantidad de Canciones por Género");
        txtTituloEstadistica.setText("Estadísticas de Géneros Musicales");
        // Obtener el conteo de canciones por género desde TiendaMusica
        Map<String, Integer> conteoPorGenero = mfm.getTiendaMusica().contarCancionesPorGenero();

        // Crear una serie de datos para el BarChart
        XYChart.Series<String, Double> barSeries = new XYChart.Series<>();
        barSeries.setName("Canciones por Género");

        // Crear una lista de datos para el PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Añadir los datos a ambas series
        for (Map.Entry<String, Integer> entry : conteoPorGenero.entrySet()) {
            String genero = entry.getKey();
            Double cantidad = entry.getValue().doubleValue(); // Convertir a Double

            // Añadir al BarChart
            barSeries.getData().add(new XYChart.Data<>(genero, cantidad));

            // Añadir al PieChart
            pieChartData.add(new PieChart.Data(genero, cantidad));
        }

        // Limpiar el BarChart y añadir la nueva serie
        chart.getData().clear();
        chart.getData().add(barSeries);

        // Limpiar el PieChart y añadir los nuevos datos
        pieChart.setData(pieChartData);

    }

    public void actualizarChartsArtistas() throws GeneralSecurityException, IOException {
        comboEstadistica.setValue("ARTISTAS");

        xAxis.setLabel("Artistas");
        yAxis.setLabel("Cantidad De Vistas Totales");
        txtTituloEstadistica.setText("Estadísticas de Número de Vistas Artistas");
        // Obtener el conteo de canciones por género desde TiendaMusica
        Map<String, Double> conteoPorArtistas = mfm.getTiendaMusica().contarVistasPorArtista();

        // Crear una serie de datos para el BarChart
        XYChart.Series<String, Double> barSeries = new XYChart.Series<>();
        barSeries.setName("Vistas Por Artista");

        // Crear una lista de datos para el PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Añadir los datos a ambas series
        for (Map.Entry<String, Double> entry : conteoPorArtistas.entrySet()) {
            String artista = entry.getKey();
            Double vistas = entry.getValue().doubleValue(); // Convertir a Double

            // Añadir al BarChart
            barSeries.getData().add(new XYChart.Data<>(artista, vistas));

            // Añadir al PieChart
            pieChartData.add(new PieChart.Data(artista, vistas));
        }

        // Limpiar el BarChart y añadir la nueva serie
        chart.getData().clear();
        chart.getData().add(barSeries);

        // Limpiar el PieChart y añadir los nuevos datos
        pieChart.setData(pieChartData);

    }

}
