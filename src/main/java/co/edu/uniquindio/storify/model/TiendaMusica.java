package co.edu.uniquindio.storify.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;

@Data
@ToString
@SuppressWarnings("All")
public class TiendaMusica implements Serializable {

    private final String nombre = "Storify";
    private final String version = "1.0.0";
    private HashMap<String, Usuario> usuarios;
    private HashMap<String, Artista> artistas;

    public TiendaMusica() {
        this.usuarios = new HashMap<>();
        this.artistas = new HashMap<>();
    }
}
