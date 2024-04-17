package co.edu.uniquindio.storify;

import co.edu.uniquindio.storify.controller.ModelFactoryController;
import co.edu.uniquindio.storify.model.Administrador;
import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.util.ArchivoUtil;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        try {
            HashMap<String, Artista> artistas = ArchivoUtil.cargarArtistasDesdeArchivo("src/main/resources/archivos/artistas.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
