package co.edu.uniquindio.storify.util;

import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaDoble;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaDobleCircular;
import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimple;
import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.model.Cancion;
import co.edu.uniquindio.storify.model.TipoGenero;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArchivoUtil {

    public static HashMap<String, Artista> cargarArtistasDesdeArchivo(String ruta) throws IOException {
        HashMap<String, Artista> artistas = new HashMap<>();
        ListaEnlazadaSimple<Artista> artistasEnlazados = new ListaEnlazadaSimple<>();
        List<String> contenido = leerArchivo(ruta);

        Artista artistaActual = null;
        boolean esEncabezadoArtistas = false;
        boolean esEncabezadoCanciones = false;


        for (String linea : contenido) {
            if (linea.startsWith("#Artistas")) {
                esEncabezadoArtistas = true;
                esEncabezadoCanciones = false;
                continue;
            } else if (linea.startsWith("#Canciones")) {
                esEncabezadoArtistas = false;
                esEncabezadoCanciones = true;
                continue;
            }

            String[] partes = linea.split(";");

            if (esEncabezadoArtistas && partes.length == 4) {
                artistaActual = new Artista(partes[0], partes[1], partes[2], Boolean.parseBoolean(partes[3]));
                artistasEnlazados.add(artistaActual);
                artistas.put(partes[0], artistaActual);
            } else if (esEncabezadoCanciones && partes.length == 9) {
                Cancion cancion = new Cancion(partes[1], partes[2], partes[3], partes[4], Integer.parseInt(partes[5]), Double.parseDouble(partes[6]), TipoGenero.valueOf(partes[7]), partes[8]);
                
                // Buscar al artista correspondiente en la lista enlazada
                for (Artista artista : artistasEnlazados) {
                    if (artista.getNombre().equals(partes[0])) {
                        artista.getCanciones().add(cancion);
                        break; // Una vez encontramos al artista, no es necesario seguir buscando
                    }
                }
            }
        }
        return artistas;
    }

    private static List<String> leerArchivo(String ruta) throws IOException {
        List<String>  contenido = new ArrayList<String>();
        FileReader fr=new FileReader(ruta);
        BufferedReader bfr=new BufferedReader(fr);
        String linea="";
        while((linea = bfr.readLine())!=null){
            contenido.add(linea);
        }
        bfr.close();
        fr.close();
        return contenido;
    }
}