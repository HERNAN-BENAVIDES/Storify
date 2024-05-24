package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimpleCircular;
import lombok.*;

import java.io.Serializable;

@SuppressWarnings("all")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class Cliente extends Persona implements Serializable, Comparable<Cliente> {
    private ListaEnlazadaSimpleCircular<Cancion> cancionesFavoritas;

    @Builder
    public Cliente(String nombre, String apellido) {
        super(nombre, apellido);
        this.cancionesFavoritas = new ListaEnlazadaSimpleCircular<>();
    }

    public void agregarCancionFavorita(Cancion cancion) {
        cancionesFavoritas.add(cancion);
    }

    public void eliminarCancionFavorita(Cancion cancion) {
        cancionesFavoritas.removeData(cancion);
    }

    @Override
    public int compareTo(Cliente o) {
        return this.getNombre().compareTo(o.getNombre());
    }
}