package co.edu.uniquindio.storify.model;

import lombok.*;

import java.io.Serializable;

@SuppressWarnings("All")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cancion implements Serializable, Comparable<Cancion> {

    private String codigo;
    private String nombre;
    private String album;
    private String caratula;
    private int anioLanzamiento;
    private double duracion;
    private TipoGenero genero;
    private String urlYoutube;

    @Override
    public int compareTo(Cancion o) {
        return this.getNombre().compareTo(o.getNombre());
    }
}
