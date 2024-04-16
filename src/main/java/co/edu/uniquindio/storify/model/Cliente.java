package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimpleCircular;
import lombok.Data;
import java.io.Serializable;

@SuppressWarnings("all")
@Data
public class Cliente implements Serializable, Comparable<Cliente> {
    private String nombre;
    private String apellido;
    private Usuario usuario;
    private ListaEnlazadaSimpleCircular<Cancion> cancionesFavoritas;

    public Cliente() {

    }
    public Cliente(String nombre, String apellido, Usuario usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.cancionesFavoritas = new ListaEnlazadaSimpleCircular<>();
    }

    @Override
    public int compareTo(Cliente o) {
        return this.getNombre().compareTo(o.getNombre());
    }
}
