package co.edu.uniquindio.storify.model;

import lombok.Data;
import java.io.Serializable;

@SuppressWarnings("all")
@Data
public class Administrador extends Persona implements Serializable {
    private static Administrador administrador;

    public Administrador() {
        super("Jose Juan", "Mesa");
    }

    public static Administrador getAdministrador() {
        if (administrador == null) {
            administrador = new Administrador();
        }
        return administrador;
    }
}