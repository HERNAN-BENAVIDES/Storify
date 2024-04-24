package co.edu.uniquindio.storify.util;

import co.edu.uniquindio.storify.model.TiendaMusica;

public class Persistencia {

    private static final String RUTA_ARCHIVO_TIENDA_BINARIO = "src/main/resources/archivos/tienda.dat";


    public static void guardarRecursoBancoBinario(TiendaMusica tiendaMusica) {
        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_TIENDA_BINARIO,tiendaMusica);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static TiendaMusica cargarRecursoBancoBinario() {
        TiendaMusica tiendaMusica = null;

        try {
            tiendaMusica = (TiendaMusica) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_TIENDA_BINARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiendaMusica;
    }
}
