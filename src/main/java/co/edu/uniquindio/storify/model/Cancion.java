package co.edu.uniquindio.storify.model;


import lombok.*;

import java.io.Serializable;

@SuppressWarnings("All")
@Data
@Builder
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

    public Cancion(String codigo, String nombre, String album, String caratula, int anioLanzamiento, String duracion, TipoGenero genero, String urlYoutube) {
        this.codigo = codigo;
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

    public String obtenerGeneroComoString() {
        switch (genero) {
            case ROCK:
                return "Rock";
            case POP:
                return "Pop";
            case SALSA:
                return "Salsa";
            case BACHATA:
                return "Bachata";
            case PUNK:
                return "Punk";
            case REGGAETON:
                return "Reggaeton";
            case ELECTRONICA:
                return "Electrónica";
            case RB:
                return "R&B";
            case KPOP:
                return "K-POP";
            case OTRO:
                return "Otro";
            default:
                return "Desconocido";
        }
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