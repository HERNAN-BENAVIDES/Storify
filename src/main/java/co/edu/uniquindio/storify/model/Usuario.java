package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimpleCircular;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@SuppressWarnings("All")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable, Comparable<Usuario> {

    private String username;
    private String password;
    private String email;

    @Override
    public int compareTo(Usuario o) {
        return this.getUsername().compareTo(o.getUsername());
    }
}
