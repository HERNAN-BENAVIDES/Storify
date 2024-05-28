package co.edu.uniquindio.storify.model;

import lombok.*;

/**
 * La clase Persona representa una persona con un nombre y un apellido.
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class Persona {

    private String nombre;
    private String apellido;

    /**
     * Constructor de la clase Persona.
     *
     * @param nombre  el nombre de la persona.
     * @param apellido el apellido de la persona.
     */
}
