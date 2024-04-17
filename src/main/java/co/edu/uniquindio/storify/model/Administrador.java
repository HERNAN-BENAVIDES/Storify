package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaDoble;
import co.edu.uniquindio.storify.util.ArchivoUtil;
import lombok.Data;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("all")
@Data
public class Administrador extends Persona implements Serializable {
    private static Administrador administrador;
    private TiendaMusica tiendaMusica;

    public Administrador(TiendaMusica tiendaMusica) {
        super("Jose Juan", "Mesa");
        this.tiendaMusica = tiendaMusica;
    }

    public boolean crearArtista(Artista artista) {
        String codigoArtista = artista.getCodigo();
        Artista artistaExistente = tiendaMusica.getArtistas().putIfAbsent(codigoArtista, artista);
        if (artistaExistente != null) {
            throw new IllegalArgumentException("El artista ya existe en la tienda.");
        }
        return true;
    }

    public boolean agregarCancion(Cancion cancion, Artista artista) {
        ListaEnlazadaDoble<Cancion> canciones = artista.getCanciones();
        if(canciones.contains(cancion)) {
            throw new IllegalArgumentException("La cancion ya esta en las canciones del  artista");
        }
        canciones.add(cancion);
        return true;
    }

    public void cargarArtistasDesdeArchivo(String ruta) throws IOException {
        HashMap<String, Artista> artistas = ArchivoUtil.cargarArtistasDesdeArchivo(ruta);
        tiendaMusica.getArtistas().putAll(artistas);
    }

}
