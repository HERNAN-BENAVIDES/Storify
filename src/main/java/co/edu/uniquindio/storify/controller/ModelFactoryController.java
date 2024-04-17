package co.edu.uniquindio.storify.controller;

import co.edu.uniquindio.storify.model.Administrador;
import co.edu.uniquindio.storify.model.TiendaMusica;
import co.edu.uniquindio.storify.util.StorifyUtil;

import java.io.IOException;

@SuppressWarnings("all")
public class ModelFactoryController {

    Administrador administrador;
    TiendaMusica tiendaMusica;

    public ModelFactoryController()  {
        cargarDatosPrueba();
        this.administrador = new Administrador(tiendaMusica);
        cargarArtistasDesdeArchivo();
    }

    private void cargarArtistasDesdeArchivo() {
        try {
            administrador.cargarArtistasDesdeArchivo("src/main/resources/archivos/artistas.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarDatosPrueba() {
        tiendaMusica = StorifyUtil.inicializarDatosPrueba();
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
