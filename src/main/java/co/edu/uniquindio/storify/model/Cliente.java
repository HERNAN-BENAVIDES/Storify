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

    public static ClienteBuilder builder() {
        return new ClienteBuilder();
    }

    public static class ClienteBuilder {
        private String nombre;
        private String apellido;

        public ClienteBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public ClienteBuilder apellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Cliente build() {
            return new Cliente(nombre, apellido);
        }
    }



    @Override
    public int compareTo(Cliente o) {
        return this.getNombre().compareTo(o.getNombre());
    }
}
