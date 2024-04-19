package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaDoble;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimple;
import co.edu.uniquindio.storify.exceptions.ArtistasYaEnTiendaException;
import co.edu.uniquindio.storify.util.ArchivoUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@ToString
@SuppressWarnings("All")
public class TiendaMusica implements Serializable {

    private final String nombre = "Storify";
    private final String version = "1.0.0";
    private HashMap<String, Usuario> usuarios;
    private HashMap<String, Artista> artistas;
    private  Administrador administrador;

    public TiendaMusica() {
        this.usuarios = new HashMap<>();
        this.artistas = new HashMap<>();
        this.administrador = crearAdministrador();
    }

    private Administrador crearAdministrador() {
        Administrador administrador = new Administrador();
        return administrador;
    }

    public boolean crearArtista(Artista artista) {
        String codigoArtista = artista.getCodigo();
        Artista artistaExistente = getArtistas().putIfAbsent(codigoArtista, artista);
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

    public void cargarArtistasDesdeArchivo(String ruta) throws IOException, ArtistasYaEnTiendaException {
        HashMap<String, Artista> artistas = ArchivoUtil.cargarArtistasDesdeArchivo(ruta);
        HashMap<String, Artista> artistasExistentes = getArtistas();
        ListaEnlazadaSimple<Artista> artistasYaEnTienda = new ListaEnlazadaSimple<>();

        for (Map.Entry<String, Artista> entry : artistas.entrySet()) {
            String nombreArtista = entry.getKey();
            Artista nuevoArtista = entry.getValue();

            if (artistasExistentes.containsKey(nombreArtista)) {
                // El artista ya está en la tienda, agregalo a la lista de artistas existentes
                artistasYaEnTienda.add(nuevoArtista);
            } else {
                // El artista es nuevo, agrégalo a la tienda
                artistasExistentes.put(nombreArtista, nuevoArtista);
            }
        }

        if (!artistasYaEnTienda.isEmpty()) {
            throw new ArtistasYaEnTiendaException("Al menos un artista ya está en la tienda");
        }

        // Si no se lanza la excepción, significa que todos los artistas se han agregado correctamente
    }

    public String obtenerGeneroConMasCanciones() {
        HashMap<TipoGenero, Integer> contadorGeneros = new HashMap<>();

        // Iterar sobre todos los artistas en la tienda
        for (Artista artista : artistas.values()) {
            // Iterar sobre todas las canciones del artista
            for (Cancion cancion : artista.getCanciones()) {
                // Obtener el género de la canción
                TipoGenero genero = cancion.getGenero();

                // Incrementar el contador para el género actual
                contadorGeneros.put(genero, contadorGeneros.getOrDefault(genero, 0) + 1);
            }
        }

        // Encontrar el género con más canciones
        String generoConMasCanciones = null;
        int maxCanciones = 0;
        for (Map.Entry<TipoGenero, Integer> entry : contadorGeneros.entrySet()) {
            if (entry.getValue() > maxCanciones) {
                generoConMasCanciones = entry.getKey().toString();
                maxCanciones = entry.getValue();
            }
        }

        return generoConMasCanciones;
    }


}
