package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.estructurasDeDatos.listas.ListaEnlazadaSimpleCircular;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
//@SuppressWarnings("All")
public class Usuario implements Serializable, Comparable<Usuario> {

    private String username;
    private String password;
    private String email;
    private Persona persona;

    /**
     * public Usuario() {
     *
     *     }
     *
     *     public Usuario(String username, String password, String email, Persona persona) {
     *         this.username = username;
     *         this.password = password;
     *         this.email = email;
     *         this.persona = persona;
     *     }
     *
     *     @Override
     *     public boolean equals(Object o) {
     *         if (this == o) return true;
     *         if (!(o instanceof Usuario)) return false;
     *         Usuario usuario = (Usuario) o;
     *         return Objects.equals(getUsername(), usuario.getUsername());
     *     }
     *
     *     @Override
     *     public int hashCode() {
     *         return Objects.hashCode(getUsername());
     *     }
     *
     */

    @Override
    public int compareTo(Usuario o) {
        return this.getUsername().compareTo(o.getUsername());
    }
}