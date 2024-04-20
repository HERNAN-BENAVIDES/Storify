package co.edu.uniquindio.storify.controller;

import co.edu.uniquindio.storify.exceptions.ArtistasYaEnTiendaException;
import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.model.TiendaMusica;
import co.edu.uniquindio.storify.util.StorifyUtil;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SuppressWarnings("all")
public class ModelFactoryController {
    private TiendaMusica tiendaMusica;

    public ModelFactoryController()  {
        cargarDatosPrueba();
        cargarArtistasDesdeArchivo();
    }

    private void cargarArtistasDesdeArchivo() {
        try {
            tiendaMusica.cargarArtistasDesdeArchivo("src/main/resources/archivos/artistas.txt");
        } catch (ArtistasYaEnTiendaException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarDatosPrueba() {
        tiendaMusica = StorifyUtil.inicializarDatosPrueba();
    }

    private String obtenerGeneroConMasCanciones() {
        return tiendaMusica.obtenerGeneroConMasCanciones();
    }

    private Artista obtenerArtistaMasPopular() throws IOException, GeneralSecurityException {
        return tiendaMusica.obtenerArtistaMaspopular();
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
