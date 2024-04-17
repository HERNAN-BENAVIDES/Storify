package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimpleCircular;
import lombok.Data;
import java.io.Serializable;

@SuppressWarnings("all")
@Data
public class Cliente extends Persona implements Serializable, Comparable<Cliente> {
    private ListaEnlazadaSimpleCircular<Cancion> cancionesFavoritas;

    public Cliente(String nombre, String apellido) {
        super(nombre, apellido);
        this.cancionesFavoritas = new ListaEnlazadaSimpleCircular<>();
    }


    @Override
    public int compareTo(Cliente o) {
        return this.getNombre().compareTo(o.getNombre());
    }
}
