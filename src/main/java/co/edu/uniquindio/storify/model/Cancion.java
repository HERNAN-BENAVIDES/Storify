package co.edu.uniquindio.storify.model;


import lombok.*;

import java.io.Serializable;

@SuppressWarnings("All")
@Data

@NoArgsConstructor
@EqualsAndHashCode
public class Cancion implements Serializable, Comparable<Cancion> {

    private String codigo;
    private String nombre;
    private String album;
    private String caratula;
    private int anioLanzamiento;
    private String duracion;
    private TipoGenero genero;
    private String urlYoutube;

    public Cancion(String nombre, String album, String caratula, int anioLanzamiento, double duracion, TipoGenero genero, String urlYoutube) {
        this.codigo = TiendaMusica.generarCodigoAleatorio();
        this.nombre = nombre;
        this.album = album;
        this.caratula = caratula;
        this.anioLanzamiento = anioLanzamiento;
        this.duracion = duracion;
        this.genero = genero;
        this.urlYoutube = urlYoutube;
    }

    @Override
    public int compareTo(Cancion o) {
        return this.getNombre().compareTo(o.getNombre());
    }

    /**
     * @Override
     *     public String toString() {
     *         return "Cancion{" +
     *                 "codigo='" + codigo + '\'' +
     *                 ", nombre='" + nombre + '\'' +
     *                 ", album='" + album + '\'' +
     *                 ", caratula='" + caratula + '\'' +
     *                 ", anioLanzamiento=" + anioLanzamiento +
     *                 ", duracion=" + duracion +
     *                 ", genero=" + genero +
     *                 ", urlYoutube='" + urlYoutube + '\'' +
     *                 '}';
     *     }
     *
     *     @Override
     *     public boolean equals(Object o) {
     *         if (this == o) return true;
     *         if (!(o instanceof Cancion)) return false;
     *         Cancion cancion = (Cancion) o;
     *         return Objects.equals(getCodigo(), cancion.getCodigo()) && Objects.equals(getNombre(), cancion.getNombre()) && Objects.equals(getAlbum(), cancion.getAlbum());
     *     }
     *
     *     @Override
     *     public int hashCode() {
     *         return Objects.hash(getCodigo(), getNombre(), getAlbum());
     *     }
     */
}