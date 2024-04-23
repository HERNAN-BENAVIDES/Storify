package co.edu.uniquindio.storify.model;

import lombok.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
//@SuppressWarnings("all")
public class Persona {
    private String nombre;
    private String apellido;

    /**
     * public Persona(String nombre, String apellido) {
     *               this.nombre = nombre;
     *               this.apellido = apellido;
     * }
     */
}