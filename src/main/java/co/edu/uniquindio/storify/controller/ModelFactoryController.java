package co.edu.uniquindio.storify.controller;

import co.edu.uniquindio.storify.model.TiendaMusica;
import co.edu.uniquindio.storify.util.StorifyUtil;

@SuppressWarnings("all")
public class ModelFactoryController {

    TiendaMusica tiendaMusica;

    public ModelFactoryController() {
        cargarDatosPrueba();
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
