package co.edu.uniquindio.storify.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@SuppressWarnings("all")
@Data
@NoArgsConstructor
public class Administrador implements Serializable {
    private Usuario usuario;
    private static Administrador administrador;

    public Administrador() {
        this.usuario.setUsername("admin");
        this.usuario.setPassword("$aDmiN");
        this.usuario.setEmail("admin@gmail.com");
    }

    public static Administrador getInstance() {
            if(administrador == null) {
                administrador = new Administrador();
            }
            return administrador;
    }
}
