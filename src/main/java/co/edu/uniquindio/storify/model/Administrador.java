package co.edu.uniquindio.storify.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@SuppressWarnings("all")
@Data
@EqualsAndHashCode(callSuper = true)

public class Administrador extends Persona implements Serializable {
    private static Administrador administrador;

    @Builder
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