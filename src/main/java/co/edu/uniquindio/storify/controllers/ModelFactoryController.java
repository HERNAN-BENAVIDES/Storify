package co.edu.uniquindio.storify.controllers;

import co.edu.uniquindio.storify.app.Aplicacion;
import co.edu.uniquindio.storify.exceptions.ArtistasYaEnTiendaException;
import co.edu.uniquindio.storify.model.*;
import co.edu.uniquindio.storify.util.Persistencia;
import co.edu.uniquindio.storify.util.StorifyUtil;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SuppressWarnings("all")
public class ModelFactoryController {
    private TiendaMusica tiendaMusica=null;
    private Aplicacion aplicacion=null;
    private Stage ventana;

    public ModelFactoryController()  {
        cargarDatosBinario();

        if(tiendaMusica == null){
            cargarDatosPrueba();
        }

        //guardarDatosBinario();
    }



    private void cargarDatosBinario() {
        this.tiendaMusica = Persistencia.cargarRecursoBancoBinario();
    }

    private void guardarDatosBinario() {
        Persistencia.guardarRecursoBancoBinario(tiendaMusica);
    }

    private void cargarArtistasDesdeArchivo(String ruta) {
        try {
            tiendaMusica.cargarArtistasDesdeArchivo(ruta);
        } catch (ArtistasYaEnTiendaException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarDatosPrueba() {
        tiendaMusica = StorifyUtil.inicializarDatosPrueba();
    }

//    private String obtenerGeneroConMasCanciones() {
//        return tiendaMusica.obtenerGeneroConMasCanciones();
//    }
//
//    private Artista obtenerArtistaMasPopular() throws IOException, GeneralSecurityException {
//        return tiendaMusica.obtenerArtistaMaspopular();
//    }

    /**
     * Metodo que obtiene la TiendaMusica
     * @return TiendaMusica
     */
    public TiendaMusica getTiendaMusica() {
        return tiendaMusica;
    }

    /**
     * Metodo que cambia la tiendaMusica
     * @param tiendaMusica Nueva tiendaMusica
     */
    public void setConcesionario(TiendaMusica tiendaMusica) {
        this.tiendaMusica = tiendaMusica;
    }

    /**
     * Obtiene la ventana
     * @return ventana
     */
    public Stage getVentana() {
        return ventana;
    }

    /**
     * Establece la ventana
     * @param ventana La ventana
     */
    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }

    /**
     * Obtiene la direccion de la aplicacion principal
     * @return Aplicacion principal
     */
    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    /**
     * Establece la direccion de la aplicacion principal
     * @param aplicacion Nueva aplicacion principal
     */
    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }



     /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------GET INSTANCE---------------------------------------------------------
    */
    /**
     * Obtiene una instancia única de la clase ModelFactoryController.
     * @return Una instancia única de ModelFactoryController.
     */
    public static ModelFactoryController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Clase interna que contiene la instancia única de ModelFactoryController.
     */
    private static class SingletonHolder {
        private static final ModelFactoryController INSTANCE = new ModelFactoryController();
    }
}